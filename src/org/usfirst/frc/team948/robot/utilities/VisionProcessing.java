package org.usfirst.frc.team948.robot.utilities;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionProcessing {
	
	public static NetworkTable table = NetworkTable.getTable("GRIP/myContoursReport");
	
	public static double[] centerX;
	public static double[] centerY;
	public static double[] area;
	public static double[] height;
	public static double[] width;
	private static final double[] DEFAULT_ARRAY = new double[0];
	
	public static void updateVision() {
		centerX = table.getNumberArray("centerX", DEFAULT_ARRAY);
		centerY = table.getNumberArray("centerY", DEFAULT_ARRAY);
		area = table.getNumberArray("area", DEFAULT_ARRAY);
		height = table.getNumberArray("height", DEFAULT_ARRAY);
		width = table.getNumberArray("width", DEFAULT_ARRAY);
	}

}
