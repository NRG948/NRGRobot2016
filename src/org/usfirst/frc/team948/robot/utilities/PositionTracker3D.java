package org.usfirst.frc.team948.robot.utilities;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

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

//	public static void test() {
////		Uniform circular motion
//		double[] araw = {0, -1, 0};
//		double radius = 10;
//		double[] gyro = {0,0, Math.sqrt(Math.abs(araw[1] * G) / radius)};
//		
//		double dt = 0.02;
//		double duration = 10;
//		
//		vworld = new double[] {gyro[2] * radius, 0, 0};
//		
//		final double[] x = new double[1 + (int) (duration/dt)];
//		final double[] y = new double[1 + (int) (duration/dt)];
//		final double[] z = new double[1 + (int) (duration/dt)];
//		
//		for(int i = 1; i<x.length; i++) {
//			computePosition(araw, gyro, dt);
//			x[i] = rworld[0];
//			y[i] = rworld[1];
//			z[i] = rworld[2];
//		}
//
//		JFrame frame = new JFrame();
//		frame.setSize(500, 500);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//
//		frame.add(new JPanel() {
//			@Override
//			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				// coordinate changes
//				double radius = 100;
//				int cx = getWidth() / 2;
//				int cy = getHeight() / 2;
//				g.setColor(Color.RED);
//				for (int i = 0; i < x.length; i++) {
//					int xp = (int) (cx + x[i] * radius);
//					int yp = (int) (cy - y[i] * radius);
//					g.fillOval(xp - 1, yp - 1, 2, 2);
//				}
//			}
//		});
//
//	}	
	
	private static void computePosition(double[] araw, double[] gyro, double dt) {
		// Magnitude of the angular velocity
		double omega = 0;
		
		for(double ocomp : gyro) {
			omega += ocomp * ocomp;
		}

		omega = Math.sqrt(omega);
		
		// Components of the unit vector in angular velocity
		// Angle of the rotation
		double angle = omega * dt;
		if (omega == 0) {
			omega = 1;
		}
		double ux = gyro[0] / omega;
		double uy = gyro[1] / omega;
		double uz = gyro[2] / omega;
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
//	
//	public static void main(String[] args) {
//		test();
//	}
}