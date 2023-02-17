package frc.robot.subsystems;

import edu.wpi.first.hal.I2CJNI;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;
import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj.Ultrasonic;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Drawer extends SubsystemBase {
    // tells us if an instance of this already exists
    private static Drawer mInstance = null;
    private Boolean drawerExtended;
    // initializes solenoid object
    private DoubleSolenoid extender;

    // initializes the ultrasonic
    private Ultrasonic m_rangeFinder = new Ultrasonic(Constants.DrawerAndGripperConstants.usPingChanel,
            Constants.DrawerAndGripperConstants.usEchoChanel);

    // distance returned from the ultrasonic
    private double distanceMillimeters;
    // distance from sensor to drawer wall, MUST be in mm
    // says if an object is in the drawer
    private Boolean objectIn = false;

    // Makes it a singleton
    public static Drawer getInstance() {
        if (mInstance == null) {
            mInstance = new Drawer();
        }
        return mInstance;
    }

    // Creates the states for the Drawer
    public enum DrawerStates {

        // Simplified state where the drawer is retracted
        DRAWERIN,

        // Simplified state where the drawer is extended
        DRAWEROUT;

    }

    // Creates the variable that stores the state machine
    private StateMachine<DrawerStates> stateMachine;

    private Drawer() {

        // creates state machine
        stateMachine = new StateMachine<>("Drawer Statemachine");

        // Assigns the states to their methods
        stateMachine.setDefaultState(DrawerStates.DRAWERIN, this::handleDrawerIn);
        stateMachine.addState(DrawerStates.DRAWEROUT, this::handleDrawerOut);

        // Initialize Double Solenoid
        extender = new DoubleSolenoid(Constants.DrawerAndGripperConstants.PneumaticHub, PneumaticsModuleType.REVPH,
                Constants.DrawerAndGripperConstants.drawerForwardChanel,
                Constants.DrawerAndGripperConstants.drawerReverseChanel);

        // Inicialize testing Controller

    }

    // moves the drawer in, handles solenoids
    public void handleDrawerIn(StateMetadata<DrawerStates> metaData) {

        // Sets the solenoid to retracted position when in this state
        if (metaData.isFirstRun()) {

            extender.set(Value.kReverse);
            drawerExtended = false;
        }

        // When Y is pressed sets drawer to DRAWEROUT State

    }

    // moves the drawer out, handles solenoids
    public void handleDrawerOut(StateMetadata<DrawerStates> metaData) {

        // Sets the solenoid to extended position when in this state
        if (metaData.isFirstRun()) {

            extender.set(Value.kForward);
            drawerExtended = true;
        }

    }

    // extends the drawer, callable from outside the class
    public void extendDrawer() {

        stateMachine.setState(DrawerStates.DRAWEROUT);

    }

    // retracts drawer, callable from outside the class
    public void retractDrawer() {

        stateMachine.setState(DrawerStates.DRAWERIN);

    }

    // Makes the state machine run periodically
    @Override
    public void periodic() {
        stateMachine.update();
        // distance returned from sensor
        distanceMillimeters = m_rangeFinder.getRangeMM();
        System.out.println(distanceMillimeters);
        // distance from sensor to wall is constant, so if the returned distance is less
        // than that an object is inside
        // we also get a weird glitch where the cube reads at this value or higher
        if (distanceMillimeters < Constants.DrawerAndGripperConstants.distanceWall
                || distanceMillimeters >= Constants.DrawerAndGripperConstants.CubeNonsenseValue
                        && drawerExtended == true) {
            objectIn = true;
            retractDrawer();
        } else {
            objectIn = false;
        }

    }

}