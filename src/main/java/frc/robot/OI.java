package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;


public class OI {
    private static OI instance = null;

    private XboxController driverController;
   
    public static OI getInstance(){
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }


    private OI() {

        driverController = new XboxController(0);

        
    }
    
}
