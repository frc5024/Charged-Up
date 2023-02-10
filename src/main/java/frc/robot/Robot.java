// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.concurrent.DelayQueue;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.math.controller.PIDController;




/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //Declaring or initializing required variables
  private Command m_autonomousCommand;

  
  XboxController controller = new XboxController(0);
  ExampleSubsystem subsystem;
  Drivetrain driveTrain;

  private WPI_TalonFX rightMaster;
  private WPI_TalonFX rightFollower;
  private WPI_TalonFX leftMaster;
  private WPI_TalonFX leftFollower;
  
  private double startTime;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    subsystem = new ExampleSubsystem();

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

    //Gets pitch from Navx
    float pitch = subsystem.Pitch();
    // Send pitch data to console
    //System.out.println(pitch);
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
   startTime = Timer.getFPGATimestamp();

    // schedule the autonomous command (example)
    // if (m_autonomousCommand != null) {
    //   m_autonomousCommand.schedule();
    // }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

    float pitch = subsystem.Pitch(); //Call for the Pitch method in ExampleSubsystem (Change later please)

    //initializing all of the motors
    leftMaster = new WPI_TalonFX(1); 
    leftFollower = new WPI_TalonFX(2);
    rightMaster = new WPI_TalonFX(3);
    rightFollower = new WPI_TalonFX(4);
    
    //Preparing the follower motors
   // leftFollower.follow(leftMaster);
   // rightFollower.follow(rightMaster);
    

   
    //To run while the front of the robot is pitched upwards
    if(pitch > 2){
      
      //Setting the speed of the motors to drive forwards
      rightMaster.set(0.25);
      rightFollower.set(0.25);
      leftMaster.set(-0.25);
      leftFollower.set(-0.25);
      System.out.println("Drive Forward");

    } 
    //To run while the front of the robot is pitched downwards
    else if(pitch < -2){

      //Setting the speed of the motors to drive backwards
      rightMaster.set(-0.25);
      rightFollower.set(-0.25);
      leftMaster.set(0.25);
      leftFollower.set(0.25);
      System.out.println("Drive Backwards");

    } 

    //To run while robot is in a level state
    else {

      //Stops the motors
      leftMaster.stopMotor();
      rightMaster.stopMotor();
      System.out.println("Level");

    }
  }

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

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    new DriveCommand().schedule();
    // if(controller.getAButton()){
    //     talon1.set(.5);
    //     talon2.set(.5);
    //     talon3.set(.5);
    //     talon4.set(.5);
    // }else{
    //   talon1.set(0);
    //   talon2.set(0);
    //   talon3.set(0);
    //   talon4.set(0);
    //}


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
