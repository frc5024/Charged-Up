// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autocommands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TankDrive;

public class DriveForward extends CommandBase {

  //Declaring Variables
  AHRS gyro = new AHRS();
  float pitch;

  /** Creates a new DriveForward. */
  public DriveForward() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Gets pitch from NavX
    pitch = gyro.getPitch();
    //Tells robot to move forward
    TankDrive.getInstance().setSpeed(0.4, 0.4);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //when there is a change in pitch this command ends
    if (pitch > 3 || pitch < -3) {
      return true;
    } else return false;
  }
}
