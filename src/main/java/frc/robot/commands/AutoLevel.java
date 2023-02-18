// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;


public class AutoLevel extends CommandBase {
  
  // create new NavX object
  AHRS gyro = new AHRS();

  // grab swerve singleton
  Swerve s_Swerve = Swerve.getInstance();

  //PID controller
  PIDController pid = new PIDController(0.07, 0, 0.0);

  //Enter leveling mode or not
  boolean levelingMode = false;

  // create new timertinme
  Timer timer = new Timer();

  /** Creates a new AutoLevel. */
  public AutoLevel() {
    // Use addRequirements() here to declare subsystem dependencies.

    addRequirements(s_Swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // if in leveling mode, attempt to balance, if not, drive forward until robot is
    if (levelingMode == true) {

      // set motor speed
      s_Swerve.drive(new Translation2d(-pid.calculate(gyro.getPitch()), 0), 0, false, true);

    } else if (levelingMode == false) {

      // drive forward to get onto charge station
      s_Swerve.drive(new Translation2d(1.2, 0), 0, false, true);

      // switch to leveling mode when angle is great enough
      if ( Math.abs(gyro.getPitch()) > 3 ) levelingMode = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    // stop motors
    s_Swerve.drive(new Translation2d(0,0), 0, false, true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {


    return false;
  }
}
