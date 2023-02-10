package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DrawerSensor  extends SubsystemBase{
    private static DrawerSensor mInstance = null;
    Ultrasonic m_rangeFinder = new Ultrasonic(1, 2);
    //distance returned from sensor
    double distanceMillimeters;
    //distance from sensor to drawer wall, MUST be in mm
    final double distanceWall=0;
    //says if an object is in the drawer
    Boolean objectIn = false;
    // Makes it a singleton
    public static DrawerSensor getInstance() {
        if (mInstance == null) {
            mInstance = new DrawerSensor();
        }
        return mInstance;
    }

    private DrawerSensor(){
        //This feels like it should be it's own system but nobody else agrees and I am a slave to public opinion/j
        //checks if an object is in the drawer
        //distance from sensor to drawe wall is constant, if it returns a value less than that an object is inside
        distanceMillimeters=m_rangeFinder.getRangeMM();
        if(distanceMillimeters<distanceWall){
            objectIn=true;
        }
        else{
            objectIn=false;
        }

        //actual DrawerSensor code starts here

    }
    @Override
    public void periodic() {
    }

}