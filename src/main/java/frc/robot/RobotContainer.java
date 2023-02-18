// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

//Allows controllers and joysticks to be used
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.subsystems.*;

public class RobotContainer {

  //Creates the operator and driver controllers
  private final Joystick driver = new Joystick(0);
  private final Joystick operator = new Joystick(1);

  //Sets up controller bindings
  private final JoystickButton drawerRetractor = new JoystickButton(driver, XboxController.Button.kBack.value);
  private final JoystickButton drawerExtender = new JoystickButton(driver, XboxController.Button.kStart.value);
  private final JoystickButton gripperClose = new JoystickButton(driver, XboxController.Button.kY.value);
  private final JoystickButton gripperOpen = new JoystickButton(driver, XboxController.Button.kA.value);

  //Gets instances for the two classes used in the button bidings
  private final Drawer s_Drawer = Drawer.getInstance();
  private final Gripper s_Gripper = Gripper.getInstance();

  public RobotContainer() {

    configureBindings();
  }

  private void configureBindings() {

    //When a button is pressed runs its respective method inside drawer
    drawerRetractor.onTrue(new InstantCommand(() -> s_Drawer.retractDrawer()));
    drawerExtender.onTrue(new InstantCommand(() -> s_Drawer.extendDrawer()));

    //When a button is pressed runs its respective method inside gripper
    gripperOpen.onTrue(new InstantCommand(() -> s_Gripper.openGripper()));
    gripperClose.onTrue(new InstantCommand(() -> s_Gripper.closeGripper()));

  }

  public Command getAutonomousCommand() {
    return null;

  }
}
