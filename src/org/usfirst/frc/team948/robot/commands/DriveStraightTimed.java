package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.Timer;

public class DriveStraightTimed extends CommandBase {

	public double time;
	public double power;
	public double currentTime;

	public Timer timer = new Timer();
	
	
	public DriveStraightTimed(double time, double power) {
		requires(drive);
		this.power = power;
		this.time = time;

	}

	@Override
	protected void end() {
		drive.rawStop();
		timer.stop();
		timer.reset();
	}

	@Override
	protected void execute() {
		double timeRemaining = time - timer.get();
		double p = power;
		if (timeRemaining <= 1) {
			p = p * timeRemaining;
		}
		drive.rawTankDrive(p, p);
//		if the time remaining is less than or equal to 1, the power will be set to its original value 
//		multiplied by the time remaining
//		the robot then drives with that power
	}

	@Override
	protected void initialize() {
		timer.reset();
		timer.start();
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return (timer.get() >= time);
	}
}
