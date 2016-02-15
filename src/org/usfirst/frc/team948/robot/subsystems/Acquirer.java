package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.Robot.Level;
import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.ManualAcquire;
import org.usfirst.frc.team948.robot.commands.ManualRaiseAcquirer;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Acquirer extends Subsystem implements PIDOutput {

	private PIDController acquirerAnglePID = new PIDController(0.1, 0.01, 0.005, RobotMap.armAngleEncoder, this);
	private double pidOutput;
	private final double TOLERANCE = 1.0 * SLOPE_VOLTS_FROM_DEGREES;
	private static final double VOLTS_0 = 3.0;
	private static final double VOLTS_180 = 0.65;
	private static final double SLOPE_VOLTS_FROM_DEGREES = (VOLTS_180 - VOLTS_0) / 180;
	
	public Acquirer() {
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ManualRaiseAcquirer());
	}
	
	private double voltsFromDegrees(double degrees)
	{
		double volts = degrees * SLOPE_VOLTS_FROM_DEGREES + VOLTS_0;
		return volts;
	}
	
	private double degreesFromVolts(double volts)
	{
		return (volts - VOLTS_0) / SLOPE_VOLTS_FROM_DEGREES;
	}

	public void setDesiredArmAngle(double degrees) {
		acquirerAnglePID.reset();
		acquirerAnglePID.setSetpoint(voltsFromDegrees(degrees));
		acquirerAnglePID.setAbsoluteTolerance(TOLERANCE);
		acquirerAnglePID.setOutputRange(-.5, .7);
		pidOutput = 0;
		acquirerAnglePID.enable();
	}

	public void moveArmToDesiredAngle() {
		rawRaiseArm(pidOutput);
	}

	public boolean isArmAtDesiredAngle() {
		return acquirerAnglePID.onTarget();
	}

	public void stopArm() {
		acquirerAnglePID.reset();
		rawRaiseArm(0);
		pidOutput = 0;
	}

	public void stopAcquirer() {
		rawRaiseArm(0);
		rawAcquireWheels(0);
	}

	public void rawAcquireWheels(double speed) {
		RobotMap.acquireWheelVictor.set(speed);
	}

	@Override
	public void pidWrite(double arg0) {
		pidOutput = arg0;
	}

	public void rawRaiseArm(double power) {		
		if((hasReachedUpperLimit() && power > 0) || (hasReachedLowerLimit() && power < 0) ) {
			RobotMap.acquireArmVictor.set(0);
		} else {
			RobotMap.acquireArmVictor.set(power);
		}
	}

	public Level nextHigherLevel(Level currentLevel) {
		Level[] levels = Level.values();
		for (int i = 0; i < levels.length; i++) {
			if (currentLevel.equals(levels[i])) {
				return levels[Math.min(levels.length - 1, i + 1)];
			}
		}
		return null;
	}

	public Level nextLowerLevel(Level currentLevel) {
		Level[] levels = Level.values();
		for (int i = 0; i < levels.length; i++) {
			if (currentLevel.equals(levels[i])) {
				return levels[Math.max(0, i - 1)];
			}
		}
		return null;
	}

	/**
	 * Returns the level nearest to the given angle.
	 */
	public Level findNearestLevel(double angle) {
		Level[] levels = Level.values();
		int nearest = 0;
		double diff = Math.abs(angle - levels[nearest].getValue());
		for (int i = 1; i < levels.length; i++) {
			double d = Math.abs(angle - levels[i].getValue());
			if (d < diff) {
				diff = d;
				nearest = i;
			}
		}
		return levels[nearest];
	}
	
	public boolean hasReachedUpperLimit() {
		return (!RobotMap.acquireUpperLimit.get());
	}
	

	public boolean hasReachedLowerLimit() {
		return (!RobotMap.acquireLowerLimit.get());
	}
}
