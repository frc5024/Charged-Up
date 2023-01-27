package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;

public class Drawer extends SubsystemBase {
    private static Drawer mInstance = null;

    private DoubleSolenoid extender;

    //Makes it a singleton
    public static Drawer getInstance() {
        if (mInstance == null) {
            mInstance = new Drawer();
        }
        return mInstance;
    }

    //Creates the states for the Drawer
    public enum drawerStates {
        IDLE,
        EMPTYIN,
        EMPTYOUT,
        STOREDIN,
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

        extender = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);
    }

    //Method for IDLE State
    public void handleIdle(StateMetadata<drawerStates> metaData) {
    
        extender.set(Value.kReverse);
    }

    //Method for EMPTYIN State
    public void handleEmptyIn(StateMetadata<drawerStates> metaData) {
    
        extender.set(Value.kReverse);
    }

    //Method for EMPTYOUT State
    public void handleEmptyOut(StateMetadata<drawerStates> metaData) {

        extender.set(Value.kForward);
    }

    //Method for STOREDIN State
    public void handleStoredIN(StateMetadata<drawerStates> metaData) {

        extender.set(Value.kReverse);
    }

    //Method for STOREDOUT State
    public void handleStoredOut(StateMetadata<drawerStates> metaData) {

        extender.set(Value.kForward);
    }

    //Makes the state machine run periodically
    @Override
    public void periodic() {
        stateMachine.update();

    }

}