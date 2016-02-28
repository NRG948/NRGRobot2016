package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterWheel extends Subsystem {
	public double currentLeftRPM;
	public double currentRightRPM;
	public static final int MAX_RPM_SAMPLES = 100;
	private double[] rpmValues = new double[MAX_RPM_SAMPLES];
	private int index;
	private int currentCount;

	public ShooterWheel() {

	}

	@Override
	protected void initDefaultCommand() {
		// setDefaultCommand(new ShooterRampUp());

	}

	public void rawShoot(double power) {
		RobotMap.leftShooterWheel.set(-power);
		RobotMap.rightShooterWheel.set(power);
	}

	public void addRPMValueToArray() {
		double value = (currentLeftRPM + currentRightRPM) / 2;
		rpmValues[index] = value;
		index++;
		index %= rpmValues.length;
		currentCount = Math.min(currentCount + 1, MAX_RPM_SAMPLES);
	}

	public double getAverageRPM(int numberOfValues) {
		if (numberOfValues <= 0) {
			numberOfValues = 1;
		}
		numberOfValues = Math.min(numberOfValues, currentCount);
		double sum = 0;
		for (int i = 1; i <= numberOfValues; i++) {

			if (index - i >= 0) {
				sum += rpmValues[index - i];
			} else {
				sum += rpmValues[rpmValues.length + index - i];
			}
		}
		return sum / numberOfValues;
	}

	public void updateLeftRPM() {
		currentLeftRPM = 60 * RobotMap.leftShooterWheelEncoder.getRate();
	}

	public void updateRightRPM() {
		currentRightRPM = 60 * RobotMap.rightShooterWheelEncoder.getRate();
	}

	public boolean isBallLoaded() {
		return !RobotMap.ballButton.get();
	}

}
