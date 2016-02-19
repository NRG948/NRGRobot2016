package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.DS2016;

public class ManualTrackAcquirer extends CommandBase{

	private static final double MAX_ARM_ANGLE = 150.0;
	
	public ManualTrackAcquirer() {
		requires(acquirer);
	}
	
	@Override
	protected void initialize() {
		acquirer.raiseArmToAngleInit();
	}

	@Override
	protected void execute() {
		double angle = (1 - DS2016.leftJoystick.getZ())/2 * MAX_ARM_ANGLE;
		acquirer.raiseArmToAngle(angle);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		acquirer.raiseArmToAngleEnd();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
