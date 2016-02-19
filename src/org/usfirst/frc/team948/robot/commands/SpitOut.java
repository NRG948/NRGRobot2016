package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;

public class SpitOut extends CommandBase {
	Timer halfAsecond = new Timer();
	private final double TIME = 0.5;
	private final double POWER= -0.6;

	public SpitOut() {
		requires(shooterWheel);
		requires(acquirerWheel);
		requires(shooterBar);
	}

	@Override
	protected void initialize() {
		halfAsecond.reset();
		halfAsecond.start();
	}

	@Override
	protected void execute() {
		RobotMap.rightShooterWheel.set(POWER);
		RobotMap.leftShooterWheel.set(-POWER);
		acquirerWheel.rawAcquireWheels(-POWER);
		if (halfAsecond.get() >= TIME) {
			shooterBar.rawBallPush(-POWER);
		}
	}

	@Override
	protected void end() {
		RobotMap.rightShooterWheel.set(0);
		RobotMap.leftShooterWheel.set(0);
		shooterBar.rawBallPush(0);
		halfAsecond.stop();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return !shooterWheel.isBallLoaded();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
