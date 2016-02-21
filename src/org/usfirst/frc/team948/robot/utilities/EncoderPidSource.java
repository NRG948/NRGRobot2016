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
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		pidGet = Math.max(RobotMap.leftMotorEncoder.getDistance() - leftStartDistance,
				RobotMap.rightMotorEncoder.getDistance() - rightStartDistance);
		if(pidGet < 0){
			pidGet = Math.min(RobotMap.leftMotorEncoder.getDistance() - leftStartDistance,
					RobotMap.rightMotorEncoder.getDistance() - rightStartDistance);
		}
		return pidGet;
	}

	@Override
	public void setPIDSourceType(PIDSourceType arg0) {
		
	}
}
