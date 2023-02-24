package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Gripper;

public class GripperCommand extends CommandBase {

  private Gripper gripper;

  public GripperCommand() {

    // Intializes subsystems.
    gripper = Gripper.getInstance();

    // Adds subsystems as requirements.
    addRequirements(gripper);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (gripper.isOpen == true) {
      gripper.closeGripper();
    } else {
      gripper.openGripper();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
