package frc.robot.autos;

import com.pathplanner.lib.PathConstraints;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.ZeroArmCommand;
import frc.robot.commands.autoCommands.AutoLevel;
import frc.robot.commands.autoCommands.AutoPath;

public class Auto1 extends SequentialCommandGroup {

    public Auto1() {

        // Specify velocity limitations.
        PathConstraints velocity = new PathConstraints(1.25, 1);

        // Play command sequence.
        addCommands(new GripperCommand(false),
                new ArmCommand(Constants.ArmConstants.midArmPosition, true),
                new ZeroArmCommand(),
                new AutoPath("Path1", velocity),
                new AutoLevel());
    }

}