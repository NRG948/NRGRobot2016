package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;

public class RaiseShooterArmTo extends CommandBase {
	private double angle;
	private boolean angleFromVisionProcessing;
	private double tolerance = ShooterArm.TOLERANCE;
	public RaiseShooterArmTo(double angle) {
		requires(shooterArm);
		this.angle = angle;
		angleFromVisionProcessing = false;
	}
	
	public RaiseShooterArmTo(double angle, double tolerance){
		this(angle);
		this.tolerance = tolerance;
	}
	
	public RaiseShooterArmTo() {
		requires(shooterArm);
		angleFromVisionProcessing = true;
	}
	
	@Override
	protected void initialize() {
		if (angleFromVisionProcessing) angle = Robot.visionProcessing.getShootingAngle();
		shooterArm.moveArmInit();
		shooterArm.setDesiredArmAngle(angle);
		shooterArm.setTolerance(tolerance);
	}

	@Override
	protected void execute() {
		//if(angleFromVisionProcessing){
		//	shooterArm.moveArmToDesiredAngleVisionTracking();
		//}else{
			shooterArm.moveArmToDesiredAngle();
		//}
	}
	int counter = 0;
	@Override
	protected boolean isFinished() {
		return shooterArm.isArmAtDesiredAngle();
		/*if(shooterArm.isArmAtDesiredAngle()){
			counter ++;
		}else{
			counter = 0;
		}
		return counter > 4;*/
		//return false;
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
