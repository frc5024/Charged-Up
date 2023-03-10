// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SlowCommand extends CommandBase {

  private Swerve s_Swerve;

  /** Creates a new SlowCommand. */
  public SlowCommand() {

    // Use addRequirements() here to declare subsystem dependencies.
    this.s_Swerve = Swerve.getInstance();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    // Check if current speed is set to 100%
    if (s_Swerve.speedModifier == Constants.SlowConstants.oneHundredPercentModifier) {

      // Set speed to 30%
      s_Swerve.speedModifier = Constants.SlowConstants.thirtyPercentModifier;

    } else {

      // Set speed to 100%
      s_Swerve.speedModifier = Constants.SlowConstants.oneHundredPercentModifier;

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
