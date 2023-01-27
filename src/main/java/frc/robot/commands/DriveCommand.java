package frc.robot.commands;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Drive;

public class DriveCommand extends CommandBase {

    private Drive driveTrain = Drive.getInstance();
    private OI oi = OI.getInstance();

    public DriveCommand() {
        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        driveTrain.stop();
    }

    @Override
    public void execute() {
        double rotate = oi.getRotation();
        //DriveTrain.getInstance().handleDriverInputs(oi.getSpeed(), oi.getRotation());
		if(Math.abs(rotate) < 0.1){
            rotate = 0;

        }
		double leftSpeed = oi.getSpeed() + rotate;
		double rightSpeed = oi.getSpeed() - rotate;

		// double max = Math.max(leftSpeed, rightSpeed);

		// if(max > 1){
		// 	leftSpeed /= max;
		// 	rightSpeed /= max;
		// }

		Drive.getInstance().setSpeed(leftSpeed, rightSpeed);

    }

    
    @Override
    public void end(boolean interrupted) {
        driveTrain.stop();
    }

    

}