package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;

public class Grabber extends SubsystemBase {
    private static Grabber mInstance = null;
   
    //Makes it a singleton
    public static Grabber getInstance() {
        if (mInstance == null) {
            mInstance = new Grabber();
        }
        return mInstance;
    }

   //Creates the states for the BOX
    public enum GrabberStates {
        
        IDLE,
        GRABBEROPEN,
        GRABBERCLOSED;
    }

    //Creates the variable that stores the state machine 
    private StateMachine<GrabberStates> stateMachine;
   
    //Assigns the states to their methods
    private Grabber() {
        stateMachine.setDefaultState(GrabberStates.IDLE, this::handleIdle);
        stateMachine.addState(GrabberStates.GRABBEROPEN, this::handleGrabberOpen);
        stateMachine.addState(GrabberStates.GRABBERCLOSED, this::handleGrabberClosed);
    }
    public void handleIdle(StateMetadata<GrabberStates> metaData) {

    }

    public void handleGrabberOpen(StateMetadata<GrabberStates> metaData) {

    }

    public void handleGrabberClosed(StateMetadata<GrabberStates> metaData) {

    }









}