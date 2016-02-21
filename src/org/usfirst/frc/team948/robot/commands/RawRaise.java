package org.usfirst.frc.team948.robot.commands;

public class RawRaise extends CommandBase{

	private double raisePower = 0.3;
	
	public RawRaise(double power){
		raisePower = power;
	}
	@Override
	protected void initialize() {
			
	}

	@Override
	protected void execute() {
		shooterArm.rawRaiseShooter(raisePower);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		shooterArm.stopArm();
	}

	@Override
	protected void interrupted() {
		shooterArm.stopArm();
	}

}
