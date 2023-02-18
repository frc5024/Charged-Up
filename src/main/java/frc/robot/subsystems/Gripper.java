package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

//Used for the solenoids
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

//Allows the use of state machines
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;

public class Gripper extends SubsystemBase {

    // Tells us if an instance of this already exists
    private static Gripper mInstance = null;

    // Initializes Doublesolenoid
    private DoubleSolenoid extender;

    // Makes it a singleton
    public static Gripper getInstance() {
        if (mInstance == null) {
            mInstance = new Gripper();
        }
        return mInstance;
    }

    // Creates the states for the Gripper
    public enum GripperStates {

        // State where the Gripper is closed
        GripClose,

        // State where the Gripper is Open
        GripOpen;

    }

    // Creates the variable that stores the state machine
    private StateMachine<GripperStates> stateMachine;

    private Gripper() {

        // Creates state machine
        stateMachine = new StateMachine<>("Gripper Statemachine");

        // Assigns the states to their methods
        stateMachine.setDefaultState(GripperStates.GripClose, this::handleGripClose);
        stateMachine.addState(GripperStates.GripOpen, this::handleGripOpen);

        // Initialize Double Solenoid
        extender = new DoubleSolenoid(Constants.PneumaticConstants.PneumaticHub, PneumaticsModuleType.REVPH,
                Constants.GripperConstants.GripperOpenChanel,
                Constants.GripperConstants.GripperCloseChanel);
    }

    // Method that states what the Closed state does
    public void handleGripClose(StateMetadata<GripperStates> metaData) {

        if (metaData.isFirstRun()) {

            // Sets the solenoid to retracted position when in this state
            extender.set(Value.kReverse);
        }

    }

    // Method that states what the Closed state does
    public void handleGripOpen(StateMetadata<GripperStates> metaData) {

        if (metaData.isFirstRun()) {

            // Sets the solenoid to extended position when in this state
            extender.set(Value.kForward);
        }

    }

    // Method that opens the gripper
    public void openGripper() {
        stateMachine.setState(GripperStates.GripOpen);
    }

    // Method that closes the gripper
    public void closeGripper() {
        stateMachine.setState(GripperStates.GripClose);
    }

    // Makes the state machine run periodically
    @Override
    public void periodic() {
        stateMachine.update();

    }

}