// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import org.checkerframework.checker.units.qual.Speed;

import com.ctre.phoenixpro.controls.VelocityVoltage;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class ArmCommand extends CommandBase {

  private Arm arm;

  private int position;

  private boolean openGrabber;

  /** Creates a new Armcommand. */
  public ArmCommand(int position, boolean openGrabber) {

    // Intializes subsystems and variables.
    arm = Arm.getInstance();
    this.position = position;
    this.openGrabber = openGrabber;

    // Adds arm subsystem as a requirement.
    addRequirements(arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Set the arms desried position and start the moving process.
    arm.setDesiredPosition(position);
    arm.setReleaseOnFinish(openGrabber);
    arm.startMoving();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // End command after one run.
    return true;
  }
}
