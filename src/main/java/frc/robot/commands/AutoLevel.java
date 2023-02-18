// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;


public class AutoLevel extends CommandBase {
  /** Creates a new AutoLevel. */
  AHRS gyro = new AHRS();
  Swerve s_Swerve = Swerve.getInstance();

  //PID controller
  PIDController pid = new PIDController(0.07, 0, 0.0);
  
  //Gets pitch from NavX
  float pitch = gyro.getPitch();
  //Sets Pidreal (Gives accurate pitch angle to PID)
  double pidreal;

  //Sends simulated pitch angle to shuffleboard
  private ShuffleboardTab tab = Shuffleboard.getTab("Balance Testing");
  private GenericEntry angleTest =
    tab.add("Simulated Angle", 0)
    .getEntry();

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
    // pull pitch
    pidreal = pid.calculate(gyro.getPitch());
    // set motor speed
    s_Swerve.drive(new Translation2d(-pidreal, 0), 0, false, true);
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
