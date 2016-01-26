package org.usfirst.frc.team948.robot.utilities;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class DummyGyro implements Gyro{

	public DummyGyro() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void calibrate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void free() {
		// TODO Auto-generated method stub
		
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

}
