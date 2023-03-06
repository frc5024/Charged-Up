package frc.robot.subsystems;

import java.io.IOException;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFieldLayout.OriginPosition;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.FieldConstants;
import frc.robot.Constants.VisionConstants;

/**
 * 
 */
public class PoseEstimator extends SubsystemBase {
    private Swerve swerveDrive;
    private PhotonCamera photonCamera;
    private SwerveDrivePoseEstimator swerveDrivePoseEstimator;
    private PhotonPoseEstimator photonPoseEstimator;

    private final Field2d field2d = new Field2d();

    private double previousPipelineTimestamp = 0;
    private OriginPosition originPosition = OriginPosition.kBlueAllianceWallRightSide;

    /**
     * Standard deviations of model states. Increase these numbers to trust your model's state estimates less. This
     * matrix is in the form [x, y, theta]ᵀ, with units in meters and radians, then meters.
     */
    private static final Vector<N3> stateStdDevs = VecBuilder.fill(0.05, 0.05, 0.1);

    /**
     * Standard deviations of the vision measurements. Increase these numbers to trust global measurements from vision
     * less. This matrix is in the form [x, y, theta]ᵀ, with units in meters and radians.
     */
    private static final Vector<N3> visionMeasurementStdDevs = VecBuilder.fill(0.5, 0.5, 0.9);

    /**
     * 
     */
    public PoseEstimator(Swerve swerveDrive, PhotonCamera photonCamera) {
        this.swerveDrive = swerveDrive;
        this.photonCamera = photonCamera;

        this.photonCamera.setPipelineIndex(1);

        setSwerveDrivePoseEstimators();
        setPhotonPoseEstimator();
        setShuffleboardTabs();
    }

    /**
     * Transforms a pose to the opposite alliance's coordinate system. (0,0) is always on the right corner of your
     * alliance wall, so for 2023, the field elements are at different coordinates for each alliance.
     * @param poseToFlip pose to transform to the other alliance
     * @return pose relative to the other alliance's coordinate system
     */
    private Pose2d flipAlliance(Pose2d poseToFlip) {
        return poseToFlip.relativeTo(
                new Pose2d(
                        new Translation2d(FieldConstants.LENGTH_METERS, FieldConstants.WIDTH_METERS),
                        new Rotation2d(Math.PI)));
    }

    /**
     * 
     */
    public String getFormattedFiducialId() {
        PhotonPipelineResult result = this.photonCamera.getLatestResult();
        PhotonTrackedTarget target = null;

        if (result.hasTargets()) {
            target = result.getBestTarget();
        }

        return target != null ? String.format("%d", target.getFiducialId()) : "";
    }

    /**
     * 
     */
    public Pose2d getCurrentPose() {
        return this.swerveDrivePoseEstimator.getEstimatedPosition();
    }

    /**
     * 
     */
    private String getFomattedPose() {
        Pose2d pose = getCurrentPose();

        return String.format("(%.2f, %.2f) %.2f degrees", pose.getX(), pose.getY(), pose.getRotation().getDegrees());
    }

    @Override
    public void periodic() {
        // Update pose estimator with drivetrain sensors
        this.swerveDrivePoseEstimator.update(
                this.swerveDrive.getYaw(),
                this.swerveDrive.getModulePositions());

        if (this.photonPoseEstimator != null) {
            // Update pose estimator with the best visible target
            this.photonPoseEstimator.update().ifPresent(estimatedRobotPose -> {
                Pose3d estimatedPose = estimatedRobotPose.estimatedPose;

                // Make sure we have a new measurement, and that it's on the field
                if (estimatedRobotPose.timestampSeconds != this.previousPipelineTimestamp
                        && estimatedPose.getX() > 0.0 && estimatedPose.getX() <= FieldConstants.LENGTH_METERS
                        && estimatedPose.getY() > 0.0 && estimatedPose.getY() <= FieldConstants.WIDTH_METERS) {
                    this.previousPipelineTimestamp = estimatedRobotPose.timestampSeconds;
                    this.swerveDrivePoseEstimator.addVisionMeasurement(estimatedPose.toPose2d(),
                            estimatedRobotPose.timestampSeconds);
                }
            });
        }

        Pose2d dashboardPose = getCurrentPose();
        if (originPosition == OriginPosition.kRedAllianceWallRightSide) {
            // Flip the pose when red, since the dashboard field photo cannot be rotated
            dashboardPose = flipAlliance(dashboardPose);
        }
        field2d.setRobotPose(dashboardPose);
    }

