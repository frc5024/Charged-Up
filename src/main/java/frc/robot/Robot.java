// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Drive;

public class Robot extends TimedRobot {

  private Command m_autonomousCommand;

  XboxController controller = new XboxController(0);

  private WPI_TalonFX talon1 = new WPI_TalonFX(1);
  private WPI_TalonFX talon2 = new WPI_TalonFX(2);

  private WPI_TalonFX talon3 = new WPI_TalonFX(3);
  private WPI_TalonFX talon4 = new WPI_TalonFX(4);

  @Override
  public void robotInit() {
    
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
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
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {

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
