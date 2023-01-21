package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class Drivetrain extends SubsystemBase {
    private static Drivetrain mInstance = null;

	private TalonSRX leftMaster;
	private TalonSRX leftSlave;
	private TalonSRX rightMaster;
	private TalonSRX rightSlave;

	public static Drivetrain getInstance(){
		if (mInstance == null){
			mInstance= new Drivetrain();
		}
		return mInstance;
	}
	private Drivetrain(){
		
		this.leftMaster = new TalonSRX(1);
		this.leftSlave = new TalonSRX(2);
		leftSlave.follow(leftMaster);

		this.rightMaster = new TalonSRX(3);
		this.rightSlave = new TalonSRX(4);
		rightSlave.follow(rightMaster);

	}
}


