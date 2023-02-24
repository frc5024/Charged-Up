// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;

public class ArmCommand extends CommandBase {

  private Arm arm;

  private Gripper gripper;

  private int position;

  private boolean openGrabber;

  /** Creates a new Armcommand. */
  public ArmCommand(int position, boolean openGrabber) {

    // Intializes subsystems and variables.
    arm = Arm.getInstance();
    gripper = Gripper.getInstance();
    this.position = position;
    this.openGrabber = openGrabber;

    // Adds subsystems as requirements.
    addRequirements(arm);
    addRequirements(gripper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Set the arms desried position and start the moving process.
    arm.setDesiredPosition(position);
    arm.setReleaseOnFinish(openGrabber);
    arm.startMoving();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    // Make sure arm is ready to dispatch,
    // In case the command is ending on an interruption.
    if (arm.readyToDispatch()) {
      // Open the gripper
      gripper.openGripper();

    }

    // Set the arm's state to hold
    arm.hold();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // End command when arm is ready to dispatch the game piece.
    return arm.readyToDispatch();

  }
}
