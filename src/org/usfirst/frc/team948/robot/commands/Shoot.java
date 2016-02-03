package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.Timer;

public class Shoot extends CommandBase {
	public double time;
	
	public double power;
	
	public Timer timer1 = new Timer();
	
	private static final double PUSH_POWER = 0.3;
	
	private static final double BALL_PUSH_TIME = 1.0;

	public Shoot(double power, double time) {
		requires(shooter);
		this.power = power;
		this.time = time;
	}

	@Override
	protected void initialize() {
		shooter.rawShoot(power);
		timer1.reset();
		timer1.start();
	}

	@Override
	protected void execute() {
		if (timer1.get() > time) {
			shooter.rawBallPush(PUSH_POWER);
		}
	}

	@Override
	protected boolean isFinished() {
		if (timer1.get() > time + BALL_PUSH_TIME) {
			return true;
		}
		return false;
	}

	@Override
	protected void end() {
		timer1.stop();
		shooter.rawShoot(0);
		shooter.rawBallPush(0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
