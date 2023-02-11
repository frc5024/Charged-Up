// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import org.checkerframework.checker.units.qual.Speed;

import com.ctre.phoenixpro.controls.VelocityVoltage;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;


public class Armcommand extends CommandBase {
  private static final String topMotor = null;

  private int position;

  /** Creates a new Armcommand. */
  public Armcommand(int position) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.position = position;
    //addRequirements(Arm.getInstance());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Arm.getInstance().setDesiredPosition(position);
    Arm.getInstance().startMoving();
    

  
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
