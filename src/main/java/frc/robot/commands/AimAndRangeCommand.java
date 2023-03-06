package frc.robot.commands;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.google.common.base.Supplier;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.VisionConstants;
import frc.robot.subsystems.Swerve;

public class AimAndRangeCommand extends CommandBase {
    private final PhotonCamera photonCamera;
    private final Swerve swerveDrive;
    private final Supplier<Pose2d> poseProvider;

    private static final TrapezoidProfile.Constraints X_CONSTRAINTS = new TrapezoidProfile.Constraints(3, 2);
    private static final TrapezoidProfile.Constraints Y_CONSTRAINTS = new TrapezoidProfile.Constraints(3, 2);
    private static final TrapezoidProfile.Constraints OMEGA_CONSTRAINTS = new TrapezoidProfile.Constraints(8, 8);

    private static final Transform3d TARGET_TO_GOAL = new Transform3d(
            new Translation3d(1.5, 0.0, 0.0),
            new Rotation3d(0.0, 0.0, Math.PI));

    private final ProfiledPIDController xController = new ProfiledPIDController(3, 0, 0, X_CONSTRAINTS);
    private final ProfiledPIDController yController = new ProfiledPIDController(3, 0, 0, Y_CONSTRAINTS);
    private final ProfiledPIDController omegaController = new ProfiledPIDController(2, 0, 0, OMEGA_CONSTRAINTS);

    private PhotonTrackedTarget lastTarget;

    /**
     * 
     */
    public AimAndRangeCommand(PhotonCamera photonCamera, Swerve swerveDrive, Supplier<Pose2d> poseProvider) {
        this.photonCamera = photonCamera;
        this.swerveDrive = swerveDrive;
        this.poseProvider = poseProvider;

        this.photonCamera.setPipelineIndex(2);

        this.xController.setTolerance(0.2);
        this.yController.setTolerance(0.2);
        this.omegaController.setTolerance(Units.degreesToRadians(3));
        this.omegaController.enableContinuousInput(-Math.PI, Math.PI);

        addRequirements(this.swerveDrive);
    }

    @Override
    public void initialize() {
        this.lastTarget = null;
        Pose2d robotPose = poseProvider.get();

        this.omegaController.reset(robotPose.getRotation().getRadians());
        this.xController.reset(robotPose.getX());
        this.yController.reset(robotPose.getY());
    }

    @Override
    public void execute() {
        Pose2d robotPose2d = poseProvider.get();
        Pose3d robotPose = new Pose3d(
                robotPose2d.getX(),
                robotPose2d.getY(),
                0.0,
                new Rotation3d(0.0, 0.0, robotPose2d.getRotation().getRadians()));

        PhotonPipelineResult result = photonCamera.getLatestResult();

        if (result.hasTargets()) {
            PhotonTrackedTarget target = result.getBestTarget();
            this.lastTarget = target;

            // Transform the robot's pose to find the camera's pose
            Pose3d cameraPose = robotPose.transformBy(VisionConstants.ROBOT_TO_REAR_CAMERA);

            // Transform the camera's pose to the target's pose
            Transform3d camToTarget = target.getBestCameraToTarget();
            Pose3d targetPose = cameraPose.transformBy(camToTarget);

            // Transform the tag's pose to set our goal
            Pose2d goalPose = targetPose.transformBy(TARGET_TO_GOAL).toPose2d();

            // Drive
            this.xController.setGoal(goalPose.getX());
            this.yController.setGoal(goalPose.getY());
            this.omegaController.setGoal(goalPose.getRotation().getRadians());
        }

        if (this.lastTarget == null) {
            this.swerveDrive.stop();
        } else {
            // Drive to the target
            double xSpeed = xController.calculate(robotPose.getX());
            if (this.xController.atGoal()) {
                xSpeed = 0;
            }

            double ySpeed = yController.calculate(robotPose.getY());
            if (this.yController.atGoal()) {
                ySpeed = 0;
            }

            double omegaSpeed = omegaController.calculate(robotPose2d.getRotation().getRadians());
            if (this.omegaController.atGoal()) {
                omegaSpeed = 0;
            }

            this.swerveDrive.drive(
                    ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, omegaSpeed, robotPose2d.getRotation()),
                    false);
        }
    }

    @Override
    public void end(boolean interrupted) {
        this.swerveDrive.stop();
    }
}
