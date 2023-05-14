// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import com.pathplanner.lib.PathConstraints;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.ThrowCommand;
import frc.robot.commands.autoCommands.AutoLevel;
import frc.robot.commands.autoCommands.AutoPath;

public class Throw extends ParallelCommandGroup {

  public Throw() {

    // Specify velocity limitations.
    PathConstraints velocity = new PathConstraints(1.25, 1);

    // Play command sequence.
    addCommands(
        Commands.parallel(new ThrowCommand(-1100), new ArmCommand(-2600, false)),
        // new WaitCommand(5),
        Commands.waitSeconds(1.0).asProxy()
            .andThen(new AutoPath("Path1", velocity)).andThen(new AutoLevel()));

  }

}
