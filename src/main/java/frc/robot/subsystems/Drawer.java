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

public class Drawer extends SubsystemBase {
    private static Drawer mInstance = null;

    private DoubleSolenoid extender;

    // Makes it a singleton
    // public static Drawer getInstance() {
    //     if (mInstance == null) {
    //         mInstance = new Drawer();
    //     }
    //     return mInstance;
    // }

    // Creates the states for the Drawer
    public enum drawerStates {

        // Simplified state where the drawer is retracted
        DRAWERIN,

        // Simplified state where the drawer is extended
        DRAWEROUT;

    }

    // Creates the variable that stores the state machine
    private StateMachine<drawerStates> stateMachine;

    public Drawer() {

        stateMachine = new StateMachine<>("Drawer Statemachine");

        // Assigns the states to their methods
        stateMachine.setDefaultState(drawerStates.DRAWERIN, this::handleDrawerIn);
        stateMachine.addState(drawerStates.DRAWEROUT, this::handleDrawerOut);

       
       

        // Initialize Double Solenoid
        extender = new DoubleSolenoid(50,PneumaticsModuleType.REVPH, 14, 15);

        // Inicialize testing Controller

    }

    // Method for DRAWERIN State
    public void handleDrawerIn(StateMetadata<drawerStates> metaData) {

        // Sets the solenoid to retracted position when in this state
        if (metaData.isFirstRun()) {
          
            System.out.println("In");
            extender.set(Value.kReverse);
        }

        // When Y is pressed sets drawer to DRAWEROUT State

    }

    public void handleDrawerOut(StateMetadata<drawerStates> metaData) {

        // Sets the solenoid to extended position when in this state
        if (metaData.isFirstRun()) {

            System.out.println("Out");

            extender.set(Value.kForward);
        }

    }

    public boolean canExtend() {

        return false;

    }

    public boolean canRetract() {

        return false;
    }

    public void extendDrawer() {

             System.out.println("extendDrawer");

            stateMachine.setState(drawerStates.DRAWEROUT);
        

    }

    public void retractDrawer() {

            System.out.println("retractDrawer");

            stateMachine.setState(drawerStates.DRAWERIN);
        

    }

    public boolean isExtended() {

        return stateMachine.getCurrentState() == drawerStates.DRAWEROUT;
    }

    public boolean isRetracted() {

        return stateMachine.getCurrentState() == drawerStates.DRAWERIN;
    }

    // Makes the state machine run periodically
    @Override
    public void periodic() {
        stateMachine.update();

    }

}