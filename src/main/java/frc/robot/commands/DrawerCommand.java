package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drawer;
import frc.robot.subsystems.Gripper;

public class DrawerCommand extends CommandBase {

    private Drawer drawer;
    private Gripper gripper;

    private Boolean shouldExtend;

    Timer timer = new Timer;

    public DrawerCommand(Boolean shouldExtend) {

        // Intializes subsystems.
        drawer = Drawer.getInstance();
        gripper = Gripper.getInstance();

        this.shouldExtend = shouldExtend;

        // Adds subsystems as requirements.
        addRequirements(drawer);
        addRequirements(gripper);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

        // Check if command should extend
        if (shouldExtend) {
            // If gripper is open, extend drawer
            if (gripper.isOpen == true) {
                drawer.extendDrawer();
            }

        } else if (!shouldExtend) {
            // Open gripper and retract drawer.
            gripper.openGripper();
            drawer.retractDrawer();

        }

        /*
        //if the drawer is in and the gripper is closed it stays that way
        if (drawer.drawerExtended == false && (gripper.isOpen == false)) {
            drawer.retractDrawer();
            //if the drawer is out and the gripper is closed,
            //the gripper opens and the drawer retracts
        } else if (drawer.drawerExtended == true && (gripper.isOpen == false)) {
            gripper.openGripper();
            drawer.retractDrawer();
            //if the drawer is out and the gripper is open,
            //the drawer retracts
        } else if (drawer.drawerExtended == true && (gripper.isOpen == true)) {
            drawer.retractDrawer();
        } else if (drawer.drawerExtended == false && (gripper.isOpen == true)) {
            drawer.extendDrawer();
        }
        */

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

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
