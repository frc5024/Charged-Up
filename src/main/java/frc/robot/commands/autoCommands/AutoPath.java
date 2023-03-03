package frc.robot.commands.autoCommands;

import java.util.HashMap;
import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.ZeroArmCommand;
import frc.robot.subsystems.Swerve;

public class AutoPath extends SequentialCommandGroup {

    ArmCommand scoreMid = new ArmCommand(Constants.ArmConstants.midArmPosition, true);
    GripperCommand close = new GripperCommand(false);
    ZeroArmCommand zeroArm = new ZeroArmCommand();
    AutoLevel level = new AutoLevel();
    GripperCommand closeClaw = new GripperCommand(false);

    /* Creates new auto path */
    public AutoPath(Swerve driveSubsystem) {

        // Loads chosen auto path from PathPlanner.
        List<PathPlannerTrajectory> pathGroup = PathPlanner.loadPathGroup(
                "Path1",
                new PathConstraints(4, 3));

        HashMap<String, Command> eventMap = new HashMap<>();

        /* Creates the AutoBuilder. */
        SwerveAutoBuilder AutoBuilder = new SwerveAutoBuilder(
                driveSubsystem::getPose, // Pose2d supplier.
                driveSubsystem::resetOdometry, // Resets the odometry at the beginning of auto.
                Constants.Swerve.swerveKinematics,
                new PIDConstants(5.0, 0.0, 0.0), // PID constants to correct for translation error (X and Y).
                new PIDConstants(0.5, 0.0, 0.0), // PID constants to correct for rotation error (Creates rotation controller).
                driveSubsystem::setModuleStates,
                eventMap,
                false, // Mirrored depending on alliance color.
                driveSubsystem

        );

        Command fullAuto = AutoBuilder.fullAuto(pathGroup);
        addCommands(closeClaw, scoreMid, zeroArm, fullAuto, level);
        //addCommands(fullAuto);
    }
}