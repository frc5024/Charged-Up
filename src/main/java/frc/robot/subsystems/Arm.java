package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

public class Arm extends SubsystemBase {
    private static Arm mInstance = null;

    private Talon MotorOne;
    private Talon MotorTwo;
    private double rate;
    private double distance;
    public int moduleNumber;
    // the numbers are the connected pins
    Encoder encoder = new Encoder(3, 2);

    // this is where to set the motor serial adresses
    static int PortOne = 0;
    static int PortTwo = 1;

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

    // Motor encoding added

    //
    private StateMachine<ArmStates> stateMachine;

    private Arm() {
        // Configures the encoder to return a distance of 4 for every 256 pulses
        // Also changes the units of getRate
        encoder.setDistancePerPulse(4. / 256.);

        // Configures the encoder to consider itself stopped after .1 seconds
        encoder.setMinRate(.1);

        // Reverses the direction of the encoder
        encoder.setReverseDirection(true);

        // Configures an encoder to average its period measurement over 5 samples
        // Can be between 1 and 127 samples
        encoder.setSamplesToAverage(5);
        stateMachine = new StateMachine<>(getName());

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
        // test code, can be deleted
        rate = encoder.getRate();
        distance=encoder.getDistance();
        System.out.println("rate:"+rate+" distance:"+distance);
    }
}
