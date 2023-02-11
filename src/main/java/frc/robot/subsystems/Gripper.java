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

import frc.robot.RobotContainer;

public class Gripper extends SubsystemBase {
    private static Gripper mInstance = null;

    private DoubleSolenoid extender;

    // Makes it a singleton
    // public static Drawer getInstance() {
    //     if (mInstance == null) {
    //         mInstance = new Drawer();
    //     }
    //     return mInstance;
    // }
    // Creates the states for the Gripper
    public enum GripperStates {

        // Simplified state where the Gripper is closed
        GripClose,

        // Simplified state where the Gripper is Open
        GripOpen;

    }

    // Creates the variable that stores the state machine
    private StateMachine<GripperStates> stateMachine;

    public Gripper() {

        stateMachine = new StateMachine<>("Gripper Statemachine");

        // Assigns the states to their methods
        stateMachine.setDefaultState(GripperStates.GripClose, this::handleGripClose);
        stateMachine.addState(GripperStates.GripOpen, this::handleGripOpen);

       
       

        // Initialize Double Solenoid
        extender = new DoubleSolenoid(50,PneumaticsModuleType.REVPH, 14, 15);

    }

    // Method for GripClose State
    public void handleGripClose(StateMetadata<GripperStates> metaData) {

        // Sets the solenoid to retracted position when in this state
        if (metaData.isFirstRun()) {
          
            System.out.println("Close");
            extender.set(Value.kReverse);
        }

    }

    public void handleGripOpen(StateMetadata<GripperStates> metaData) {

        // Sets the solenoid to extended position when in this state
        if (metaData.isFirstRun()) {

            System.out.println("Open");

            extender.set(Value.kForward);
        }

    }

    public boolean canOpen() {

        return false;

    }

    public boolean canClose() {

        return false;
    }

    public void openGripper() {

             System.out.println("openGripper");

            stateMachine.setState(GripperStates.GripOpen);
        

    }

    public void closeGripper() {

            System.out.println("closeGripper");

            stateMachine.setState(GripperStates.GripClose);
        

    }

    public boolean isOpen() {

        return stateMachine.getCurrentState() == GripperStates.GripOpen;
    }

    public boolean isClosed() {

        return stateMachine.getCurrentState() == GripperStates.GripClose;
    }

    // Makes the state machine run periodically
    @Override
    public void periodic() {
        stateMachine.update();

    }

}