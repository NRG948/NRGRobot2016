package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;

public class ResetSensors extends CommandBase {
	
	public ResetSensors(){
		
	}
	protected void initialize(){
	
	}
	protected void execute(){
//		RobotMap.ahrs.resetDisplacement();
		RobotMap.driveGyro.reset();
		RobotMap.leftMotorEncoder.reset();
		RobotMap.leftShooterWheelEncoder.reset();
		RobotMap.rightMotorEncoder.reset();
		RobotMap.rightShooterWheelEncoder.reset();
	    
	}
	protected boolean isFinished(){
		return Math.abs(RobotMap.driveGyro.getAngle()) < 0.5;
//		return true;
	}
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}

}
