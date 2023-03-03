package frc.robot.commands.autoCommands;

import java.util.HashMap;
import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.ZeroArmCommand;
import frc.robot.subsystems.Swerve;

public class AutoPath extends SequentialCommandGroup {
    ArmCommand scoreMid = new ArmCommand(Constants.ArmConstants.midArmPosition, true);
    ZeroArmCommand zeroArm = new ZeroArmCommand();

    public AutoPath(Swerve driveSubsystem) {
        // This will load the file "FullAuto.path" and generate it with a max velocity
        // of 4 m/s and a max acceleration of 3 m/s^2
        // for every path in the group
        List<PathPlannerTrajectory> pathGroup = PathPlanner.loadPathGroup(
                "Path1",
                new PathConstraints(4, 3));
        // This is just an example event map. It would be better to have a constant,
        // global event map
        // in your code that will be used by all path following commands.
        HashMap<String, Command> eventMap = new HashMap<>();
        // eventMap.put("Wait", new Timer);
        // eventMap.put("intakeDown", new IntakeDown());

        // Create the AutoBuilder. This only needs to be created once when robot code
        // starts, not every time you want to create an auto command. A good place to
        // put this is in RobotContainer along with your subsystems.
        SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
                driveSubsystem::getPose, // Pose2d supplier
                driveSubsystem::resetOdometry, // Pose2d consumer, used to reset odometry at the beginning of auto
                Constants.Swerve.swerveKinematics, // driveSubsystem.kinematics, // SwerveDriveKinematics
                new PIDConstants(5.0, 0.0, 0.0), // PID constants to correct for translation error (used to create the X
                // and Y PID controllers)
                new PIDConstants(0.5, 0.0, 0.0), // PID constants to correct for rotation error (used to create the
                // rotation controller)
                driveSubsystem::setModuleStates, // Module states consumer used to output to the drive subsystem
                eventMap,
                false, // Should the path be automatically mirrored depending on alliance color.
                // Optional, defaults to true
                driveSubsystem // The drive subsystem. Used to properly set the requirements of path following
                               // commands
        );

        Command fullAuto = autoBuilder.fullAuto(pathGroup);
        addCommands(scoreMid, zeroArm, fullAuto);
    }
}