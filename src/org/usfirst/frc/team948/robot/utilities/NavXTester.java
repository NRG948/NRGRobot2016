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
		SmartDashboard.putNumber("GyroX (deg per s)", ahrs.getRawGyroX());
		SmartDashboard.putNumber("GyroY (deg per s)", ahrs.getRawGyroY());
		SmartDashboard.putNumber("GyroZ (deg per s)", ahrs.getRawGyroZ());
		
		//Angles
		SmartDashboard.putNumber("Pitch", ahrs.getPitch());
		SmartDashboard.putNumber("Yaw", ahrs.getYaw());
		SmartDashboard.putNumber("Roll", ahrs.getRoll());		

		//Velocities
		SmartDashboard.putNumber("vx (m per s)", ahrs.getVelocityX());
		SmartDashboard.putNumber("vy (m per s)", ahrs.getVelocityY());
		SmartDashboard.putNumber("vz (m per s)", ahrs.getVelocityZ());
		
		//Positions
		SmartDashboard.putNumber("dx", ahrs.getDisplacementX());
		SmartDashboard.putNumber("dy", ahrs.getDisplacementY());
		SmartDashboard.putNumber("dz", ahrs.getDisplacementZ());

		//Inertial frame accelerations
		SmartDashboard.putNumber("worldax", ahrs.getWorldLinearAccelX());
		SmartDashboard.putNumber("worlday", ahrs.getWorldLinearAccelY());
		SmartDashboard.putNumber("worldaz", ahrs.getWorldLinearAccelZ());
		
		
		//SmartDashboard.putString("Yaw axis", ahrs.getBoardYawAxis().toString());
	}	
}