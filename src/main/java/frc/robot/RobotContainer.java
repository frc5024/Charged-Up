package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.DrawerCommand;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.TeleopSwerve;
import frc.robot.commands.ZeroArmCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drawer;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Swerve;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
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
    
    private final JoystickButton drawerExtender = new JoystickButton(driver, XboxController.Button.kB.value);
    private final JoystickButton drawerRetractor = new JoystickButton(driver, XboxController.Button.kStart.value);

    /* Operator Buttons */
    private final JoystickButton scoreMid = new JoystickButton(operator, XboxController.Button.kY.value);
    private final JoystickButton scoreHybrid = new JoystickButton(operator, XboxController.Button.kA.value);
    private final JoystickButton zeroEncoder = new JoystickButton(operator, XboxController.Button.kX.value);
    private final JoystickButton gripperClose = new JoystickButton(operator, XboxController.Axis.kLeftTrigger.value);
    private final JoystickButton gripperOpen = new JoystickButton(operator, XboxController.Axis.kRightTrigger.value);

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();
    private final Arm s_arm = Arm.getInstance();
    //Gets instances for the two classes used in the button bidings
    private final Drawer s_Drawer = Drawer.getInstance();
    private final Gripper s_Gripper = Gripper.getInstance();

    public RobotContainer() {
        s_Swerve.setDefaultCommand(
                new TeleopSwerve(s_Swerve, () -> -driver.getRawAxis(translationAxis),
                        () -> -driver.getRawAxis(strafeAxis),
                        () -> -driver.getRawAxis(rotationAxis), () -> robotCentric.getAsBoolean()));

        // Configure the button binding
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));

        /* Operator Buttons */
        scoreMid.onTrue(new ArmCommand(Constants.ArmConstants.midArmPosition, true));
        scoreHybrid.onTrue(new ArmCommand(Constants.ArmConstants.hybridArmPosition, true));
        zeroEncoder.onTrue(new ZeroArmCommand());

        //When a button is pressed runs its respective method inside drawer
        drawerExtender.onTrue(new DrawerCommand(true));
        drawerRetractor.onTrue(new DrawerCommand(false));

        //When a button is pressed runs its respective method inside gripperCommand
        gripperOpen.onTrue(new GripperCommand(true));


    }

    public Command getAutonomousCommand() {
        return null;

    }
}
