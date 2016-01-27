package org.usfirst.frc.team948.robot.utilities;

import org.usfirst.frc.team948.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NavXTester {
	
	private static AHRS ahrs = RobotMap.ahrs;
	
	public static void parameterDisplay() {
		
		//Accelerations
		SmartDashboard.putNumber("RawAccelX", ahrs.getRawAccelX());
		SmartDashboard.putNumber("RawAccelY", ahrs.getRawAccelY());
		SmartDashboard.putNumber("RawAccelZ", ahrs.getRawAccelZ());
		
		//Angular velocities
		SmartDashboard.putNumber("GyroX (deg/s)", ahrs.getRawGyroX());
		SmartDashboard.putNumber("GyroY (deg/s)", ahrs.getRawAccelY());
		SmartDashboard.putNumber("GyroZ (deg/s)", ahrs.getRawAccelZ());
		
		//Angles
		SmartDashboard.putNumber("Pitch", ahrs.getPitch());
		SmartDashboard.putNumber("Yaw", ahrs.getYaw());
		SmartDashboard.putNumber("Roll", ahrs.getRoll());		

		//Velocities
		SmartDashboard.putNumber("vx (m/s)", ahrs.getVelocityX());
		SmartDashboard.putNumber("vy (m/s)", ahrs.getVelocityY());
		SmartDashboard.putNumber("vz (m/s)", ahrs.getVelocityZ());
		
		//Positions
		SmartDashboard.putNumber("dx", ahrs.getDisplacementX());
		SmartDashboard.putNumber("dy", ahrs.getDisplacementY());
		SmartDashboard.putNumber("dz", ahrs.getDisplacementZ());

		//Inertial frame accelerations
		SmartDashboard.putNumber("worldax", ahrs.getWorldLinearAccelX());
		SmartDashboard.putNumber("worlday", ahrs.getWorldLinearAccelY());
		SmartDashboard.putNumber("worldaz", ahrs.getWorldLinearAccelZ());
	}	
}