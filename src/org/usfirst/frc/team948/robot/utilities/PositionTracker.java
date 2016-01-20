package org.usfirst.frc.team948.robot.utilities;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.usfirst.frc.team948.robot.RobotMap;

public class PositionTracker {

	public static final double ONE_G = 32.2; // In feet per second squared
	public static final double DT = 0.02; // In seconds

	// Position coordinates in the fixed frame
	public static double currentPN, currentPE;
	// Velocity components in the fixed frame
	public static double currentVN, currentVE;
	// Acceleration components in the fixed frame
	public static double currentAN, currentAE;

	public static void updatePosition() {
		double ax = RobotMap.accelerometer.getX() * ONE_G;
		double ay = RobotMap.accelerometer.getY() * ONE_G;
		double angle = RobotMap.driveGyro.getAngle() * Math.PI / 180;
		
		updatePosition(ax, ay, angle);
	}
	
	private static void updatePosition(double ax, double ay, double angle)
	{
		// acceleration components
		double an = ax * Math.cos(angle) - ay * Math.sin(angle);
		double ae = ax * Math.sin(angle) + ay * Math.cos(angle);

		// velocities
		double vn = currentVN + (currentAN + an) / 2 * DT;
		double ve = currentVE + (currentAE + ae) / 2 * DT;

		// position
		double pn = currentPN + (currentVN + vn) / 2 * DT;
		double pe = currentPE + (currentVE + ve) / 2 * DT;

		currentAN = an;
		currentAE = ae;

		currentVN = vn;
		currentVE = ve;

		currentPN = pn;
		currentPE = pe;
	}
	
	public static void test() {
		double ax = -1;
		double ay = 0;
		double omega = 1;
		double duration = 30;
		final double[] x = new double[(int) (duration / DT)];
		final double[] y = new double[(int) (duration / DT)];
		currentVE = omega * 1;
		currentVN = 0;
		for (int i = 0; i < duration / DT; i++) {
			double angle = omega * i * DT;
			
			updatePosition(ax, ay, angle);

			x[i] = currentPE;
			y[i] = currentPN;
		}

		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		frame.add(new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// coordinate changes
				double radius = 100;
				int cx = getWidth() / 2;
				int cy = getHeight() / 2;
				g.setColor(Color.RED);
				for (int i = 0; i < x.length; i++) {
					int xp = (int) (cx + x[i] * radius);
					int yp = (int) (cy - y[i] * radius);
					g.fillOval(xp - 1, yp - 1, 2, 2);
				}
			}
		});

	}

	public static void main(String[] args) {
		test();
	}
}
