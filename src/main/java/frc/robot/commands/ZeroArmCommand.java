// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;

public class ZeroArmCommand extends CommandBase {

  private Arm arm;

  private Gripper gripper;

  /** Creates a new ZeroArmCommand. */
  public ZeroArmCommand() {
    // Intializes subsystems and variables.
    arm = Arm.getInstance();
    gripper = Gripper.getInstance();

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Open gripper and then start the zeroing process.
    gripper.openGripper();
    arm.startZeroing();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
