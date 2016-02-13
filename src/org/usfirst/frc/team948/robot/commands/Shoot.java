package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.Timer;

public class Shoot extends CommandBase {
	
	public Timer timer1 = new Timer();
	
	private static final double PUSH_POWER = 0.8;
	
	private static final double BALL_PUSH_TIME = 1.0;

	public Shoot() {
		requires(shooterwheel);
		requires(shooterbar);
	}

	@Override
	protected void initialize() {
		timer1.reset();
		timer1.start();
	}

 	@Override
	protected void execute() {
		{
			shooterbar.rawBallPush(PUSH_POWER);
		}
	}

	@Override
	protected boolean isFinished() {
		if (timer1.get() > BALL_PUSH_TIME) {
			return true;
		}
		return false;
	}

	@Override
	protected void end() {
		timer1.stop();
		shooterwheel.rawShoot(0);
		shooterbar.rawBallPush(0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
