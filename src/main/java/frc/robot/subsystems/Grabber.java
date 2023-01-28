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

    // Creates the states for the grabber.
    public enum GrabberStates {

    //State when the grabber is open 
        GRABBEROPEN,

    //State when the grabber is closed
        GRABBERCLOSED;

    }

    //Creates the variable that stores the state machine 
    private StateMachine<GrabberStates> stateMachine;
   
    //Assigns the states to their methods
    private Grabber() {
        stateMachine.addState(GrabberStates.GRABBEROPEN, this::handleGrabberOpen);
        stateMachine.addState(GrabberStates.GRABBERCLOSED, this::handleGrabberClosed);
    }

    @Override
    public void periodic() {
        // updates the current state, periodically.
        stateMachine.update();


    }

    /**
     * Controls the functions of the system when in the "Grabber Open" state.
     * 
     * @param metaData
     */
    public void handleGrabberOpen(StateMetadata<GrabberStates> metaData) {

    }

    /**
     * Controls the functions of the system when in the "Grabber Closed" state.
     * 
     * @param metaData
     */
    public void handleGrabberClosed(StateMetadata<GrabberStates> metaData) {

    }



    /** 
     * TODO: This method should check and then decide if the claw will be allowed to open.
     * 
     * @return true or false based on if grabber is allowed to open.
    */
    public boolean canOpen() {
        // Placeholder
        return false;
    }


    /**
     * TODO: Should do the same as the previous method, but for closing the grabber.
     * 
     * @return true or false based on if the grabber is allowed to close.
     */
    public boolean canClose() {
        // Placeholder
        return false;
    }









}