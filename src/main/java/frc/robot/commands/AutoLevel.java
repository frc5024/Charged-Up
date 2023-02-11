// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoLevel extends CommandBase {
  /** Creates a new AutoLevel. */
  AHRS gyro = new AHRS();

  PIDController pid = new PIDController(0.03, 0, 0);

  private WPI_TalonFX rightMaster;
  private WPI_TalonFX rightFollower;
  private WPI_TalonFX leftMaster;
  private WPI_TalonFX leftFollower;
  
  float pitch = gyro.getPitch();
  double pidreal = pid.calculate(gyro.getPitch(), -1.4);

  public AutoLevel() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    leftMaster = new WPI_TalonFX(1);
    leftFollower = new WPI_TalonFX(2);
    rightMaster = new WPI_TalonFX(3);
    rightFollower = new WPI_TalonFX(4);

    leftFollower.follow(leftMaster);
    rightFollower.follow(rightMaster);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    rightMaster.set(-pidreal);
    leftMaster.set(pidreal);
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
