package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.DS2016;
import org.usfirst.frc.team948.robot.subsystems.Drive;

public class ManualDriveStraight extends CommandBase {
	private Drive drive = CommandBase.drive;
	private double heading;
	public ManualDriveStraight() {
		requires(drive);
	}

	protected void initialize() {
		heading = drive.driveOnHeadingInit(1.0);
	}

	// Drives on a fixed heading using the right joystick y-value for power
	protected void execute() {
		drive.driveOnHeading(heading, DS2016.getRightJSY());
	}

	// Command runs forever, until it is interrupted or the battery goes dead
	protected boolean isFinished() {
		return false;
	}
   //Add a comment here later
	protected void end() {
		drive.rawStop();
		drive.driveOnHeadingEnd();
		drive.setDesiredHeading(heading);
		
	}

	protected void interrupted() {
		end();
		
	}
}
