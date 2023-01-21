package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class Drivetrain extends SubsystemBase {
    private static Drivetrain mInstance = null;

	private WPI_TalonSRX leftMaster;
	private WPI_TalonSRX leftSlave;
	private WPI_TalonSRX rightMaster;
	private WPI_TalonSRX rightSlave;

	public static Drivetrain getInstance(){
		if (mInstance == null){
			mInstance= new Drivetrain();
		}
		return mInstance;
	}
	private Drivetrain(){
		
		this.leftMaster = new WPI_TalonSRX(1);
		this.leftSlave = new WPI_TalonSRX(2);
		leftSlave.follow(leftMaster);

		this.rightMaster = new WPI_TalonSRX(3);
		this.rightSlave = new WPI_TalonSRX(4);
		rightSlave.follow(rightMaster);

	}
}


