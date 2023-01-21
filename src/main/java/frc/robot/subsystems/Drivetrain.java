package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;


public class Drivetrain extends SubsystemBase {
    private static Drivetrain mInstance = null;

	private WPI_TalonFX leftMaster;
	private WPI_TalonFX leftSlave;
	private WPI_TalonFX rightMaster;
	private WPI_TalonFX rightSlave;

	public static Drivetrain getInstance(){
		if (mInstance == null){
			mInstance= new Drivetrain();
		}
		return mInstance;
	}
	private Drivetrain(){
		
		this.leftMaster = new WPI_TalonFX(1);
		this.leftSlave = new WPI_TalonFX(2);
		leftSlave.follow(leftMaster);

		this.rightMaster = new WPI_TalonFX(3);
		this.rightSlave = new WPI_TalonFX(4);
		rightSlave.follow(rightMaster);

	}
}