    /**
     * Resets the position on the field to 0,0 0-degrees, with forward being downfield. This resets
     * what "forward" is for field oriented driving.
     */
    public void resetFieldPosition() {
        setCurrentPose(new Pose2d());
    }

    /**
     * Sets the alliance. This is used to configure the origin of the AprilTag map
     * @param alliance alliance
     */
    public void setAlliance(Alliance alliance) {
        AprilTagFieldLayout fieldTags = this.photonPoseEstimator.getFieldTags();
        boolean allianceChanged = false;

        switch (alliance) {
        case Blue:
            fieldTags.setOrigin(OriginPosition.kBlueAllianceWallRightSide);
            allianceChanged = (originPosition == OriginPosition.kRedAllianceWallRightSide);
            originPosition = OriginPosition.kBlueAllianceWallRightSide;
            break;

        case Red:
            fieldTags.setOrigin(OriginPosition.kRedAllianceWallRightSide);
            allianceChanged = (originPosition == OriginPosition.kBlueAllianceWallRightSide);
            originPosition = OriginPosition.kRedAllianceWallRightSide;
            break;

        default:
        }

        // The alliance changed, which changes the coordinate system.
        // Since a tag may have been seen and the tags are all relative to the coordinate system, the estimated pose
        // needs to be transformed to the new coordinate system.
        if (allianceChanged) {
            Pose2d newPose = flipAlliance(swerveDrivePoseEstimator.getEstimatedPosition());
            setCurrentPose(newPose);
        }
    }

    /**
     * Resets the current pose to the specified pose. This should ONLY be called
     * when the robot's position on the field is known, like at the beginning of
     * a match.
     * @param newPose new pose
     */
    public void setCurrentPose(Pose2d newPose) {
        this.swerveDrivePoseEstimator.resetPosition(this.swerveDrive.getYaw(), this.swerveDrive.getModulePositions(),
                newPose);
    }

    /**
     * 
     */
    private void setPhotonPoseEstimator() {
        PhotonPoseEstimator photonPoseEstimator = null;

        try {
            AprilTagFieldLayout aprilTagFieldLayout = AprilTagFieldLayout
                    .loadFromResource("/edu/wpi/first/apriltag/2023-chargedup.json");
            aprilTagFieldLayout.setOrigin(this.originPosition);
            photonPoseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout, PoseStrategy.MULTI_TAG_PNP,
                    this.photonCamera, VisionConstants.REAR_CAMERA_TO_ROBOT);
        } catch (IOException e) {
            DriverStation.reportError("Failed to load AprilTag Field Layout", e.getStackTrace());
        }

        this.photonPoseEstimator = photonPoseEstimator;
    }

    /**
     * 
     */
    private void setSwerveDrivePoseEstimators() {
        this.swerveDrivePoseEstimator = new SwerveDrivePoseEstimator(
                Constants.Swerve.swerveKinematics,
                this.swerveDrive.getYaw(),
                this.swerveDrive.getModulePositions(),
                new Pose2d(),
                stateStdDevs,
                visionMeasurementStdDevs);
    }

    /**
     * 
     */
    private void setShuffleboardTabs() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("PoseEstimation");

        shuffleboardTab.addString("Tag ID", this::getFormattedFiducialId).withPosition(0, 0).withSize(2, 0);
        shuffleboardTab.addString("Pose", this::getFomattedPose).withPosition(1, 0).withSize(2, 1);
        shuffleboardTab.add("Field", this.field2d).withPosition(0, 1).withSize(6, 4);
    }
}
