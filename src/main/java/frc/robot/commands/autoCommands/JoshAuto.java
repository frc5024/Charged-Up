// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.ZeroArmCommand;

public class JoshAuto extends SequentialCommandGroup {
  /** Creates a new JoshAuto. */
  public JoshAuto() {
    // Use addRequirements() here to declare subsystem dependencies.
    addCommands(
        new GripperCommand(false),
        new ArmCommand(Constants.ArmConstants.midArmPosition, true),
        new ZeroArmCommand(),
        new AutoPath("Path3"),
        new GripperCommand(true),
        new ArmCommand(Constants.ArmConstants.hybridArmPosition, true),
        new GripperCommand(false)
        // new ArmCommand(Constants.ArmConstants.midArmPosition, false),
        // new AutoPath("Path4"),
        // new AutoLevel()
        );
  }
}
