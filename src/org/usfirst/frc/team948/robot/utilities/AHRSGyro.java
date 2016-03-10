package org.usfirst.frc.team948.robot.utilities;

import org.usfirst.frc.team948.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;

public class AHRSGyro extends GyroBase implements Gyro, PIDSource, LiveWindowSendable {


    private AHRS ahrs = RobotMap.ahrs;
	private double cycleCount;
	private double previousAngle;
    private double currentAngle;
    private double angleOffset = 0;
    
	public AHRSGyro() {
		
	}

	@Override
	public void calibrate() {
		
	}

	public void setAngleOffset(double angleOffset){
		this.angleOffset = angleOffset;
	}
	
	@Override
	public void free() {
		ahrs.free();
	}

	//X is now the axis directed up because the location the roboRIO was mounted(vertically)
	@Override
	public double getAngle() {
		currentAngle = ahrs.getYaw();
		
		//postive -> negative is +cycle, negative -> positive is -cycle
		if (previousAngle - currentAngle > 200) {
			cycleCount++;
		}
		else if (previousAngle - currentAngle < -200) {
			cycleCount--;
		}
		
		previousAngle = ahrs.getYaw();
		//Add 360 times number of cycles to make continuous
		return currentAngle + cycleCount * 360 + angleOffset;
	}

	@Override
	public double getRate() {
		return ahrs.getRawGyroX();
	}

	@Override
	public void reset() {
		ahrs.reset();
		cycleCount = 0;
		previousAngle = currentAngle;
	}
	
	@Override
	public double pidGet() {
		return getAngle();
	}
}
