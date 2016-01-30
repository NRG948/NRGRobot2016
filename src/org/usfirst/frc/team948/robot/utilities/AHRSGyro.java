package org.usfirst.frc.team948.robot.utilities;

import org.usfirst.frc.team948.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class AHRSGyro extends GyroBase implements Gyro, PIDSource, LiveWindowSendable {

    private static AHRS ahrs = RobotMap.ahrs;
	    
	public AHRSGyro() {
		
	}

	@Override
	public void calibrate() {
		
	}

	@Override
	public void free() {
		ahrs.free();
	}

	@Override
	public double getAngle() {
		return ahrs.getYaw();
	}

	@Override
	public double getRate() {
		return ahrs.getRate();
	}

	@Override
	public void reset() {
		ahrs.reset();
	}
}
