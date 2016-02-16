package org.usfirst.frc.team948.robot.utilities;

import org.usfirst.frc.team948.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NavXTester {

	private static AHRS ahrs = RobotMap.ahrs;
	private static AHRSGyro ahrsgyro = new AHRSGyro();
	
	public static void parameterDisplay() {

		// Accelerations
		SmartDashboard.putNumber("RawAccelX", ahrs.getRawAccelX());
		SmartDashboard.putNumber("RawAccelY", ahrs.getRawAccelY());
		SmartDashboard.putNumber("RawAccelZ", ahrs.getRawAccelZ());

		// Angular velocities
		SmartDashboard.putNumber("GyroX (deg per s)", ahrs.getRawGyroX());
		SmartDashboard.putNumber("GyroY (deg per s)", ahrs.getRawGyroY());
		SmartDashboard.putNumber("GyroZ (deg per s)", ahrs.getRawGyroZ());

		// Angles
		SmartDashboard.putNumber("Pitch", ahrs.getPitch());
		SmartDashboard.putNumber("Yaw", ahrsgyro.getAngle());
		SmartDashboard.putNumber("Roll", ahrs.getRoll());

		// Velocities
		SmartDashboard.putNumber("vx (m per s)", ahrs.getVelocityX());
		SmartDashboard.putNumber("vy (m per s)", ahrs.getVelocityY());
		SmartDashboard.putNumber("vz (m per s)", ahrs.getVelocityZ());

		// Positions
		SmartDashboard.putNumber("dx", ahrs.getDisplacementX());
		SmartDashboard.putNumber("dy", ahrs.getDisplacementY());
		SmartDashboard.putNumber("dz", ahrs.getDisplacementZ());

		// Inertial frame accelerations
		SmartDashboard.putNumber("worldax", ahrs.getWorldLinearAccelX());
		SmartDashboard.putNumber("worlday", ahrs.getWorldLinearAccelY());
		SmartDashboard.putNumber("worldaz", ahrs.getWorldLinearAccelZ());

		// Computed world acceleration, velocity, and position
		for (int i = 0; i < 3; i++) {
			SmartDashboard.putNumber("computeda" + (char) ('x' + i), PositionTracker3D.aworld[i]);
			SmartDashboard.putNumber("computedv" + (char) ('x' + i), PositionTracker3D.vworld[i]);
			SmartDashboard.putNumber("computedr" + (char) ('x' + i), PositionTracker3D.rworld[i]);
			SmartDashboard.putNumber("acal" + (char) ('x' + i), PositionTracker3D.acal[i]);
			SmartDashboard.putNumber("ocal" + (char) ('x' + i), PositionTracker3D.ocal[i]);
		}

		SmartDashboard.putString("finalize calibration", PositionTracker3D.finalizeCalibration + "");
		SmartDashboard.putNumber("valueToCheck", PositionTracker3D.valueToCheck);
	}
}