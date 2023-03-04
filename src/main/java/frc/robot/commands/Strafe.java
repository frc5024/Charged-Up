// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

public class Strafe extends CommandBase {

  double strafeValue;
  private Swerve s_Swerve;

  /** Creates a new Strafe. */
  public Strafe(double strafeValue) {
    // Use addRequirements() here to declare subsystem dependencies.

    strafeValue = this.strafeValue;
    s_Swerve = Swerve.getInstance();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    s_Swerve.drive(new Translation2d(0, strafeValue), 0, false, false);
    //strafeValue = 0.0;
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
