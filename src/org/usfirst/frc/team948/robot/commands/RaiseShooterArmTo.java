package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.Robot;

public class RaiseShooterArmTo extends CommandBase {
	private double angle;
	private boolean angleFromVisionProcessing;
	
	public RaiseShooterArmTo(double angle) {
		requires(shooterArm);
		this.angle = angle;
		angleFromVisionProcessing = false;
	}
	
	public RaiseShooterArmTo() {
		requires(shooterArm);
		angleFromVisionProcessing = true;
	}
	
	@Override
	protected void initialize() {
		if (angleFromVisionProcessing) angle = Robot.visionProcessing.getShootingAngle();
		shooterArm.setDesiredArmAngle(angle);
	}

	@Override
	protected void execute() {
		shooterArm.moveArmToDesiredAngle();
	}

	@Override
	protected boolean isFinished() {
//		return shooterarm.isArmAtDesiredAngle();
		return false;
	}

	@Override
	protected void end() {
		shooterArm.stopArm();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
