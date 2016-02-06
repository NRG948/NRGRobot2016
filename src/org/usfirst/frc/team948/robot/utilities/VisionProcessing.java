package org.usfirst.frc.team948.robot.utilities;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionProcessing {

	public static NetworkTable table = NetworkTable.getTable("GRIP/myContoursReport");
	public static NetworkTable tableTotalArea = NetworkTable.getTable("GRIP/totalAreaReport");
	public static double[] totalWidth;
	public static double[] totalHeight;
	public static double[] centerX;
	public static double[] centerY;
	public static double[] area;
	public static double[] height;
	public static double[] width;
	private static final double[] DEFAULT_ARRAY = new double[0];
	private static final double TARGET_FEET = 2.5;
	private static final double FOV_ANGLE = 49.64;
	private static double targetPixel;
	private static double fovPixel;
	



	public static void updateVision() {
		centerX = table.getNumberArray("centerX", DEFAULT_ARRAY);
		centerY = table.getNumberArray("centerY", DEFAULT_ARRAY);
		area = table.getNumberArray("area", DEFAULT_ARRAY);
		height = table.getNumberArray("height", DEFAULT_ARRAY);
		width = table.getNumberArray("width", DEFAULT_ARRAY);
		totalWidth = tableTotalArea.getNumberArray("width", DEFAULT_ARRAY);
		totalHeight = tableTotalArea.getNumberArray("height", DEFAULT_ARRAY);
	}
	public static double getArea() {
		return area[0];
	}
	public static double getWidth() {
		return width[0];
	}
	public static double getTotalWidth(){
		return totalWidth[0];
	}
	public static double calcDistance(){
		fovPixel = getTotalWidth();
		targetPixel = getWidth();
		double distance = TARGET_FEET*fovPixel/(2*targetPixel*Math.tan((FOV_ANGLE/2.0)*Math.PI)/180);
		return distance;
	}
	public static double getShooterPower() {
		return 1; // Temporary
	}

}
