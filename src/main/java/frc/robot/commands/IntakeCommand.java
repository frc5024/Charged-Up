// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drawer;
import frc.robot.subsystems.DrawerSensor;

public class IntakeCommand extends CommandBase {

  public IntakeCommand() {
  
  }

  @Override
  public void initialize() {
    //only works with drawer
    Drawer.getInstance().isRetracted();
  }

 
  @Override
  public void execute() {
    
  }


  @Override
  public void end(boolean interrupted) {}


  @Override
  public boolean isFinished() {
    return false;
  }
}
