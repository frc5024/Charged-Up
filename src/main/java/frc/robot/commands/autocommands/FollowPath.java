// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autocommands;

import java.util.HashMap;
import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class FollowPath extends SequentialCommandGroup {
  /** Creates a new FollowPath. */
  public FollowPath() {
    // Use addRequirements() here to declare subsystem dependencies.

    // This will load the file "FullAuto.path" and generate it with a max velocity of 4 m/s and a max acceleration of 3 m/s^2
// for every path in the group
List<PathPlannerTrajectory> pathGroup = PathPlanner.loadPathGroup("Auto2", new PathConstraints(4, 3));

// This is just an example event map. It would be better to have a constant, global event map
// in your code that will be used by all path following commands.
HashMap<String, Command> eventMap = new HashMap<>();
eventMap.put("", new PrintCommand("Finished"));

Swerve swerve = Swerve.getInstance();
swerve.resetModulesToAbsolute();
swerve.resetOdometry(swerve.getPose());

// Create the AutoBuilder. This only needs to be created once when robot code starts, not every time you want to create an auto command. A good place to put this is in RobotContainer along with your subsystems.
SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
    swerve::getPose, // Pose2d supplier
    swerve::resetOdometry, // Pose2d consumer, used to reset odometry at the beginning of auto
    Constants.Swerve.swerveKinematics, // SwerveDriveKinematics
    new PIDConstants(5.0, 0.0, 0.0), // PID constants to correct for translation error (used to create the X and Y PID controllers)
    new PIDConstants(0.5, 0.0, 0.0), // PID constants to correct for rotation error (used to create the rotation controller)
    swerve::setModuleStates, // Module states consumer used to output to the drive subsystem
    eventMap,
    false,  //Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
    swerve // The drive subsystem. Used to properly set the requirements of path following commands
);

Command Auto = autoBuilder.fullAuto(pathGroup);

addCommands(Auto);
  }
}
