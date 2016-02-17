package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.PIDOutput;

public class RampToRPM extends CommandBase implements PIDOutput{

	private double targetRPM;
	private double leftRPM;
	private double rightRPM;
	private double prevLeftDiff;
	private double prevRightDiff;
	private double leftWheelOutput;
	private double rightWheelOutput;
	private double p = 0.00001;
	private double leftH0 = 0;
	private double rightH0 = 0;
	public RampToRPM(double RPM){
		this.targetRPM = RPM;
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		p = preferences.getDouble(PreferenceKeys.TAKE_HALF_BACK_RATIO, 0.000001);
	}

	@Override
	protected void execute() {
		//difference in RPM to target
		leftRPM = shooterWheel.currentLeftRPM;
		rightRPM = shooterWheel.currentRightRPM;
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
		RobotMap.leftShooterWheel.set(leftWheelOutput);
		RobotMap.rightShooterWheel.set(rightWheelOutput);
		// TODO Auto-generated method stub
		prevLeftDiff = diffLeft;
		prevRightDiff = diffRight;
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		shooterWheel.rawShoot(0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}
	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		pidOutput = output;
	}

}
