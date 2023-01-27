package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;

public class Drawer extends SubsystemBase {
    private static Drawer mInstance = null;

    private Solenoid extender;

    //Makes it a singleton
    public static Drawer getInstance() {
        if (mInstance == null) {
            mInstance = new Drawer();
        }
        return mInstance;
    }

    //Creates the states for the Drawer
    public enum drawerStates {

    //IDLE State, waiting for state change 
        IDLE,

    //State when the drawer is empty and retracted
        EMPTYIN,

    //State when the drawer is empty and extended
        EMPTYOUT,

    //State when the drawer has a piece and is retracted
        STOREDIN,

    //State when the drawer has a piece and is extended
        STOREDOUT;

    }

    //Creates the variable that stores the state machine
    private StateMachine<drawerStates> stateMachine;

    //Assigns the states to their methods
    private Drawer() {
        stateMachine.setDefaultState(drawerStates.IDLE, this::handleIdle);
        stateMachine.addState(drawerStates.EMPTYIN, this::handleEmptyIn);
        stateMachine.addState(drawerStates.EMPTYOUT, this::handleEmptyOut);
        stateMachine.addState(drawerStates.STOREDIN, this::handleStoredIN);
        stateMachine.addState(drawerStates.STOREDOUT, this::handleStoredOut);

    }

    //Method for IDLE State
    public void handleIdle(StateMetadata<drawerStates> metaData) {
    
        extender.set(false);
    }

    //Method for EMPTYIN State
    public void handleEmptyIn(StateMetadata<drawerStates> metaData) {
    
        extender.set(false);
    }

    //Method for EMPTYOUT State
    public void handleEmptyOut(StateMetadata<drawerStates> metaData) {

        extender.set(true);
    }

    //Method for STOREDIN State
    public void handleStoredIN(StateMetadata<drawerStates> metaData) {

        extender.set(false);
    }

    //Method for STOREDOUT State
    public void handleStoredOut(StateMetadata<drawerStates> metaData) {

        extender.set(true);
    }

    //Makes the state machine run periodically
    @Override
    public void periodic() {
        stateMachine.update();

    }

}