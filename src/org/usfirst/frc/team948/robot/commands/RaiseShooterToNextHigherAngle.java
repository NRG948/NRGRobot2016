package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm.ShooterAngle;

public class RaiseShooterToNextHigherAngle extends CommandBase{

	private ShooterAngle desiredAngle;
	
	public RaiseShooterToNextHigherAngle() {
		requires(shooterArm);
	}
	
	@Override
	protected void initialize() {
		double voltage = RobotMap.shooterLifterEncoder.getVoltage();
		double angle = ShooterArm.degreesFromVolts(voltage);
		ShooterAngle currentAngle = shooterArm.findNearestAngle(angle);	
		desiredAngle = shooterArm.nextHigherAngle(currentAngle);
		shooterArm.moveArmInit();
		shooterArm.setDesiredArmAngle(desiredAngle.getAngleInDegrees());
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
