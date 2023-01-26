// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.concurrent.DelayQueue;

import frc.robot.subsystems.Drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.ExampleSubsystem;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  double speed;
  XboxController operatorController;

  private WPI_TalonSRX leftMaster;
	private WPI_TalonSRX leftFollower;
	private WPI_TalonSRX rightMaster;
	private WPI_TalonSRX rightFollower;
  private int motorInversionMultiplier = 1;

  ExampleSubsystem subsystem;
  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    subsystem = new ExampleSubsystem();

    operatorController = new XboxController(0);

    leftMaster = new WPI_TalonSRX(1);
		leftFollower = new WPI_TalonSRX(2);
		leftFollower.follow(leftMaster);

		rightMaster = new WPI_TalonSRX(3);
		rightFollower = new WPI_TalonSRX(4);
		rightFollower.follow(rightMaster);

  }

  public double getSpeed(){
    double speed = 0;
    speed += operatorController.getRightTriggerAxis();
    speed -= operatorController.getLeftTriggerAxis();
    return speed;
}
  public void setSpeed(double speed){
    speed = getSpeed();
    rightMaster.set(speed); //motorInversionMultiplier);
    leftMaster.set(speed * motorInversionMultiplier);
}


  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    //Gets pitch from Navex
    float pitch = subsystem.Pitch();
    // Send pitch data to console
    System.out.println(pitch);
    // Send pitch data to shuffleboard
    SmartDashboard.putNumber("Gyro", pitch);
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
   

    // schedule the autonomous command (example)
    // if (m_autonomousCommand != null) {
    //   m_autonomousCommand.schedule();
    // }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. 
   * @return */
  @Override
  public void teleopPeriodic() {
    
    setSpeed(speed);
    
  }




  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
