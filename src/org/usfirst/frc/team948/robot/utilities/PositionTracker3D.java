package org.usfirst.frc.team948.robot.utilities;

import org.usfirst.frc.team948.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

public class PositionTracker3D {
	
	public static double valueToCheck;

	// Acceleration of Gravity in meters per second squared
	public static final double G = 9.8;

	public static boolean finalizeCalibration = false;
	// Calibration of a and omega in arrays
	// these are drift values
	public static double[] acal = new double[3];
	public static double[] ocal = new double[3];

	// Time required for calibration
	public static final double CAL_TIME = 10;

	public static double elapsedTime;

	public static double[] rworld = new double[3];
	public static double[] vworld = new double[3];
	public static double[] aworld = new double[3];

	private static AHRS ahrs = RobotMap.ahrs;
	private static double[] i = { 1, 0, 0 };
	private static double[] j = { 0, 1, 0 };
	private static double[] k = { 0, 0, 1 };
	public static double dt = 0.02;
	public static long ct = System.currentTimeMillis();

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

	public static void computePosition() {
		double ct0 = ct;
		ct = System.currentTimeMillis();
		dt = (ct - ct0) / 1000.0;// This is now in seconds
		elapsedTime += dt;

		if (elapsedTime <= CAL_TIME) {
			calibrate();
			return;
		} else if(!finalizeCalibration){
			for(int index = 0; index<3; index++){
				acal[index] /= elapsedTime;
				ocal[index] /= elapsedTime;
			}
			acal[2]--; // subtract 1 G since aRawZ is 1G
			finalizeCalibration = true;
		}
		
		// unit vector of the rotation
		// angular velocity components in the robot frame (in radians)
		double ox = -(ahrs.getRawGyroX() - ocal[0])* Math.PI / 180;
		double oy = -(ahrs.getRawGyroY() - ocal[1])* Math.PI / 180;
		double oz = -(ahrs.getRawGyroZ() - ocal[2]) * Math.PI / 180;

		// Magnitude of the angular velocity
		double omega = Math.sqrt(ox * ox + oy * oy + oz * oz);
		// Components of the unit vector in angular velocity
		// Angle of the rotation
		double angle = omega * dt;
		if (omega == 0) {
			omega = 1;
		}
		double ux = ox / omega;
		double uy = oy / omega;
		double uz = oz / omega;
		// Rotation matrix
		// Cosine and sine of the angle of rotation
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		// 3-dimensional rotation matrix(see Wikipedia page for three
		// dimensional rotation matrix)
		double[][] rot = {
				{ cos + ux * ux * (1 - cos), ux * uy * (1 - cos) - uz * sin, ux * uz * (1 - cos) + uy * sin },
				{ ux * uy * (1 - cos) + uz * sin, cos + uy * uy * (1 - cos), uy * uz * (1 - cos) - ux * sin },
				{ ux * uz * (1 - cos) - uy * sin, uy * uz * (1 - cos) + ux * sin, cos + uz * uz * (1 - cos) } };
		// New basis vectors of the robot frame size 3 arrays
		double[] inew = new double[3];
		double[] jnew = new double[3];
		double[] knew = new double[3];

		for (int index = 0; index < 3; index++) {
			inew[index] = rot[0][0] * i[index] + rot[1][0] * j[index] + rot[2][0] * k[index];
			jnew[index] = rot[0][1] * i[index] + rot[1][1] * j[index] + rot[2][1] * k[index];
			knew[index] = rot[0][2] * i[index] + rot[1][2] * j[index] + rot[2][2] * k[index];
		}
		// Update basis vectors of the robot frame expressed on the fixed frame
		i = inew;
		j = jnew;
		k = knew;
		// world accelerations(acceleration components in the fixed frame)
		double[] araw = { ahrs.getRawAccelX()- acal[0], ahrs.getRawAccelY()- acal[1], ahrs.getRawAccelZ()- acal[2] };
		double[] newAWorld = new double[3];
		for (int index = 0; index < 3; index++) {
			newAWorld[index] = araw[0] * i[index] + araw[1] * j[index] + araw[2] * k[index];
		}
		// Subtract g since the raw accel measures the true accel minus g
		newAWorld[2]--;
		
		for (int index = 0; index < 3; index++) {
			newAWorld[index] *= G;
		}
		// Switch to navX World Acceleration
//		newAWorld = new double[] { ahrs.getWorldLinearAccelX() * G, ahrs.getWorldLinearAccelY() * G,
//				ahrs.getWorldLinearAccelZ() * G };

		double[] newVWorld = new double[3];
		for (int index = 0; index < 3; index++) {
			newVWorld[index] = vworld[index] + (newAWorld[index] + aworld[index]) / 2 * dt;
		}
		for (int index = 0; index < 3; index++) {
			rworld[index] += (newVWorld[index] + vworld[index]) / 2 * dt;
		}
		// Update velocities and accelerations
		aworld = newAWorld;
		vworld = newVWorld;
	}

	public static void calibrate() {
		acal[0] += ahrs.getRawAccelX() * dt;
		acal[1] += ahrs.getRawAccelY() * dt;
		acal[2] += ahrs.getRawAccelZ() * dt;
		
		ocal[0] += ahrs.getRawGyroX() * dt;
		ocal[1] += ahrs.getRawGyroY() * dt;
		ocal[2] += ahrs.getRawGyroZ() * dt;
	}
}