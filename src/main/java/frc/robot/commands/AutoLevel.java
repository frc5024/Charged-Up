// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;


public class AutoLevel extends CommandBase {
  /** Creates a new AutoLevel. */
  AHRS gyro = new AHRS();
  Swerve s_Swerve = Swerve.getInstance();

  //PID controller
  PIDController pid = new PIDController(0.025, 0, 0.001);
  
  //Gets pitch from NavX
  float pitch = gyro.getPitch();
  //Sets Pidreal (Gives accurate pitch angle to PID)
  double pidreal;

  public AutoLevel() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // pull pitch
    pidreal = pid.calculate(gyro.getPitch());
    // set motor speed
    s_Swerve.drive(new Translation2d(10, 0), 0, true, false);
    System.out.println(pidreal);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
