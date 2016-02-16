package org.usfirst.frc.team948.robot.utilities;

import org.usfirst.frc.team948.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;

public class AHRSGyro extends GyroBase implements Gyro, PIDSource, LiveWindowSendable {


    private AHRS ahrs = RobotMap.ahrs;
	
	public AHRSGyro() {
		
	}

	@Override
	public void calibrate() {
		
	}

	@Override
	public void free() {
		ahrs.free();
	}

	//X is now the axis directed up because the location the roboRIO was mounted(vertically)
	@Override
	public double getAngle() {
		return ahrs.getYaw();

//			if(ahrs.getAngle() == -180){
//				ahrs.getAngle() += 360;
//			}
	}

	@Override
	public double getRate() {
		return ahrs.getRawGyroX();
	}

	@Override
	public void reset() {
		ahrs.reset();
	}
}
