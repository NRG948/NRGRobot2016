package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm.ShooterAngle;

public class MoveShooterToLowerLevel extends CommandBase{

	private ShooterAngle desiredAngle;
	
	public MoveShooterToLowerLevel() {
		// TODO Auto-generated constructor stub
	requires(shooterArm);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		double voltage = RobotMap.shooterLifterEncoder.getVoltage();
		ShooterAngle currentAngle = shooterArm.findNearestAngle(voltage);	
		desiredAngle = shooterArm.nextLowerAngle(currentAngle);
		shooterArm.setDesiredArmAngle(desiredAngle.getValue());
	}
	
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		shooterArm.moveArmToDesiredAngle();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		shooterArm.stopArm();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}
}
