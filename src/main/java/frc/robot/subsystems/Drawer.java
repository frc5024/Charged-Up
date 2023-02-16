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
    //tells us if an instance of this already exists
    private static Drawer mInstance = null;
   
    //initializes solenoid object
    private DoubleSolenoid extender;

    //initializes the ultrasonic
    private Ultrasonic m_rangeFinder  = new Ultrasonic(1, 2);;

    //distance returned from the ultrasonic
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

        // creates the UltraSensor
       
        // distance returned from sensor
        distanceMillimeters = m_rangeFinder.getRangeMM();
        //distance from sensor to wall is constant, so if the returned distance is less than that an object is inside
        if (distanceMillimeters < distanceWall) {
            objectIn = true;
        } else {
            objectIn = false;
        }
        //creates state machine
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

            extender.set(Value.kReverse);
        }

        // When Y is pressed sets drawer to DRAWEROUT State

    }


    //I am aware that some of this is pointless, however it works and I don't want to break things
    public void handleDrawerOut(StateMetadata<DrawerStates> metaData) {

        // Sets the solenoid to extended position when in this state
        if (metaData.isFirstRun()) {

           

            extender.set(Value.kForward);
        }

    }

    //extends the drawer
    public void extendDrawer() {


        stateMachine.setState(DrawerStates.DRAWEROUT);

    }

    //
    public void retractDrawer() {


        stateMachine.setState(DrawerStates.DRAWERIN);

    }

    //says the drawe is extended
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