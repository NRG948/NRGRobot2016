package org.usfirst.frc.team948.robot.commands;

public class RaiseShooterArmTo extends CommandBase {
	private double angle;
	
	public RaiseShooterArmTo(double angle) {
		requires(shooterArm);
		this.angle = angle;
	}
	
	@Override
	protected void initialize() {
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
