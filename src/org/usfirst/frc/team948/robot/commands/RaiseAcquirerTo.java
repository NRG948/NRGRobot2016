package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.Robot;

public class RaiseAcquirerTo extends CommandBase{

	public double angle;
	
	public RaiseAcquirerTo(double angle){
		requires(acquirer);
		this.angle = angle;
	}
	
	public RaiseAcquirerTo(Robot.Level level){
		requires(acquirer);
		this.angle = level.getValue();
	}
	
	protected void initialize (){
		acquirer.raiseArmToAngleInit();
		acquirer.setDesiredArmAngle(angle);
	}
	
	protected void execute(){
		acquirer.raiseArmToAngle();
	}
	
	protected boolean isFinished(){
		return acquirer.isArmAtDesiredAngle();
	}
	
	protected void end(){
		acquirer.stopArm();
	}
	
	protected void interrupted(){
		end();
	}
}
