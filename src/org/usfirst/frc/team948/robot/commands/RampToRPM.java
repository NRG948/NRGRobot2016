package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;
import org.usfirst.frc.team948.robot.utilities.MathHelper;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RampToRPM extends CommandBase {

	private double leftTargetRPM;
	private double rightTargetRPM;
	private double leftRPM;
	private double rightRPM;
	private double prevLeftDiff;
	private double prevRightDiff;
	private double leftWheelOutput;
	private double rightWheelOutput;
	private double p = 0.000015;
	private double leftH0 = 0;
	private double rightH0 = 0;
	private double delayTime;
	private boolean passedThresholdLeft = false;
	private boolean passedThresholdRight = false;
	private boolean prevBallStatus;
	private double startTime;
	private Timer timer;

	public RampToRPM(double RPM, double delayTime) {
		requires(shooterWheel);
		this.leftTargetRPM = RPM;
		this.rightTargetRPM = RPM;
		this.delayTime = delayTime;
		timer = new Timer();
	}

	public RampToRPM(double RPM) {
		this(RPM, 0);
	}

	public RampToRPM() {
		requires(shooterWheel);
		this.leftTargetRPM = preferences.getDouble(PreferenceKeys.SHOOTER_LEFT_TARGET_RPM, 2000);
		this.rightTargetRPM = preferences.getDouble(PreferenceKeys.SHOOTER_RIGHT_TARGET_RPM, 2000);
		delayTime = 0;
	}

	@Override
	protected void initialize() {
		prevBallStatus = shooterWheel.isBallLoaded();
		startTime = Integer.MAX_VALUE;
		passedThresholdLeft = false;
		passedThresholdRight = false;
		if (!visionProcessing.getMode()) {
			visionProcessing.switchCamera();
		}
		p = preferences.getDouble(PreferenceKeys.TAKE_HALF_BACK_RATIO, 0.000015);
		leftWheelOutput = 1;
		rightWheelOutput = 1;
		timer.reset();
		timer.start();
		// prevLeftDiff = 1;
		// prevRightDiff = 1;
		// leftH0 = 0.84 - 1.0;
		// rightH0 = 0.94 - 1.0;
	}

	@Override
	protected void execute() {
		if (timer.get() > delayTime) {
			shooterWheel.updateRPM();
			shooterWheel.addRPMValueToArrays();
			if (!passedThresholdLeft) {
				RobotMap.leftShooterWheel.set(leftWheelOutput);
				if (shooterWheel.currentLeftRPM - leftTargetRPM > 0) {
					passedThresholdLeft = true;
					leftWheelOutput = 0.42;
//					leftWheelOutput = 0.59;
				}
				SmartDashboard.putNumber("Left shooter output", leftWheelOutput);
			} else {
				leftRPM = Math.abs(shooterWheel.currentLeftRPM);
				double diffLeft = leftTargetRPM - leftRPM;
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
				if (shooterWheel.currentRightRPM - rightTargetRPM > 0) {
					passedThresholdRight = true;
					rightWheelOutput = 0.47;
//					rightWheelOutput = 0.66;
				}
				SmartDashboard.putNumber("Right shooter output", rightWheelOutput);
			} else {
				// difference in RPM to target
				rightRPM = Math.abs(shooterWheel.currentRightRPM);
				double diffRight = rightTargetRPM - rightRPM;
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
		boolean hasBall = shooterWheel.isBallLoaded();
		if (prevBallStatus && !hasBall && startTime == Integer.MAX_VALUE) {
			startTime = timer.get();
		}
		prevBallStatus = hasBall;
	}

	@Override
	protected boolean isFinished() {
		//end RampToRPM at end of ShootSequence when shooter arm setpoint = -10 degrees
		return timer.get() > startTime + 0.5;
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
