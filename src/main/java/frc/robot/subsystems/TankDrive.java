// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class TankDrive extends SubsystemBase {
  /** Creates a new tankDrive. */

  private WPI_TalonFX rightMaster;
  private WPI_TalonFX rightFollower;
  private WPI_TalonFX leftMaster;
  private WPI_TalonFX leftFollower;

  private static TankDrive mInstance;
  public static TankDrive getInstance() {
    if (mInstance == null) {
      mInstance = new TankDrive();
    }
    return mInstance;
  }

  private TankDrive() {

    leftMaster = new WPI_TalonFX(1);
    leftFollower = new WPI_TalonFX(2);
    rightMaster = new WPI_TalonFX(3);
    rightFollower = new WPI_TalonFX(4);

    leftFollower.follow(leftMaster);
    rightFollower.follow(rightMaster);

  }

  public void setSpeed(double leftSpeed, double rightSpeed) {
    leftMaster.set(-leftSpeed);
    rightMaster.set(rightSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
