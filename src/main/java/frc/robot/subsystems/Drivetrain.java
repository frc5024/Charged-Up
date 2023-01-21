package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    private static Drivetrain mInstance = null;

	public static Drivetrain getInstance(){
		if (mInstance == null){
			mInstance= new Drivetrain();
		}
		return mInstance;
	}
	private Drivetrain(){
		
	}
}


