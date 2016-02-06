package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;

public class ShooterRampUp extends CommandBase {
	private double power;
	private final double DEFAULT_POWER = 1;

	public ShooterRampUp() {
		requires(shooter);
		this.power = DEFAULT_POWER;
	}

	public ShooterRampUp(double power) {
		requires(shooter);
		this.power = power;
	}

	protected void initialize() {

	}

	protected void execute() {
		RobotMap.leftShooterWheel.set(-power);
		RobotMap.rightShooterWheel.set(power);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		RobotMap.leftShooterWheel.set(0);
		RobotMap.rightShooterWheel.set(0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}
}
