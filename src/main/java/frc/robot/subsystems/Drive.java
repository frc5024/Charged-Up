
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.DriveCommand;

public class Drive extends SubsystemBase {
  private static Drive mInstance = null;
  private WPI_TalonFX talon1 = new WPI_TalonFX(1);
  private WPI_TalonFX talon2 = new WPI_TalonFX(2);
  private WPI_TalonFX talon3 = new WPI_TalonFX(3);
  private WPI_TalonFX talon4 = new WPI_TalonFX(4);

  public static Drive getInstance() {
		if (mInstance == null) {
			mInstance = new Drive();
		}

		return mInstance;
		
	}
  public Drive() {
    super();
    WPI_TalonFX talon1 = new WPI_TalonFX(1);
    WPI_TalonFX talon2 = new WPI_TalonFX(2);
    WPI_TalonFX talon3 = new WPI_TalonFX(3);
    WPI_TalonFX talon4 = new WPI_TalonFX(4);

    talon2.follow(talon1);
    // talon1.setInverted(true);
    // talon2.setInverted(true);
    talon4.follow(talon3);

    talon3.setInverted(true);
    talon4.setInverted(true);

  }

  public void setSpeed(double leftSpeed, double rightSpeed){
    talon1.set(leftSpeed);
    talon3.set(rightSpeed);

  }

  public void stop(){
		talon1.stopMotor();
		talon3.stopMotor();
	}


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
