package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;

public class SpitOut extends CommandBase {
	Timer timer = new Timer();
	private final double TIME = 1.5;
	private final double POWER= -0.6;
	private double startTime;

	public SpitOut() {
		requires(shooterWheel);
		requires(acquirerWheel);
		requires(shooterBar);
	}

	@Override
	protected void initialize() {
		timer.reset();
		timer.start();
		startTime = 0;
	}

	@Override
	protected void execute() {
		RobotMap.rightShooterWheel.set(POWER);
		RobotMap.leftShooterWheel.set(-POWER);
		acquirerWheel.rawAcquireWheels(-POWER);
		if (timer.get() >= TIME) {
			shooterBar.rawBallPush(-POWER);
		}
		if (!shooterWheel.isBallLoaded() && startTime == 0) {
			startTime = timer.get();
		}
			
	}

	@Override
	protected void end() {
		RobotMap.rightShooterWheel.set(0);
		RobotMap.leftShooterWheel.set(0);
		shooterBar.rawBallPush(0);
		acquirerWheel.rawAcquireWheels(0);
		timer.stop();
	}

	@Override
	protected boolean isFinished() {
//		return (startTime != 0 && timer.get() > startTime + 0.5);
		return timer.get() > TIME + 0.5;
	}

	@Override
	protected void interrupted() {
		end();
	}

}
