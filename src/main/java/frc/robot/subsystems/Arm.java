package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

public class Arm extends SubsystemBase {
    private static Arm mInstance = null;
    
    private Talon MotorOne;
    private Talon MotorTwo;

    // this is where to set the motor serial adresses
    static int PortOne = 0;
    static int PortTwo = 0;

    // Makes it a singleton
    public static Arm getInstance() {
        if (mInstance == null) {
            mInstance = new Arm();
        }
        return mInstance;
    }

    // Creates the states for the Arm
    public enum ArmStates {
        IDLE,
        PICKUP,
        DISPATCH,
        MIDDLEGOAL,
        HYBRIDGOAL;

    }

    //
    private StateMachine<ArmStates> stateMachine;

    private Arm() {

        // adds motor control to the arm
        MotorOne = new Talon(PortOne);
        MotorTwo = new Talon(PortTwo);

        // sets states for the arm, and what methods
        stateMachine.setDefaultState(ArmStates.IDLE, this::handleIdle);
        stateMachine.addState(ArmStates.PICKUP, this::handlePickup);
        stateMachine.addState(ArmStates.DISPATCH, this::handleDispatch);
        stateMachine.addState(ArmStates.MIDDLEGOAL, this::handleMiddleGoal);
        stateMachine.addState(ArmStates.HYBRIDGOAL, this::handleHYBRIDGOAL);
    }

    // creates the methods for each individual state.
    public void handleIdle(StateMetadata<ArmStates> metaData) {

    }

    public void handlePickup(StateMetadata<ArmStates> metaData) {
    }

    public void handleDispatch(StateMetadata<ArmStates> metaData) {

    }

    public void handleMiddleGoal(StateMetadata<ArmStates> metaData) {

    }

    public void handleHYBRIDGOAL(StateMetadata<ArmStates> metaData) {

    }

    // adds code to the main periodic command in robot
    @Override
    public void periodic() {
        stateMachine.update();

    }
}
