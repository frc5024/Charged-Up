package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);
    private final Joystick operator = new Joystick(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

    /* operator Buttons */
    private final JoystickButton makeLEDpurple = new JoystickButton(operator, XboxController.Button.kX.value);
    private final JoystickButton makeLEDyellow = new JoystickButton(operator, XboxController.Button.kY.value);
    private final JoystickButton makeLEDgreen = new JoystickButton(operator, XboxController.Button.kA.value);

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();
    private final Leds s_Leds = new Leds();


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );


        // Configure the button bindings
        configureButtonBindings();
    }


    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        
        makeLEDgreen.onTrue(new InstantCommand(() -> s_Leds.makeLEDgreen()));
        makeLEDyellow.onTrue(new InstantCommand(() -> s_Leds.makeLEDyellow()));
        makeLEDpurple.onTrue(new InstantCommand(() -> s_Leds.makeLEDpurple()));
    }

   
    public Command getAutonomousCommand() {
    
        return new exampleAuto(s_Swerve);
    }
}
