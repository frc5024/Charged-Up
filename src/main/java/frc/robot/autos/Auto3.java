package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.ZeroArmCommand;
import frc.robot.commands.autoCommands.AutoPath;

public class Auto3 extends SequentialCommandGroup {

    public Auto3() {

        // Play command sequence.
        addCommands(new GripperCommand(false),
                new ZeroArmCommand(),
                new AutoPath("Path3"));
    }

}
