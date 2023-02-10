// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.*;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;


public class RobotContainer {

  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();


  private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);

      /* Controllers */
      private final Joystick driver = new Joystick(0);
      private final Joystick operator = new Joystick(1);
      
  private final JoystickButton backExtender = new JoystickButton(driver, XboxController.Button.kStart.value);
  private final JoystickButton startExender= new JoystickButton(driver, XboxController.Button.kBack.value);

  private final Drawer s_Drawer = new Drawer();

 
  public RobotContainer() {

    configureBindings();
  }

 
  private void configureBindings() {
 
    backExtender.onTrue (new InstantCommand (() -> s_Drawer.canExtend()));
   
  }

 
  public Command getAutonomousCommand() {

    return Autos.exampleAuto(m_exampleSubsystem);
  }
}