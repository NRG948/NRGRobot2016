package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterWheel extends Subsystem {
	public volatile double currentLeftRPM;
	public volatile double currentRightRPM;
	public static final int MAX_RPM_SAMPLES = 100;
	private double[] leftRPMValues = new double[MAX_RPM_SAMPLES];
	private double[] rightRPMValues = new double[MAX_RPM_SAMPLES];
	private int index;
	private int currentCount;
	
	private long prevTime = 0;
	private long prevTimeRight = 0;
	private long currentTime = 0;
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

	public void addRPMValueToArrays() {
		leftRPMValues[index] = currentLeftRPM;
		rightRPMValues[index] = currentRightRPM;
		index++;
		index %= leftRPMValues.length;
		currentCount = Math.min(currentCount + 1, MAX_RPM_SAMPLES);
	}

	public double getAverageLeftRPM(int numberOfValues) {
		if (numberOfValues <= 0) {
			numberOfValues = 1;
		}
		numberOfValues = Math.min(numberOfValues, currentCount);
		double sum = 0;
		for (int i = 1; i <= numberOfValues; i++) {

			if (index - i >= 0) {
				sum += leftRPMValues[index - i];
			} else {
				sum += leftRPMValues[leftRPMValues.length + index - i];
			}
		}
		return sum / numberOfValues;
	}
	
	public double getAverageRightRPM(int numberOfValues) {
		if (numberOfValues <= 0) {
			numberOfValues = 1;
		}
		numberOfValues = Math.min(numberOfValues, currentCount);
		double sum = 0;
		for (int i = 1; i <= numberOfValues; i++) {

			if (index - i >= 0) {
				sum += rightRPMValues[index - i];
			} else {
				sum += rightRPMValues[rightRPMValues.length + index - i];
			}
		}
		return sum / numberOfValues;
	}
	
	public void updateRPM() {
		currentEncoderLeft = RobotMap.leftShooterWheelEncoder.getDistance();
		currentEncoderRight = RobotMap.rightShooterWheelEncoder.getDistance();
		currentTime = System.nanoTime();
		currentLeftRPM = ((currentEncoderLeft - prevEncoderLeft) / (currentTime - prevTime)) * 60000000000.0;
		currentRightRPM = ((currentEncoderRight - prevEncoderRight) / (currentTime - prevTime)) * 60000000000.0;
		prevEncoderLeft = currentEncoderLeft;
		prevEncoderRight = currentEncoderRight;
		prevTime = currentTime;
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
