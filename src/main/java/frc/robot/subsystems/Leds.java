package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.liblite.StateMachine;
import frc.robot.liblite.StateMetadata;

public class Leds extends SubsystemBase {
    private static Spark LEDhub_Rev = new Spark(9);
    private static double LEDcolour_GREEN = 0.71;
    private static double LEDcolour_YELLOW = 0.69;
    private static double LEDcolour_PURPLE = 0.91;

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
        LEDhub_Rev.set(LEDcolour_GREEN);
    }

    // Method for LED_GREEN State
    public void handleLEDgreen(StateMetadata<LEDStates> metaData) {
        // Sets the LED to green
        if (metaData.isFirstRun()) {
            LEDhub_Rev.set(LEDcolour_GREEN);
        }
    }

    // Method for LED__YELLOW State
    public void handleLEDyellow(StateMetadata<LEDStates> metaData) {
        // Sets the LED to yellow
        if (metaData.isFirstRun()) {
            LEDhub_Rev.set(LEDcolour_YELLOW);
        }
    }

    // Method for LED_GREEN State
    public void handleLEDpurple(StateMetadata<LEDStates> metaData) {
        // Sets the LED to purple
        if (metaData.isFirstRun()) {
            LEDhub_Rev.set(LEDcolour_PURPLE);
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