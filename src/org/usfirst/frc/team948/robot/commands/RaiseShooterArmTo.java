package org.usfirst.frc.team948.robot.commands;

public class RaiseShooterArmTo extends CommandBase {
	private double angle;
	
	public RaiseShooterArmTo(double angle) {
		requires(shooterarm);
		this.angle = angle;
	}
	
	@Override
	protected void initialize() {
		shooterarm.setDesiredArmAngle(angle);
	}

	@Override
	protected void execute() {
		shooterarm.moveArmToDesiredAngle();
	}

	@Override
	protected boolean isFinished() {
//		return shooterarm.isArmAtDesiredAngle();
		return false;
	}

	@Override
	protected void end() {
		shooterarm.stopArm();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
