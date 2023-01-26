package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Drivetrain;


public class OI {
    private static OI instance = null;
    private Drivetrain drivetrain = Drivetrain.getInstance();
   
    public static OI getInstance(){
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }


    private OI() {
        
    }
    
}
