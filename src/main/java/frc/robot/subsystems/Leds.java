package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;

public class Leds extends SubsystemBase {
    private static Spark lEDhub_Rev = new Spark(Constants.LedsConstants.ledID);

    public int ledsDegrees = 0;

    public enum LEDStates {

        LED_GREEN, LED_YELLOW, LED_PURPLE,LED_BLACK;

    }

    private StateMachine<LEDStates> stateMachine;

    public Leds() {

        stateMachine = new StateMachine<>("LED Statemachine");

        // Assigns the states to their methods
        stateMachine.addState(LEDStates.LED_GREEN, this::handleLEDgreen);
        stateMachine.addState(LEDStates.LED_YELLOW, this::handleLEDyellow);
        stateMachine.addState(LEDStates.LED_PURPLE, this::handleLEDpurple);
        stateMachine.setDefaultState(LEDStates.LED_PURPLE, this::handleLEDblack);

        // Initialize LED to Green
        lEDhub_Rev.set(Constants.LedsConstants.lEDcolour_GREEN);
    }

    // Method for LED_GREEN State
    public void handleLEDgreen(StateMetadata<LEDStates> metaData) {
        // Sets the LED to green
        if (metaData.isFirstRun()) {
            lEDhub_Rev.set(Constants.LedsConstants.lEDcolour_GREEN);
        }

    }

    // Method for LED__YELLOW State
    public void handleLEDyellow(StateMetadata<LEDStates> metaData) {
        // Sets the LED to yellow
        if (metaData.isFirstRun()) {
            lEDhub_Rev.set(Constants.LedsConstants.lEDcolour_YELLOW);
        }

    }

    // Method for LED_Purple State
    public void handleLEDpurple(StateMetadata<LEDStates> metaData) {
        // Sets the LED to purple
        if (metaData.isFirstRun()) {
            lEDhub_Rev.set(Constants.LedsConstants.lEDcolour_PURPLE);
        }

    }

    public void handleLEDblack(StateMetadata<LEDStates> metaData) {
        // Sets the LED to purple
        if (metaData.isFirstRun()) {
            lEDhub_Rev.set(Constants.LedsConstants.lEDcolour_BLACK);
        }

    }


    public void makeLEDgreen() {
        stateMachine.setState(LEDStates.LED_GREEN);
    }

    public void makeLEDyellow() {
        stateMachine.setState(LEDStates.LED_YELLOW);
    }

    public void makeLEDpurple() {
        stateMachine.setState(LEDStates.LED_PURPLE);
    }
    
    public void makeLEDblack() {
        stateMachine.setState(LEDStates.LED_BLACK);
    }

    @Override
    public void periodic() {
        
        
        stateMachine.update();
    }

}
