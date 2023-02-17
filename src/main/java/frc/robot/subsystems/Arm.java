package frc.robot.subsystems;

//This is where we import stuff.
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends SubsystemBase {
    private static Arm mInstance = null;
    
    private TalonSRX topMotor;
    private TalonSRX bottomMotor;

    private DigitalInput innerLimitSwitch;
    private DigitalInput outerLimitSwitch;

    private PIDController pid;
    private double kP;
    private double kI;
    private double kD;
    
    private int desiredPosition;
    private boolean limitPushed;

    // Creates the states for the arm.
    public enum ArmStates {
        IDLE,
        PICKUP,
        DISPATCH,
        MOVING,
        HOLD,
        ZEROING,

    }

    private StateMachine<ArmStates> stateMachine;

    // Makes it a singleton.
    public static Arm getInstance() {
        if (mInstance == null) {
            mInstance = new Arm();
        }
        return mInstance;
    }

    private Arm() {

        // Adds motor control to the arm.
        topMotor = new TalonSRX(Constants.ArmConstants.topMotorID);
        bottomMotor = new TalonSRX(Constants.ArmConstants.bottomMotorID);

        // Initializes the limit switches.
        innerLimitSwitch = new DigitalInput(Constants.ArmConstants.innerLimitSwitchID);
        outerLimitSwitch = new DigitalInput(Constants.ArmConstants.outerLimitSwitchID);

        // Intializes the state machine.
        stateMachine = new StateMachine<>("Arm");

        // Sets states for the arm, and what methods.
        stateMachine.setDefaultState(ArmStates.IDLE, this::handleIdle);
        stateMachine.addState(ArmStates.PICKUP, this::handlePickup);
        stateMachine.addState(ArmStates.DISPATCH, this::handleDispatch);
        stateMachine.addState(ArmStates.MOVING, this::handleMoving);
        stateMachine.addState(ArmStates.HOLD, this::handleHold);
        stateMachine.addState(ArmStates.ZEROING, this::handleZeroing);

        kP = 0.95/1000;
        kI = 0;
        kD = 0/1000;

        pid = new PIDController(kP, kI, kD);

        addChild("PID Controller", pid);

        desiredPosition = 0;
        limitPushed = false;
    }

    // Creates the methods for each individual state.
    public void handleIdle(StateMetadata<ArmStates> metaData) {

    }

    public void handlePickup(StateMetadata<ArmStates> metaData) {
    }

    public void handleDispatch(StateMetadata<ArmStates> metaData) {

    }

    public void handleMoving(StateMetadata<ArmStates> metaData) {

        // Checks if the limit switches are triggered.
        if (innerLimitTriggered()) {
            // If triggered set limitPushed to true, and move the arm in the opposite direction.
            limitPushed = true;
            setSpeed(-Constants.ArmConstants.armSpeed);

        } else if (outerLimitTriggered()) {
            limitPushed = true;
            setSpeed(Constants.ArmConstants.armSpeed);
            
        // Check if the limited is currently being pushed
        } else if (!limitPushed) {

            // if (topMotor.getSelectedSensorPosition() >= desiredPosition - 5 || topMotor.getSelectedSensorPosition() <= desiredPosition + 5)

            // Check if the arm is at its desired position
            if (topMotor.getSelectedSensorPosition() != desiredPosition) {

                // Use pid to set the motor speed, to move the arm to its desired position.
                setSpeed(pid.calculate(topMotor.getSelectedSensorPosition(), desiredPosition));

            } else {
            // If at desired position, set subsystem state to HOLD.
            //stateMachine.setState(ArmStates.HOLD);

            }
            
        } else {
            // If no longer triggering the limit switch,
            // Set limitedPushed to false and set subsystem state to HOLD.
            limitPushed = false;
            stateMachine.setState(ArmStates.HOLD); 
        }         

    }

    public void handleHold(StateMetadata<ArmStates> metaData) {
        // Stop the arm from moving.
        stopArm();
    }

    public void handleZeroing(StateMetadata<ArmStates> metaData) {

        // Checks if the limit switches are triggered.
        if (innerLimitTriggered()) {
            // If triggered set limitPushed to true, and move the arm in the opposite direction at half the speed.
            limitPushed = true;
            setSpeed(-Constants.ArmConstants.armSpeed/2);
        } else if (!limitPushed) {
            setSpeed(Constants.ArmConstants.armSpeed);

        } else {
            // If no longer triggering the limit switch,
            // Set limitPushed to false
            limitPushed = false;
            stopArm();

            // Reset encoder value to zero and set subsystem state to HOLD.
            topMotor.setSelectedSensorPosition(0);
            stateMachine.setState(ArmStates.HOLD);
        }
    }

    // Adds code to the main periodic command in robot.
    @Override
    public void periodic() {

        SmartDashboard.putNumber("Arm Position", topMotor.getSelectedSensorPosition());
        SmartDashboard.putNumber("Top Motor Percentage", topMotor.getMotorOutputPercent());
        SmartDashboard.putString("Arm State", stateMachine.getCurrentState().toString());
        SmartDashboard.putBoolean("Inner Limit Triggered", innerLimitTriggered());
        SmartDashboard.putBoolean("Outer Limit Triggered", outerLimitTriggered());

        // Update the statemachine periodically.
        stateMachine.update();

    }

    /**
   * Sets the desired postion to the input value.
   */
    public void setDesiredPosition(int position) {
        desiredPosition = position;
    }

    /**
   * Sets the subsystem state to MOVING.
   */
    public void startMoving() {
        stateMachine.setState(ArmStates.MOVING);
    }

    /**
   * Sets the subsystem state to HOLD.
   */
    public void hold() {
        stateMachine.setState(ArmStates.HOLD); 
    }

    /**
   * Sets the percent speed of the arm motors to the input value.
   */
    public void setSpeed(double speed){
        speed = MathUtil.clamp(speed, -0.25, 0.25 );
        topMotor.set(ControlMode.PercentOutput,(speed));
        bottomMotor.set(ControlMode.PercentOutput,(speed));
               
    }

    /**
   * Sets the percent speed to zero.
   */
    public void stopArm() {
        topMotor.set(ControlMode.PercentOutput,(0));
        bottomMotor.set(ControlMode.PercentOutput,(0));
    }

    /**
   * Checks if the inner limit switch is currently triggered.
   *
   * @return true or false
   */
    public boolean innerLimitTriggered() {
        return !innerLimitSwitch.get();
    }

    /**
   * Checks if the outer limit switch is currently triggered.
   *
   * @return true or false
   */
    public boolean outerLimitTriggered() {
        return !outerLimitSwitch.get();
    }

    /**
   * Set the subsystem state to ZEROING.
   */
    public void startZeroing() {
        stateMachine.setState(ArmStates.ZEROING);

    }

}
