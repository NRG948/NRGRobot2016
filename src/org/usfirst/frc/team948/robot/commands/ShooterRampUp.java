package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;

public class ShooterRampUp extends CommandBase{
	
	public ShooterRampUp(){
		requires(shooter);
	}
	protected void initialize() {
	
	}
	protected void execute() {
		RobotMap.leftShooterWheel.set(1.0);	
		RobotMap.rightShooterWheel.set(1.0);
	}
	protected boolean isFinished() {
		return false;
	}
	protected void end() {
		RobotMap.leftShooterWheel.set(0);
		RobotMap.rightShooterWheel.set(0);
	}
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}
}
