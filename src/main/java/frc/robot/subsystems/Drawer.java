package frc.robot.subsystems;

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
import frc.robot.RobotContainer;

public class Drawer extends SubsystemBase {
    private static Drawer mInstance = null;

    private DoubleSolenoid extender;

    private Ultrasonic m_rangeFinder;

    private double distanceMillimeters;
    // distance from sensor to drawer wall, MUST be in mm
    private final double distanceWall = 0;
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

        // creates the UtraSensor
        Ultrasonic m_rangeFinder = new Ultrasonic(1, 2);
        // distance returned from sensor

        distanceMillimeters = m_rangeFinder.getRangeMM();
        if (distanceMillimeters < distanceWall) {
            objectIn = true;
        } else {
            objectIn = false;
        }

        stateMachine = new StateMachine<>("Drawer Statemachine");

        // Assigns the states to their methods
        stateMachine.setDefaultState(DrawerStates.DRAWERIN, this::handleDrawerIn);
        stateMachine.addState(DrawerStates.DRAWEROUT, this::handleDrawerOut);

        // Initialize Double Solenoid
        extender = new DoubleSolenoid(50, PneumaticsModuleType.REVPH, 13, 12);

        // Inicialize testing Controller

    }

    // Method for DRAWERIN State
    public void handleDrawerIn(StateMetadata<DrawerStates> metaData) {

        // Sets the solenoid to retracted position when in this state
        if (metaData.isFirstRun()) {

            System.out.println("In");
            extender.set(Value.kReverse);
        }

        // When Y is pressed sets drawer to DRAWEROUT State

    }

    public void handleDrawerOut(StateMetadata<DrawerStates> metaData) {

        // Sets the solenoid to extended position when in this state
        if (metaData.isFirstRun()) {

            System.out.println("Out");

            extender.set(Value.kForward);
        }

    }

    public boolean canExtend() {

        return false;

    }

    public boolean canRetract() {

        return false;
    }

    public void extendDrawer() {

        System.out.println("extendDrawer");

        stateMachine.setState(DrawerStates.DRAWEROUT);

    }

    public void retractDrawer() {

        System.out.println("retractDrawer");

        stateMachine.setState(DrawerStates.DRAWERIN);

    }

    public boolean isExtended() {

        return stateMachine.getCurrentState() == DrawerStates.DRAWEROUT;
    }

    public boolean isRetracted() {

        return stateMachine.getCurrentState() == DrawerStates.DRAWERIN;
    }

    // Makes the state machine run periodically
    @Override
    public void periodic() {
        stateMachine.update();

    }

}