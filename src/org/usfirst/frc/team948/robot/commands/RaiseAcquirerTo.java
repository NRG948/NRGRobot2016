package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.Robot;

public class RaiseAcquirerTo extends CommandBase{

	public double angle;
	private int counter = 0;

	public RaiseAcquirerTo(double angle){
		requires(acquirerArm);
		this.angle = angle;
	}
	
	public RaiseAcquirerTo(Robot.Level level){
		requires(acquirerArm);
		this.angle = level.getValue();
	}
	
	protected void initialize (){
		counter = 0;
		acquirerArm.raiseArmToAngleInit();
		acquirerArm.setDesiredArmAngle(angle);
	}
	
	protected void execute(){
		acquirerArm.raiseArmToAngle();
	}
	
	protected boolean isFinished(){
		if(acquirerArm.isArmAtDesiredAngle() && angle == 0){
//			counter++;
			return true;
		}else{
			counter = 0;
		}
		if(counter > 1 && angle == 0){
			return true;
		}
		return false;
	}
	
	protected void end(){
		acquirerArm.stopArm();
	}
	
	protected void interrupted(){
		end();
	}
}
