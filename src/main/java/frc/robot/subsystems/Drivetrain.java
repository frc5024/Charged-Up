package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    private static Drivetrain mInstance = null;

	private ExtendedTalonSRX rightMaster;
	private ExtendedTalonSRX leftMaster;
}
