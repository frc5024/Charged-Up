// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Swerve;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Park extends InstantCommand {

  // get swerve object instance
  private Swerve s_Swerve = Swerve.getInstance();

  public Park() {
    // Use addRequirements() here to declare subsystem dependencies.

    addRequirements(s_Swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    // turn slightly to lock motor angle
    s_Swerve.drive(new Translation2d(0,0), 0.01, false, true);
  }
}
