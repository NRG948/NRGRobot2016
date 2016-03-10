package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.utilities.PositionTracker;
import org.usfirst.frc.team948.robot.utilities.PositionTrackerWithEncoder;

public class InitializePosition extends CommandBase{

	private double initialX;
	private double initialY;
	private double initialHeading;
	
	public InitializePosition(double initialX, double initialY, double initialHeading) {
		this.initialHeading = initialHeading;
		this.initialX = initialX;
		this.initialY = initialY;
	}
	
	@Override
	protected void end() {
		
	}

	@Override
	protected void execute() {
		
	}

//	Set initial angle, x, and y positions 
	@Override
	protected void initialize() {
		PositionTracker.setPosition(initialX, initialY);
		RobotMap.driveGyro.setAngleOffset(initialHeading);
	}

	@Override
	protected void interrupted() {
		
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
	
}
