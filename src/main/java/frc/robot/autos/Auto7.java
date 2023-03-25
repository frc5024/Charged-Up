package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.autoCommands.AutoPath;
import frc.robot.subsystems.Swerve;

public class Auto7 extends SequentialCommandGroup {

    public Auto7() {

        // Play command sequence.
        // Added Instant command to reset the speed of the Swerve to 100% to ensure that it is not in slowmode and can successfully auto level.
        addCommands(new InstantCommand(() -> {
            Swerve.getInstance().speedModifier = Constants.SlowConstants.oneHundredPercentModifier;
        }), new GripperCommand(false),
                new AutoPath("Path7"));
    }

}
