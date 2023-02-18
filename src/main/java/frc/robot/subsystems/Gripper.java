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
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Gripper extends SubsystemBase {
    private static Gripper mInstance = null;

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

        // Simplified state where the Gripper is closed
        GripClose,

        // Simplified state where the Gripper is Open
        GripOpen;

    }

    // Creates the variable that stores the state machine
    private StateMachine<GripperStates> stateMachine;

    private Gripper() {

        stateMachine = new StateMachine<>("Gripper Statemachine");

        // Assigns the states to their methods
        stateMachine.setDefaultState(GripperStates.GripClose, this::handleGripClose);
        stateMachine.addState(GripperStates.GripOpen, this::handleGripOpen);

        // Initialize Double Solenoid
        extender = new DoubleSolenoid(Constants.PneumaticConstants.PneumaticHub, PneumaticsModuleType.REVPH,
                Constants.GripperConstants.GripperOpenChanel, Constants.GripperConstants.GripperCloseChanel);

    }

    // Method for GripClose State
    public void handleGripClose(StateMetadata<GripperStates> metaData) {

        // Sets the solenoid to retracted position when in this state
        if (metaData.isFirstRun()) {

            extender.set(Value.kReverse);
        }

    }

    public void handleGripOpen(StateMetadata<GripperStates> metaData) {

        // Sets the solenoid to extended position when in this state
        if (metaData.isFirstRun()) {

            extender.set(Value.kForward);
        }

    }

    // for some reason we don't talk to GripOpen directly. I didn't touch this, so
    // I'm not changing it.
    public void openGripper() {

        stateMachine.setState(GripperStates.GripOpen);

    }

    // for some reason we don't talk to GripClose directly. I didn't touch this, so
    // I'm not changing it.
    public void closeGripper() {

        stateMachine.setState(GripperStates.GripClose);

    }

   

    // Makes the state machine run periodically
    @Override
    public void periodic() {
        stateMachine.update();

    }

}