// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.autos.Auto1;
import frc.robot.autos.Auto2;
import frc.robot.autos.Auto3;
import frc.robot.autos.Auto4;
import frc.robot.autos.Auto5;
import frc.robot.autos.Auto6;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private SendableChooser<Integer> autoChooser;

  public static CTREConfigs ctreConfigs;

  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private PneumaticHub pHub;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    autoChooser = new SendableChooser<>();

    autoChooser.addOption("Middle", 1);
    autoChooser.addOption("Left-J", 2);
    autoChooser.addOption("Straight", 3);
    autoChooser.addOption("Drift", 4);
    autoChooser.addOption("Right-J", 5);
    autoChooser.addOption("Don't Touch", 6);

    Shuffleboard.getTab("AutoTab").add(autoChooser);

    ctreConfigs = new CTREConfigs();

    CameraServer.startAutomaticCapture();
    CameraServer.startAutomaticCapture();

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    pHub = new PneumaticHub(Constants.PneumaticConstants.pneumaticHub);
    pHub.enableCompressorAnalog(Constants.PneumaticConstants.minPressure, Constants.PneumaticConstants.maxPressure);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {

    // Schedule auto command chosen on Shuffleboard.
    switch (autoChooser.getSelected()) {

    case 1:
      m_autonomousCommand = new Auto1();
      break;
    case 2:
      m_autonomousCommand = new Auto2();
      break;
    case 3:
      m_autonomousCommand = new Auto3();
      break;
    case 4:
      m_autonomousCommand = new Auto4();
      break;
    case 5:
      m_autonomousCommand = new Auto5();
      break;
    case 6:
      m_autonomousCommand = new Auto6();
      break;

    default:
      break;
    }

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
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
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}
