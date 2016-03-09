package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm.ShooterAngle;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LowerShooterToNextLowerAngle extends CommandBase{

	private ShooterAngle desiredAngle;
	private static int a = 0;
	public LowerShooterToNextLowerAngle() {
		requires(shooterArm);
	}
	
	@Override
	protected void initialize() {
		double voltage = RobotMap.shooterLifterEncoder.getVoltage();
		double angle = ShooterArm.degreesFromVolts(voltage);
		ShooterAngle currentAngle = shooterArm.findNearestAngle(angle);	
		desiredAngle = shooterArm.nextLowerAngle(currentAngle);
		a++;
		SmartDashboard.putString("Next Higher Level Seek", a+"lower"+desiredAngle.getAngleInDegrees());
		shooterArm.moveArmInit();
		shooterArm.setDesiredArmAngle(desiredAngle.getAngleInDegrees());
	}
	
	@Override
	protected void execute() {
		shooterArm.moveArmToDesiredAngle();
	}

	@Override
	protected boolean isFinished() {
		return shooterArm.isArmAtDesiredAngle();
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
