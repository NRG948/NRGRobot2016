package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterWheel extends Subsystem {
	public volatile double currentLeftRPM;
	public volatile double currentRightRPM;
	public static final int MAX_RPM_SAMPLES = 100;
	private double[] rpmValues = new double[MAX_RPM_SAMPLES];
	private int index;
	private int currentCount;
	
	private long prevTimeLeft = 0;
	private long prevTimeRight = 0;
	private long currentTimeLeft = 0;
	private long currentTimeRight = 0;
	
	private double prevEncoderLeft = 0;
	private double prevEncoderRight = 0;
	private double currentEncoderLeft = 0;
	private double currentEncoderRight = 0;

	private long prevTimeNano = 0;
	
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
		currentEncoderLeft = RobotMap.leftShooterWheelEncoder.getDistance();
		currentTimeLeft = System.currentTimeMillis();
		currentLeftRPM = ((currentEncoderLeft - prevEncoderLeft) / (currentTimeLeft - prevTimeLeft)) * 60000.0;
		prevEncoderLeft = currentEncoderLeft;
		prevTimeLeft = currentTimeLeft;
	}

	public void updateRightRPM() {
		currentEncoderRight = RobotMap.rightShooterWheelEncoder.getDistance();
		currentTimeRight = System.currentTimeMillis();
		currentRightRPM = ((currentEncoderRight - prevEncoderRight) / (currentTimeRight - prevTimeRight)) * 60000.0;
		prevEncoderRight = currentEncoderRight;
		prevTimeRight = currentTimeRight;
	}

	public long currentTimeNanos() {
		long difference = System.nanoTime() - prevTimeNano;
		prevTimeNano = System.nanoTime();
		return difference;
	}
	
	public boolean isBallLoaded() {
		return !RobotMap.ballButton.get();
	}

}
