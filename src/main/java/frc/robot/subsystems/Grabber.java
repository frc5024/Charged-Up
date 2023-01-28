package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;

public class Grabber extends SubsystemBase {
    private static Grabber mInstance = null;

    private DoubleSolenoid grabberSolenoid;
   
    /**
     * Gets the instance for the grabber
     * 
     * @return Grabber instance
     */
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
   
    // Constructor for the Grabber subsystem
    private Grabber() {


        // Construct the grabber solenoid
        grabberSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.GrabberConstants.solenoidForward, Constants.GrabberConstants.solenoidReverse);

        // Setup statemachine states
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

        // Run code only on first run of state method
        if (metaData.isFirstRun()) {

            // sets the pnuematic cylinder to retract and open the grabber
            grabberSolenoid.set(Value.kReverse);
        }

    }

    /**
     * Controls the functions of the system when in the "Grabber Closed" state.
     * 
     * @param metaData
     */
    public void handleGrabberClosed(StateMetadata<GrabberStates> metaData) {

        // Run this code only on first run of state method
        if (metaData.isFirstRun()) {
            
            // Sets the pneumatic cylinder to extend and close the grabber
            grabberSolenoid.set(Value.kForward);
        }

    }

    /** 
     * TODO: This method should check and then decide if the claw will be allowed to open.
     * 
     * @return True or False
    */
    public boolean canOpen() {
        // Placeholder
        return false;
    }

    /**
     * TODO: Should do the same as the previous method, but for closing the grabber.
     * 
     * @return True or False
     */
    public boolean canClose() {
        // Placeholder
        return false;
    }

    /**
     * Opens the grabber if it is allowed
     */
    public void openGrabber() {

        // set state to GRABBEROPEN only if canOpen returns true
        if (canOpen()) {
            stateMachine.setState(GrabberStates.GRABBEROPEN);
        }

    }

    /**
     * Closes the grabber if it is allowed
     */
    public void closeGrabber() {

        // set state to GRABBERCLOSED only if canClose returns true
        if (canClose()) {
            stateMachine.setState(GrabberStates.GRABBERCLOSED);
        }

    }

    /**
     * Checks if the grabber is in the open state
     * 
     * @return True or False
     */
    public boolean isOpen() {

        // Returns wether the current state is GRABBEROPEN or not
        return stateMachine.getCurrentState() == GrabberStates.GRABBEROPEN;
    }

    public boolean isClosed() {

        // Returns wether the current state is GRABBERCLOSED or not
        return stateMachine.getCurrentState() == GrabberStates.GRABBERCLOSED;
    }













}