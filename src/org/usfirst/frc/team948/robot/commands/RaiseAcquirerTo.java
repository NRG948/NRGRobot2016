package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.Robot;

public class RaiseAcquirerTo extends CommandBase{

	public double angle;
	
	public RaiseAcquirerTo(double angle){
		requires(acquirerArm);
		this.angle = angle;
	}
	
	public RaiseAcquirerTo(Robot.Level level){
		requires(acquirerArm);
		this.angle = level.getValue();
	}
	
	protected void initialize (){
		acquirerArm.raiseArmToAngleInit();
		acquirerArm.setDesiredArmAngle(angle);
	}
	
	protected void execute(){
		acquirerArm.raiseArmToAngle();
	}
	
	protected boolean isFinished(){
//		return acquirerArm.isArmAtDesiredAngle();
		return false;
	}
	
	protected void end(){
		acquirerArm.stopArm();
	}
	
	protected void interrupted(){
		end();
	}
}
