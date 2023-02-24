package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Gripper;

public class GripperCommand extends CommandBase {

  private Gripper gripper;

  private Boolean shouldOpen;

  public GripperCommand(Boolean shouldOpen) {

    // Intializes subsystems.
    gripper = Gripper.getInstance();
    this.shouldOpen = shouldOpen;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    if (shouldOpen == true) {

      gripper.openGripper();

    } else if (shouldOpen == false) {
      // Open gripper and retract drawer.
      gripper.closeGripper();

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
