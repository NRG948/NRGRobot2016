package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.Robot.Level;
import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Acquirer extends Subsystem implements PIDOutput {

	public PIDController armAnglePID = new PIDController(0.1, 0.01, 0.005, RobotMap.armAngleEncoder, this);
	private double pidOutput;
	private final double ANGLE_TO_VOLTS = 0.01389;
	private final double TOLERANCE = 1.0 * ANGLE_TO_VOLTS;

	public Acquirer() {
	}

	@Override
	protected void initDefaultCommand() {
	}

	public void setDesiredArmAngle(double angle) {
		armAnglePID.reset();
		armAnglePID.setSetpoint(angle * ANGLE_TO_VOLTS);
		armAnglePID.setAbsoluteTolerance(TOLERANCE);
		pidOutput = 0;
		armAnglePID.enable();
	}

	public void moveArmToDesiredAngle() {
		RobotMap.acquireArmVictor.set(pidOutput);
	}

	public boolean isArmAtDesiredAngle() {
		return armAnglePID.onTarget();
	}

	public void stopArm() {
		armAnglePID.reset();
		RobotMap.acquireArmVictor.set(0);
	}

	public void stopAcquirer() {
		RobotMap.acquireArmVictor.set(0);
		RobotMap.acquireWheelVictor.set(0);
	}

	public void rawAcquire(double speed) {
		RobotMap.acquireWheelVictor.set(speed);
	}

	@Override
	public void pidWrite(double arg0) {
		pidOutput = arg0;
	}

	public void rawRaise(double power) {
		RobotMap.acquireArmVictor.set(power);
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

}
