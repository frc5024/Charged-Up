package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.DriveCommand;

public class Drivetrain extends SubsystemBase {
  private static Drivetrain mInstance = null;
  
  private WPI_TalonFX rightMaster;
  private WPI_TalonFX rightFollower;
  private WPI_TalonFX leftMaster;
  private WPI_TalonFX leftFollower;

  //Sets Singleton
  public static Drivetrain getInstance() {
		if (mInstance == null) {
			mInstance = new Drivetrain();
		}

		return mInstance;
		
	}
  public Drivetrain() {
    super();

    leftMaster = new WPI_TalonFX(1);
    leftFollower = new WPI_TalonFX(2);
    rightMaster = new WPI_TalonFX(3);
    rightFollower = new WPI_TalonFX(4);
    

    leftFollower.follow(leftMaster);
    rightFollower.follow(rightMaster);

  }
  //Tells motors to go forwards/backwards
  public void setSpeed(double leftSpeed, double rightSpeed){
    leftMaster.set(-leftSpeed*0.5);
    rightMaster.set(rightSpeed*0.5);
  }
  //Tells motors to stop
  public void stop(){
		leftMaster.stopMotor();
		rightMaster.stopMotor();
	}


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}