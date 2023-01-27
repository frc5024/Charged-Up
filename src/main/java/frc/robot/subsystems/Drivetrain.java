package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    private static Drivetrain mInstance = null;
	private Talon rightMaster;
	private Talon leftMaster;


	public static Drivetrain getInstance(){
		if (mInstance == null){
			mInstance= new Drivetrain();
		}
		return mInstance;
	}
	private Drivetrain(){
		rightMaster=new Talon(0);
	}
}


