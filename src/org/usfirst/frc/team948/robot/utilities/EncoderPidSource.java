package org.usfirst.frc.team948.robot.utilities;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class EncoderPidSource implements PIDSource{
	private double leftStartDistance;
	private double rightStartDistance;
	private volatile double pidGet;

	public void reset() {
		leftStartDistance = RobotMap.leftMotorEncoder.getDistance();
		rightStartDistance = RobotMap.rightMotorEncoder.getDistance();
	}


	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		pidGet = Math.max(Math.abs(RobotMap.leftMotorEncoder.getDistance() - leftStartDistance),
				Math.abs(RobotMap.rightMotorEncoder.getDistance() - rightStartDistance));
		return pidGet;
	}

	@Override
	public void setPIDSourceType(PIDSourceType arg0) {
		// TODO Auto-generated method stub
	
	}
}
