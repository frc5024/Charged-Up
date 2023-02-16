// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class RobotContainer {

  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);

  /* Controllers */
  private final Joystick driver = new Joystick(0);
  private final Joystick operator = new Joystick(1);

  // sets up controller bindings
  private final JoystickButton drawerRetractor = new JoystickButton(driver, XboxController.Button.kBack.value);
  private final JoystickButton drawerExtender = new JoystickButton(driver, XboxController.Button.kStart.value);
  private final JoystickButton gripperClose = new JoystickButton(driver, XboxController.Button.kY.value);
  private final JoystickButton gripperOpen = new JoystickButton(driver, XboxController.Button.kA.value);

  private final Drawer s_Drawer = Drawer.getInstance();
  private final Gripper s_Gripper = Gripper.getInstance();

  public RobotContainer() {

    configureBindings();
  }

  /**
   * 
   */
  private void configureBindings() {

    // makes the bindings actually do things
    drawerRetractor.onTrue(new InstantCommand(() -> s_Drawer.retractDrawer()));
    drawerExtender.onTrue(new InstantCommand(() -> s_Drawer.extendDrawer()));

    gripperOpen.onTrue(new InstantCommand(() -> s_Gripper.openGripper()));
    gripperClose.onTrue(new InstantCommand(() -> s_Gripper.closeGripper()));

  }

  public Command getAutonomousCommand() {
    return null;

  }
}
