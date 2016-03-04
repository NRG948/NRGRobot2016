package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.utilities.MathHelper;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RampToRPM extends CommandBase {

	private double targetRPM;
	private double leftRPM;
	private double rightRPM;
	private double prevLeftDiff;
	private double prevRightDiff;
	private double leftWheelOutput;
	private double rightWheelOutput;
	private double p = 0.000015;
	private double leftH0 = 0;
	private double rightH0 = 0;
	private boolean passedThresholdLeft = false;
	private boolean passedThresholdRight = false;

	public RampToRPM(double RPM) {
		requires(shooterWheel);
		this.targetRPM = RPM;
	}

	@Override
	protected void initialize() {
		passedThresholdLeft = false;
		passedThresholdRight = false;
		if (!visionProcessing.getMode()) {
			visionProcessing.switchMode();
		}
		p = preferences.getDouble(PreferenceKeys.TAKE_HALF_BACK_RATIO, 0.000015);
		leftWheelOutput = 1;
		rightWheelOutput = 1;
	}

	@Override
	protected void execute() {
		shooterWheel.updateRPM();
		shooterWheel.addRPMValueToArrays();
		if (!passedThresholdLeft) {
			RobotMap.leftShooterWheel.set(leftWheelOutput);
			if (shooterWheel.currentLeftRPM - targetRPM > 0) {
				passedThresholdLeft = true;
				leftWheelOutput = 0.42;
			}
			SmartDashboard.putNumber("Left shooter output", leftWheelOutput);
		} else {
			leftRPM = Math.abs(shooterWheel.currentLeftRPM);
			double diffLeft = targetRPM - leftRPM;
			leftWheelOutput += diffLeft * p;
			leftWheelOutput = MathHelper.clamp(leftWheelOutput, -1.0, 1.0);
			if (diffLeft * prevLeftDiff < 0) {
				if (leftH0 != 0) {
					leftWheelOutput = (leftWheelOutput + leftH0) / 2;
				}
				leftH0 = leftWheelOutput;
			}
			RobotMap.leftShooterWheel.set(leftWheelOutput);
			SmartDashboard.putNumber("Left shooter output", leftWheelOutput);
			prevLeftDiff = diffLeft;
		}
		if (!passedThresholdRight) {
			RobotMap.rightShooterWheel.set(-rightWheelOutput);
			if (shooterWheel.currentRightRPM - targetRPM > 0) {
				passedThresholdRight = true;
				rightWheelOutput = 0.47;
			}
			SmartDashboard.putNumber("Right shooter output", rightWheelOutput);
		} else {
			// difference in RPM to target
			rightRPM = Math.abs(shooterWheel.currentRightRPM);
			double diffRight = targetRPM - rightRPM;
			rightWheelOutput += diffRight * p;
			rightWheelOutput = MathHelper.clamp(rightWheelOutput, -1.0, 1.0);
			if (diffRight * prevRightDiff < 0) {
				if (rightH0 != 0) {
					rightWheelOutput = (rightWheelOutput + rightH0) / 2;
				}
				rightH0 = rightWheelOutput;
			}
			RobotMap.rightShooterWheel.set(-rightWheelOutput);
			SmartDashboard.putNumber("Right shooter output", rightWheelOutput);
			prevRightDiff = diffRight;
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
