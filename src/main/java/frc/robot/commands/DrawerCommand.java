package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drawer;
import frc.robot.subsystems.Gripper;
import frc.robot.Constants;

public class DrawerCommand extends CommandBase {

    private Drawer drawer;
    private Gripper gripper;

    private Boolean shouldExtend;

    Timer timer = new Timer();

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
        if (shouldExtend == true) {
            
            timer.start();
            gripper.openGripper();

            if (timer.get()>= Constants.DrawerCommandConstants.gripperVSDrawer && (gripper.isOpen == true)){
           
            timer.reset();
            drawer.extendDrawer();
            }
            else {
                System.out.println("gripper closed");
                timer.reset();

            }
           
        } else if (shouldExtend == false) {

            timer.start();
            gripper.openGripper();

            if (timer.get()>= Constants.DrawerCommandConstants.gripperVSDrawer && (gripper.isOpen == true)){
           
            timer.reset();
            drawer.retractDrawer();
            }
            else{

            System.out.println("gripper closed 2"); // get rid of this once tested

            timer.reset();
            }

        }

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
