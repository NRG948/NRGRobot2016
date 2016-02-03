package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.utilities.MathHelper;
import org.usfirst.frc.team948.robot.utilities.PositionTracker;

public class moveAcquirerAngle extends CommandBase{

	private double angle;
	
	public moveAcquirerAngle(double angle) {
		requires(acquirer);
		this.angle = angle;
	}
	public moveAcquirerAngle(Robot.ACQUIRER_ARM_LEVEL_HEIGHT input) {
		requires(acquirer);
		this.angle = input.getAngle();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		acquirer.setDesiredArmAngle();
		acquirer.moveArmToDesiredAngle();

	}


	protected boolean isFinished() {
		return acquirer.isArmAtDesiredAngle();
	}

	protected void end() {
		acquirer.stopArm();
		acquirer.setCurrentAngle(angle);
	}

	protected void interrupted() {
		//Insert a something to set the angle to the traversed angle
		end();
	}
}
