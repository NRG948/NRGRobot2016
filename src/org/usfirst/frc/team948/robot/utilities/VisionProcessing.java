package org.usfirst.frc.team948.robot.utilities;

import com.sun.org.apache.bcel.internal.classfile.Code;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionProcessing implements PIDSource {

	public  NetworkTable table = NetworkTable.getTable("GRIP/myContoursReport");
	public  NetworkTable tableTotalArea = NetworkTable.getTable("GRIP/totalAreaReport");
	public  double[] centerX;
	public  double[] centerY;
	public  double[] area;
	public  double[] height;
	public  double[] width;
	private final double[] DEFAULT_ARRAY = new double[0];
	private final double TARGET_FEET = 19.5/12;
	private final double FOV_ANGLE = 49.64;
	private double targetPixel;
	private double fovPixel;
	private double pidGet;

	private static final double totalHeight = 240.0;
	private static final double totalWidth = 320.0;





	public void updateVision() {
		centerX = table.getNumberArray("centerX", DEFAULT_ARRAY);
		centerY = table.getNumberArray("centerY", DEFAULT_ARRAY);
		area = table.getNumberArray("area", DEFAULT_ARRAY);
		height = table.getNumberArray("height", DEFAULT_ARRAY);
		width = table.getNumberArray("width", DEFAULT_ARRAY);
		
	}

	public double getArea() {
		try {
			return area[0];
		} catch (IndexOutOfBoundsException e) {
			return 0.0;
		}
	}
	public double getWidth(){
		try{
			return width[0];
		}catch(Exception e){
			//e.printStackTrace();
			return 1.0;
		}
	}


	public double getTotalWidth() {
		try {
			return totalWidth;
		} catch (IndexOutOfBoundsException e) {
			return 0.0;
		}

	}
	public  double calcDistance() throws Exception{
		fovPixel = getTotalWidth();
		targetPixel = getWidth();
		double distance = TARGET_FEET*fovPixel/(2*targetPixel*Math.tan((FOV_ANGLE/2.0)*Math.PI/180));
		return distance;
	}

	public double getShooterPower() {
		return 1; // Temporary
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		pidGet = Math.abs(centerX[0] - (totalWidth/2));
		return pidGet;
	}
	public double getPidGet()
	{
		return pidGet;
	}

	@Override
	public void setPIDSourceType(PIDSourceType arg0) {
		// TODO Auto-generated method stub
		
	}

}
