package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.DS2016;
import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.subsystems.VisionProcessing;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterRampUp extends CommandBase {
	private double power;
	public boolean autoPower = false;
	private final double DEFAULT_POWER = 1;

	public ShooterRampUp() {
	    requires(shooterWheel);
		autoPower = true;
		this.power = DEFAULT_POWER;
	}

	public ShooterRampUp(double power) {
		requires(shooterWheel);
		this.power = power;
	}
	
	public ShooterRampUp(boolean x)
	{
		power = preferences.getDouble("SHOOTER_RAMP_UP_TEST_POWER", DEFAULT_POWER);
	}

	protected void initialize() {

	}

	protected void execute() {
		if (autoPower) {
			power = 1;
		}
//		if (shooterWheel.isBallLoaded()) {
			power = (1 - DS2016.rightJoystick.getZ()) / 2.0;
		
			RobotMap.leftShooterWheel.set(power);
			RobotMap.rightShooterWheel.set(-power);
//		} else {
//			RobotMap.leftShooterWheel.set(0);
//			RobotMap.rightShooterWheel.set(0);
//		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	//	RobotMap.leftShooterWheel.set(0);
	//	RobotMap.rightShooterWheel.set(0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
