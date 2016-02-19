package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.Robot;

public class RaiseAcquirerTo extends CommandBase{

	public double angle;
	
	public RaiseAcquirerTo(double angle){
		requires(CommandBase.acquirer);
		this.angle = angle;
	}
	
	public RaiseAcquirerTo(Robot.Level level){
		requires(CommandBase.acquirer);
		this.angle = level.getValue();
	}
	
	protected void initialize (){
		CommandBase.acquirer.setDesiredArmAngle(angle);
	}
	
	protected void execute(){
		CommandBase.acquirer.moveArmToDesiredAngle();
	}
	
	protected boolean isFinished(){
//		return CommandBase.acquirer.isArmAtDesiredAngle();
		return false;
	}
	
	protected void end(){
		CommandBase.acquirer.stopArm();
	}
	
	protected void interrupted(){
		end();
	}
}
