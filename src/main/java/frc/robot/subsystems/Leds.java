package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;
import frc.robot.Constants;

public class Leds extends SubsystemBase {
  
private static Spark lEDhub_Rev = new Spark(Constants.LedsConstants.ledID);  

    public enum LEDStates {

        // Simplified state where the drawer is retracted
        LED_GREEN, LED_YELLOW, LED_PURPLE;

    }

    private StateMachine<LEDStates> stateMachine;

    public Leds() {

        stateMachine = new StateMachine<>("LED Statemachine");

        // Assigns the states to their methods
        stateMachine.setDefaultState(LEDStates.LED_GREEN, this::handleLEDgreen);
        stateMachine.addState(LEDStates.LED_YELLOW, this::handleLEDyellow);
        stateMachine.addState(LEDStates.LED_PURPLE, this::handleLEDpurple);

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

    public void makeLEDgreen() {
        stateMachine.setState(LEDStates.LED_GREEN);
    }

    public void makeLEDyellow() {
        stateMachine.setState(LEDStates.LED_YELLOW);
    }

    public void makeLEDpurple() {
        stateMachine.setState(LEDStates.LED_PURPLE);
    }

    @Override
    public void periodic() {
        stateMachine.update();
    }

}