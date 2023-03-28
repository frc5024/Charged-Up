package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.DrawerCommand;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.TeleopSwerve;
import frc.robot.commands.ZeroArmCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drawer;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Leds;
import frc.robot.subsystems.Swerve;

public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);
    public final Joystick operator = new Joystick(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kBack.value);

    private final JoystickButton drawerExtender = new JoystickButton(driver, XboxController.Button.kB.value);
    private final JoystickButton drawerRetractor = new JoystickButton(driver, XboxController.Button.kStart.value);

    /* Operator Buttons */
    private final JoystickButton scoreMid = new JoystickButton(operator, XboxController.Button.kY.value);
    private final JoystickButton scoreHybrid = new JoystickButton(operator, XboxController.Button.kA.value);
    private final JoystickButton scoreClosed = new JoystickButton(operator, XboxController.Button.kB.value);
    private final JoystickButton shelfPosition = new JoystickButton(operator, XboxController.Button.kStart.value);
    private final JoystickButton zeroEncoder = new JoystickButton(operator, XboxController.Button.kX.value);
    private final JoystickButton gripperClose = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
    private final JoystickButton gripperOpen = new JoystickButton(operator, XboxController.Button.kRightBumper.value);

    /* operator Buttons */
    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();
    private final Arm s_arm = Arm.getInstance();
    //Gets instances for the two classes used in the button bidings
    private final Drawer s_Drawer = Drawer.getInstance();
    private final Gripper s_Gripper = Gripper.getInstance();
    private final Leds s_Leds = new Leds();

    // Maps Leds to Dpad 0=u0, 90=right, 180=down, 270=left
    private final POVButton upButton = new POVButton(operator, 0, operator.getPOV());
    private final POVButton rightButton = new POVButton(operator, 90, operator.getPOV());
    private final POVButton downButton = new POVButton(operator, 270, operator.getPOV());
    private final POVButton leftButton = new POVButton(operator, 270, operator.getPOV());
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
                new TeleopSwerve(s_Swerve, () -> -driver.getRawAxis(translationAxis),
                        () -> -driver.getRawAxis(strafeAxis),
                        () -> -driver.getRawAxis(rotationAxis), () -> robotCentric.getAsBoolean()));

        // Configure the button binding
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));

        /* Operator Buttons */
        scoreMid.onTrue(new ArmCommand(Constants.ArmConstants.midArmPosition, true));
        scoreHybrid.onTrue(new ArmCommand(Constants.ArmConstants.hybridArmPosition, true));
        scoreClosed.onTrue(new ArmCommand(Constants.ArmConstants.midArmPosition, false));
        shelfPosition.onTrue(new ArmCommand(Constants.ArmConstants.shelfPosition, false));
        zeroEncoder.onTrue(new ZeroArmCommand());

        //When a button is pressed runs its respective method inside drawer
        drawerExtender.onTrue(new DrawerCommand(true));
        drawerRetractor.onTrue(new DrawerCommand(false));

        //When a button is pressed runs its respective method inside gripperCommand
        gripperOpen.onTrue(new GripperCommand(true));
        gripperClose.onTrue(new GripperCommand(false));

        // When DPads are pressed, the LEDS turn their respective colour
        upButton.onTrue(new InstantCommand(() -> s_Leds.makeLEDgreen()));
        rightButton.onTrue(new InstantCommand(() -> s_Leds.makeLEDyellow()));
        downButton.onTrue(new InstantCommand(() -> s_Leds.makeLEDpurple()));
        leftButton.onTrue(new InstantCommand(() -> s_Leds.makeLEDblack()));
    }

    public Command getAutonomousCommand() {
        return null;

    }
}
