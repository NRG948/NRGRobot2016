package org.usfirst.frc.team948.robot.utilities;

import org.usfirst.frc.team948.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

public class PositionTracker3D {

	private static AHRS ahrs = RobotMap.ahrs;
	
	// North(Y), East(X), Up(Z) CHECK LATER WHEN TESTING
	// Position coordinates in the fixed frame
	public static double currentPN, currentPE, currentPU;
	// Velocity components in the fixed frame
	public static double currentVN, currentVE, currentVU;
	// Acceleration components in the fixed frame
	public static double currentAN, currentAE, currentAU;

	public static void updatePosition() {
		currentAE = ahrs.getWorldLinearAccelX();
		currentAN = ahrs.getWorldLinearAccelY();
		currentAU = ahrs.getWorldLinearAccelZ();

		currentVE = ahrs.getVelocityX();
		currentVN = ahrs.getVelocityY();
		currentVU = ahrs.getVelocityZ();
	
		currentPE = ahrs.getDisplacementX();
		currentPN = ahrs.getDisplacementY();
		currentPU = ahrs.getDisplacementZ();
	}
}
