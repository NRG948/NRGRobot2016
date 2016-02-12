package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.Timer;

public class Delay extends CommandBase{

	Timer timer;
	double timeInSeconds;

	private void startTimer() {
		timer = new Timer();
		timer.reset();
		timer.start();
	}
	
	public Delay(double time) {
		timeInSeconds = time;
	}
	
	@Override
	protected void initialize() {
		startTimer();
		System.out.println("Started the delay");	
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return (timer.get() >= timeInSeconds);
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		end();
	}

}
