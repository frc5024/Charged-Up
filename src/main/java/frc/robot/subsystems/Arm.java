package frc.robot.subsystems;

//this is where we import stuff
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

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
    
    private int desiredPosition;
    private boolean limitPushed;

    // Creates the states for the Arm
    public enum ArmStates {
        IDLE,
        PICKUP,
        DISPATCH,
        MOVING,
        HOLD,
        ZEROING,

    }

    private StateMachine<ArmStates> stateMachine;

    // Makes it a singleton
    public static Arm getInstance() {
        if (mInstance == null) {
            mInstance = new Arm();
        }
        return mInstance;
    }

    private Arm() {

        // adds motor control to the arm
        topMotor = new TalonSRX(Constants.ArmConstants.topMotorID);
        bottomMotor = new TalonSRX(Constants.ArmConstants.bottomMotorID);

        innerLimitSwitch = new DigitalInput(Constants.ArmConstants.innerLimitSwitchID);
        outerLimitSwitch = new DigitalInput(Constants.ArmConstants.outerLimitSwitchID);

        stateMachine = new StateMachine<>("Arm");

        // sets states for the arm, and what methods
        stateMachine.setDefaultState(ArmStates.IDLE, this::handleIdle);
        stateMachine.addState(ArmStates.PICKUP, this::handlePickup);
        stateMachine.addState(ArmStates.DISPATCH, this::handleDispatch);
        stateMachine.addState(ArmStates.MOVING, this::handleMoving);
        stateMachine.addState(ArmStates.HOLD, this::handleHold);
        stateMachine.addState(ArmStates.ZEROING, this::handleZeroing);

        desiredPosition = 0;
        limitPushed = false;
    }

    // creates the methods for each individual state.
    public void handleIdle(StateMetadata<ArmStates> metaData) {

    }

    public void handlePickup(StateMetadata<ArmStates> metaData) {
    }

    public void handleDispatch(StateMetadata<ArmStates> metaData) {

    }

    public void handleMoving(StateMetadata<ArmStates> metaData) {

        if (innerLimitTriggered()) {
            limitPushed = true;
            setSpeed(-Constants.ArmConstants.armSpeed);

        } else if (outerLimitTriggered()) {
            limitPushed = true;
            setSpeed(Constants.ArmConstants.armSpeed);
            
        } else if (!limitPushed) {

            if (topMotor.getSelectedSensorPosition() >= desiredPosition - 20 || topMotor.getSelectedSensorPosition() <= desiredPosition + 20) {

                if (topMotor.getSelectedSensorPosition() > desiredPosition) {
                setSpeed(-Constants.ArmConstants.armSpeed);
                } else if (topMotor.getSelectedSensorPosition() < desiredPosition) {
                setSpeed(Constants.ArmConstants.armSpeed);
                }

            } else {
            stateMachine.setState(ArmStates.HOLD);

            }
            
        } else {
            limitPushed = false;
            stateMachine.setState(ArmStates.HOLD); 
        }  

        

    }

    public void handleHold(StateMetadata<ArmStates> metaData) {
        stopArm();
    }

    public void handleZeroing(StateMetadata<ArmStates> metaData) {
        if (innerLimitTriggered()) {
            limitPushed = true;
            setSpeed(-Constants.ArmConstants.armSpeed/2);
        } else if (!limitPushed) {
            setSpeed(Constants.ArmConstants.armSpeed);

        } else {
            limitPushed = false;
            stopArm();
            topMotor.setSelectedSensorPosition(0);
            stateMachine.setState(ArmStates.HOLD);
        }
    }

    // adds code to the main periodic command in robot
    @Override
    public void periodic() {

        SmartDashboard.putNumber("Arm Position", topMotor.getSelectedSensorPosition());
        SmartDashboard.putBoolean("Inner Limit Triggered", innerLimitTriggered());
        SmartDashboard.putBoolean("Outer Limit Triggered", outerLimitTriggered());

        stateMachine.update();


    }

    public void setDesiredPosition(int position) {
        desiredPosition = position;
    }

    public void startMoving() {
        stateMachine.setState(ArmStates.MOVING);
    }

    public void hold() {
        stateMachine.setState(ArmStates.HOLD); 
    }

    public void setSpeed(double speed){
        topMotor.set(ControlMode.PercentOutput,(speed));
        bottomMotor.set(ControlMode.PercentOutput,(speed));
               
    }

    public void stopArm() {
        topMotor.set(ControlMode.PercentOutput,(0));
        bottomMotor.set(ControlMode.PercentOutput,(0));
    }

    public boolean innerLimitTriggered() {
        return !innerLimitSwitch.get();
    }

    public boolean outerLimitTriggered() {
        return !outerLimitSwitch.get();
    }

    public void startZeroing() {
        stateMachine.setState(ArmStates.ZEROING);

    }

}
