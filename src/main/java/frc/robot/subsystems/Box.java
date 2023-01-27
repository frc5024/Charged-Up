package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;

public class Box extends SubsystemBase {
    private static Box mInstance = null;

    //Makes it a singleton
    public static Box getInstance() {
        if (mInstance == null) {
            mInstance = new Box();
        }
        return mInstance;
    }

    //Creates the states for the BOX
    public enum BoxStates {
        IDLE,
        EMPTYIN,
        EMPTYOUT,
        STOREDIN,
        STOREDOUT;

    }

    //Creates the variable that stores the states machine 
    private StateMachine<BoxStates> stateMachine;

    //Assigns the states to their methods
    private Box() {
        stateMachine.setDefaultState(BoxStates.IDLE, this::handleIdle);
        stateMachine.addState(BoxStates.EMPTYIN, this::handleEmptyIn);
        stateMachine.addState(BoxStates.EMPTYOUT, this::handleEmptyOut);
        stateMachine.addState(BoxStates.STOREDIN, this::handleStoredIN);
        stateMachine.addState(BoxStates.STOREDOUT, this::handleStoredOut);
    }

    //Method for IDLE State
    public void handleIdle(StateMetadata<BoxStates> metaData) {

    }

    //Method for EMPTYIN State
    public void handleEmptyIn(StateMetadata<BoxStates> metaData) {

    }

    //Method for EMPTYOUT State
    public void handleEmptyOut(StateMetadata<BoxStates> metaData) {

    }

    //Method for STOREDIN State
    public void handleStoredIN(StateMetadata<BoxStates> metaData) {

    }

    //Method for STOREDOUT State
    public void handleStoredOut(StateMetadata<BoxStates> metaData) {

    }

    //Makes the state machine run periodically
    @Override
    public void periodic() {
        stateMachine.update();

    }

}