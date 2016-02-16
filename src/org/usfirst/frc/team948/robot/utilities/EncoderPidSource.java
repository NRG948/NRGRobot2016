package org.usfirst.frc.team948.robot.utilities;

import org.usfirst.frc.team948.robot.RobotMap;

public class EncoderPidSource {
	private double leftStartDistance;
	private double rightStartDistance;
	private volatile double pidGet;

	public void reset() {
		leftStartDistance = RobotMap.leftMotorEncoder.getDistance();
		rightStartDistance = RobotMap.rightMotorEncoder.getDistance();
	}

	public double PIDGet() {
		pidGet = Math.max(Math.abs(RobotMap.leftMotorEncoder.getDistance() - leftStartDistance),
				Math.abs(RobotMap.rightMotorEncoder.getDistance() - rightStartDistance));
		return pidGet;
	}
}
