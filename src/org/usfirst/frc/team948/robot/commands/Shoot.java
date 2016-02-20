package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.Timer;

public class Shoot extends CommandBase {
	
	public Timer timer1 = new Timer();
	private double time;
	
	private static final double PUSH_POWER = 0.8;
	
	private static final double BALL_PUSH_TIME = 1.0;

	public Shoot(double time) {
		requires(shooterWheel);
		requires(shooterBar);
		this.time = time;                  
	}

	@Override
	protected void initialize() {
		timer1.reset();
		timer1.start();
	}

 	@Override
	protected void execute() {
		if (timer1.get() > time){
			shooterBar.rawBallPush(PUSH_POWER);
		}
	}

	@Override
	protected boolean isFinished() {
		return (timer1.get() > BALL_PUSH_TIME + time);
//		Finishes command if the current time is greater than the Ball Push Time
	}

	@Override
	protected void end() {
		timer1.stop();
		shooterWheel.rawShoot(0);
		shooterBar.rawBallPush(0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
