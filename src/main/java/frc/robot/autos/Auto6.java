package frc.robot.autos;

import com.pathplanner.lib.PathConstraints;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.autoCommands.AutoPath;
import frc.robot.subsystems.Swerve;

public class Auto6 extends SequentialCommandGroup {

    public Auto6() {

        // Specify velocity limitations.
        PathConstraints velocity = new PathConstraints(1.25, 1);

        // Play command sequence.
        // Added Instant command to reset the speed of the Swerve to 100% to ensure that it is not in slowmode and can successfully auto level.
        addCommands(new InstantCommand(() -> {
            Swerve.getInstance().speedModifier = Constants.SlowConstants.oneHundredPercentModifier;
        }), new GripperCommand(false),
                new ArmCommand(Constants.ArmConstants.hybridArmPosition, true),
                new AutoPath("Path6.1", velocity),
                new GripperCommand(false),
                new AutoPath("Path6.2", velocity),
                new ArmCommand(Constants.ArmConstants.midArmPosition, true),
                new AutoPath("Path6.3", velocity));
    }

}
