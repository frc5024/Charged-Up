package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.RobotContainer;

//Able to switch between two states with a button press (Y) (This trigger will be changed in the future)
//Line-break sensor needs to be implemented
//The intention is to delete the simplified states once the sensor is implemented
//States for the drawer with and without the piece are already made, but commented off

public class Drawer extends SubsystemBase {
    private static Drawer mInstance = null;

    private XboxController testingController;

    private Compressor compressor;
    private Solenoid extender;

    // Makes it a singleton
    public static Drawer getInstance() {
        if (mInstance == null) {
            mInstance = new Drawer();
        }
        return mInstance;
    }

    // Creates the states for the Drawer
    public enum drawerStates {

        // Simplified state where the drawer is retracted
        DRAWERIN,

        // Simplified state where the drawer is extended
        DRAWEROUT;

        // IDLE State, waiting for state change
        // IDLE,

        // State when the drawer is empty and retracted
        // EMPTYIN,

        // State when the drawer is empty and extended
        // EMPTYOUT,

        // State when the drawer has a piece and is retracted
        // STOREDIN,

        // State when the drawer has a piece and is extended
        // STOREDOUT;

    }

    // Creates the variable that stores the state machine
    private StateMachine<drawerStates> stateMachine;

    public Drawer() {

        stateMachine = new StateMachine<>("Drawer Statemachine");

        // Assigns the states to their methods
        stateMachine.setDefaultState(drawerStates.DRAWERIN, this::handleDrawerIn);
        stateMachine.addState(drawerStates.DRAWEROUT, this::handleDrawerOut);

        // stateMachine.setDefaultState(drawerStates.IDLE, this::handleIdle);
        // stateMachine.addState(drawerStates.EMPTYIN, this::handleEmptyIn);
        // stateMachine.addState(drawerStates.EMPTYOUT, this::handleEmptyOut);
        // stateMachine.addState(drawerStates.STOREDIN, this::handleStoredIN);
        // stateMachine.addState(drawerStates.STOREDOUT, this::handleStoredOut);

        // Initialize Compressor
        compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
        compressor.disable();

        // Initialize Single Solenoid
        extender = new Solenoid(PneumaticsModuleType.CTREPCM, 7);

        // Inicialize testing Controller
        testingController = new XboxController(0);

    }

    // Method for DRAWERIN State
    public void handleDrawerIn(StateMetadata<drawerStates> metaData) {

        // Sets the solenoid to retracted position when in this state
        if (metaData.isFirstRun()) {
          
            extender.set(false);
        }

        // When Y is pressed sets drawer to DRAWEROUT State

    }

    public void handleDrawerOut(StateMetadata<drawerStates> metaData) {

        // Sets the solenoid to extended position when in this state
        if (metaData.isFirstRun()) {

            extender.set(true);
        }

    }

    public boolean canExtend() {
        // When Y is pressed sets drawer to DRAWERIN State
    
        return false;

    }

    public boolean canRetract() {

        return false;
    }

    public void extendDrawer() {

        if (canExtend()) {
            stateMachine.setState(drawerStates.DRAWEROUT);
        }

    }

    public void retractDrawer() {

        if (canRetract()) {
            stateMachine.setState(drawerStates.DRAWERIN);
        }

    }

    public boolean isExtended() {

        return stateMachine.getCurrentState() == drawerStates.DRAWEROUT;
    }

    public boolean isRetracted() {

        return stateMachine.getCurrentState() == drawerStates.DRAWERIN;
    }

    // Method for IDLE State
    // public void handleIdle(StateMetadata<drawerStates> metaData) {

    // extender.set(false);

    // }

    // Method for EMPTYIN State
    // public void handleEmptyIn(StateMetadata<drawerStates> metaData) {

    // extender.set(false);
    // stateMachine.setState(drawerStates.EMPTYIN);

    // }

    // Method for EMPTYOUT State
    // public void handleEmptyOut(StateMetadata<drawerStates> metaData) {

    // extender.set(true);
    // }

    // Method for STOREDIN State
    // public void handleStoredIN(StateMetadata<drawerStates> metaData) {

    // extender.set(false);
    // }

    // Method for STOREDOUT State
    // public void handleStoredOut(StateMetadata<drawerStates> metaData) {

    // extender.set(true);
    // }

    // Makes the state machine run periodically
    @Override
    public void periodic() {
        stateMachine.update();

    }

}