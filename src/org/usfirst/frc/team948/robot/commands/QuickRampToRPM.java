package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;

public class QuickRampToRPM extends CommandBase{

	private double leftTargetRPM;
	private double rightTargetRPM;
	private boolean passedThresholdLeft;
	private boolean passedThresholdRight;
	
	public QuickRampToRPM(double targetRPM) {
		requires(shooterWheel);
		this.leftTargetRPM = targetRPM;
		this.rightTargetRPM = targetRPM;
		passedThresholdLeft = false;
		passedThresholdRight = false;
	}
	
	@Override
	protected void initialize() {
		if (!visionProcessing.getMode()) {
			visionProcessing.switchCamera();
		}
	}

	@Override
	protected void execute() {
		shooterWheel.updateRPM();
		shooterWheel.addRPMValueToArrays();
		if(!passedThresholdLeft) {
			RobotMap.leftShooterWheel.set(1.0);
			if (shooterWheel.currentLeftRPM - leftTargetRPM > 0) {
				passedThresholdLeft = true;
				RobotMap.leftShooterWheel.set(0.42);
			}
		} else {
			RobotMap.leftShooterWheel.set(0.42);
		}
		if(!passedThresholdRight) {
			RobotMap.rightShooterWheel.set(-1.0);
			if (shooterWheel.currentRightRPM - rightTargetRPM > 0) {
				passedThresholdRight = true;
				RobotMap.rightShooterWheel.set(-0.46);
			}
		} else {
			RobotMap.rightShooterWheel.set(-0.46);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		shooterWheel.rawShoot(0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
