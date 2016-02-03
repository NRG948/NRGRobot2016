package org.usfirst.frc.team948.robot.utilities;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class DummyGyro implements Gyro, PIDSource{
	
	public DummyGyro() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void calibrate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void free() {
		// TODO Auto-generated method stu+
	}

	@Override
	public double getAngle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kRate;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return 0;
	}

}
