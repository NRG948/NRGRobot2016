package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.DS2016;
import org.usfirst.frc.team948.robot.subsystems.Drive;

public class ManualDriveStraight extends CommandBase {
	private Drive drive = CommandBase.drive;

	public ManualDriveStraight() {
		requires(drive);
	}

	protected void initialize() {
	}

	// Drives on a fixed heading using the right joystick y-value for power
	protected void execute() {
		drive.rawTankDrive(DS2016.leftJoystick.getY(), DS2016.leftJoystick.getY());
	}

	// Command runs forever, until it is interrupted or the battery goes dead
	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		drive.rawStop();
	}

	protected void interrupted() {
		end();
	}
}
