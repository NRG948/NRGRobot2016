package org.usfirst.frc.team948.robot.utilities;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class PositionTrackerWithEncoder {
	private final Encoder leftQuad = RobotMap.rightMotorEncoder;
	private final Encoder rightQuad = RobotMap.leftMotorEncoder;
	
	private double x;
	private double y;
	private boolean isInitialized = false;
	private double lastLeftQuadDistance;
	private double lastRightQuadDistance;
	private long lastTime;
	private double velocity;

	public PositionTrackerWithEncoder() {
	}

	public void update() {
		if (!isInitialized) {
			initialize();
		}
		double leftChange = leftQuad.getDistance() - lastLeftQuadDistance;
		double rightChange = rightQuad.getDistance() - lastRightQuadDistance;
		double averageChange = (leftChange + rightChange) / 2.0;
		double angle = Math.toRadians(MathHelper.headingToDegrees(RobotMap.driveGyro.getAngle()));
		long time = System.nanoTime();
		x += averageChange * Math.cos(angle);
		y += averageChange * Math.sin(angle);
		lastLeftQuadDistance += leftChange;
		lastRightQuadDistance += rightChange;
		velocity = averageChange * 1000000000.0 / (time - lastTime);
		lastTime = time;
	}

	public void initialize() {
		x = 0;
		y = 0;
		lastLeftQuadDistance = lastRightQuadDistance = 0;
		lastTime = System.nanoTime();
		isInitialized = true;
	}

	public double getVelocity() {
		return velocity;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
