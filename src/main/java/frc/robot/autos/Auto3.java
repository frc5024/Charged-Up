package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.ZeroArmCommand;
import frc.robot.commands.autoCommands.AutoPath;
import frc.robot.subsystems.Swerve;

public class Auto3 extends SequentialCommandGroup {

    public Auto3() {

        // Play command sequence.
        addCommands(new InstantCommand(() -> {
            Swerve.getInstance().speedModifier = 1.00;
        }), new GripperCommand(false),
                new AutoPath("Path3"));
    }

}
