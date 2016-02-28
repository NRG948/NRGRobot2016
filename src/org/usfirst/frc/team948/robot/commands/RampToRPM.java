package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RampToRPM extends CommandBase{

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

	public RampToRPM(double RPM){
		requires(shooterWheel);
		this.targetRPM = RPM;
	}
	@Override
	protected void initialize() {
		if (!visionProcessing.getMode()) {
			visionProcessing.switchMode();
		}
		p = preferences.getDouble(PreferenceKeys.TAKE_HALF_BACK_RATIO, 0.000015);
		leftWheelOutput = 0.7;
		rightWheelOutput = 0.7;
	}

	@Override
	protected void execute() {
		//difference in RPM to target
		leftRPM = Math.abs(shooterWheel.currentLeftRPM);
		rightRPM = Math.abs(shooterWheel.currentRightRPM);
		double diffLeft = targetRPM - leftRPM;
		double diffRight = targetRPM - rightRPM;
		leftWheelOutput += diffLeft*p;
		rightWheelOutput += diffRight*p;
		if(diffLeft*prevLeftDiff < 0){
			if(leftH0 != 0){
				leftWheelOutput = (leftWheelOutput + leftH0)/2;
			}
			leftH0 = leftWheelOutput;
		}
		if(diffRight*prevRightDiff < 0){
			if(rightH0 != 0){
				rightWheelOutput = (rightWheelOutput + rightH0)/2;
			}
			rightH0 = rightWheelOutput;
		}
		SmartDashboard.putNumber("Left shooter output", leftWheelOutput);
		SmartDashboard.putNumber("Right shooter output", rightWheelOutput);
		RobotMap.leftShooterWheel.set(leftWheelOutput);
		RobotMap.rightShooterWheel.set(-rightWheelOutput);
		
		prevLeftDiff = diffLeft;
		prevRightDiff = diffRight;
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		//shooterWheel.rawShoot(0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
