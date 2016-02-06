
package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.DS2016;

/**
 *
 */
public class ManualDrive extends CommandBase {

    public ManualDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(CommandBase.drive);
    }

   

	// Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drive.rawTankDrive(DS2016.getLeftJSY(),
    		    DS2016.getRightJSY());

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
		drive.rawStop();
		drive.setDesiredHeadingFromGyro();  
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
