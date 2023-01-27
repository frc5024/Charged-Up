package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;

public class Drawer extends SubsystemBase {
    private static Drawer mInstance = null;

    //Makes it a singleton
    public static Drawer getInstance() {
        if (mInstance == null) {
            mInstance = new Drawer();
        }
        return mInstance;
    }

    //Creates the states for the Drawer
    public enum DrawerStates {
        IDLE,
        EMPTYIN,
        EMPTYOUT,
        STOREDIN,
        STOREDOUT;

    }

    //Creates the variable that stores the state machine
    private StateMachine<DrawerStates> stateMachine;

    //Assigns the states to their methods
    private Drawer() {
        stateMachine.setDefaultState(DrawerStates.IDLE, this::handleIdle);
        stateMachine.addState(DrawerStates.EMPTYIN, this::handleEmptyIn);
        stateMachine.addState(DrawerStates.EMPTYOUT, this::handleEmptyOut);
        stateMachine.addState(DrawerStates.STOREDIN, this::handleStoredIN);
        stateMachine.addState(DrawerStates.STOREDOUT, this::handleStoredOut);
    }

    //Method for IDLE State
    public void handleIdle(StateMetadata<DrawerStates> metaData) {

    }

    //Method for EMPTYIN State
    public void handleEmptyIn(StateMetadata<DrawerStates> metaData) {

    }

    //Method for EMPTYOUT State
    public void handleEmptyOut(StateMetadata<DrawerStates> metaData) {

    }

    //Method for STOREDIN State
    public void handleStoredIN(StateMetadata<DrawerStates> metaData) {

    }

    //Method for STOREDOUT State
    public void handleStoredOut(StateMetadata<DrawerStates> metaData) {

    }

    //Makes the state machine run periodically
    @Override
    public void periodic() {
        stateMachine.update();

    }

}