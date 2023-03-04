// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoCommands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

public class AutoLevel extends CommandBase {

  PIDController pid = new PIDController(0.06, 0, 0.0);

  Timer timer = new Timer();

  // Grab swerve singleton.
  Swerve s_Swerve = Swerve.getInstance();

  AHRS gyro = s_Swerve.gyro;

  boolean timerRunning;

  public AutoLevel() {

    // Declaring subsystem dependencies.
    addRequirements(s_Swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Set motor speed.
    s_Swerve.drive(new Translation2d(-pid.calculate(gyro.getPitch()), 0), 0, false, true);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    // Stop motors.
    s_Swerve.drive(new Translation2d(0, 0), 0, false, true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    // Guard Clause.
    if (Math.abs(gyro.getPitch()) > 1) {
      timer.reset();
      timerRunning = false;
      return false;
    }

    // Start if not started. 
    if (!timerRunning) {
      timer.start();
      timerRunning = true;
    }

    // Check for completion.
    return timer.hasElapsed(3);
  }
}
