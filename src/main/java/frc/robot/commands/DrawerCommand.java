package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drawer;
import frc.robot.subsystems.Gripper;

public class DrawerCommand extends CommandBase {

    private Drawer drawer;
    private Gripper gripper;

    private Boolean shouldExtend;

    Timer timer = new Timer();
    private static Spark lEDhub_Rev = new Spark(Constants.LedsConstants.ledID);

    public DrawerCommand(Boolean shouldExtend) {

        // Intializes subsystems.
        drawer = Drawer.getInstance();
        gripper = Gripper.getInstance();

        this.shouldExtend = shouldExtend;

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        
        timer.reset();
        timer.start();
        gripper.openGripper();

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        if (gripper.isOpen == false) {

            cancel();
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

        if (shouldExtend == true) {
        
            drawer.extendDrawer();
            lEDhub_Rev.set(Constants.LedsConstants.lEDcolour_RED);
        }

        if (shouldExtend == false) {

            
            drawer.retractDrawer();
            lEDhub_Rev.set(Constants.LedsConstants.lEDcolour_GREEN);
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return timer.get() >= Constants.DrawerCommandConstants.gripperVSDrawer;
    }
}
