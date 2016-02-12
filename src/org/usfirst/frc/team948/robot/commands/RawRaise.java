package org.usfirst.frc.team948.robot.commands;

public class RawRaise extends CommandBase{

	private double raisePower = 0.3;
	
	public RawRaise(double power){
		raisePower = power;
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		shooterarm.rawRaiseShooter(raisePower);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		shooterarm.stopArm();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		shooterarm.stopArm();
	}

}
