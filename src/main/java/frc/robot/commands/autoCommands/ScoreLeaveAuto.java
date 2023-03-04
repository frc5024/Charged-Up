package frc.robot.commands.autoCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.ZeroArmCommand;

public class ScoreLeaveAuto extends SequentialCommandGroup {

    public ScoreLeaveAuto() {

        // Play command sequence.
        addCommands(new GripperCommand(false),
            new ArmCommand(Constants.ArmConstants.midArmPosition, true),
            new ZeroArmCommand(),
            new AutoPath("ScoreLeave"));
    }

}
