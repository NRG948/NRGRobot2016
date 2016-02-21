package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm.ShooterAngle;

public class RaiseShooterToNextLowerAngle extends CommandBase{

	private ShooterAngle desiredAngle;
	
	public RaiseShooterToNextLowerAngle() {
		requires(shooterArm);
	}
	
	@Override
	protected void initialize() {
		double voltage = RobotMap.shooterLifterEncoder.getVoltage();
		ShooterAngle currentAngle = shooterArm.findNearestAngle(voltage);	
		desiredAngle = shooterArm.nextLowerAngle(currentAngle);
		shooterArm.moveArmInit();
		shooterArm.setDesiredArmAngle(desiredAngle.getValue());
	}
	
	@Override
	protected void execute() {
		shooterArm.moveArmToDesiredAngle();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		shooterArm.stopArm();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
