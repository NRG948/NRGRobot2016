package org.usfirst.frc.team948.robot.utilities;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class VisionProcessing implements PIDSource {

	public double centerX;
	public double centerY;
	public double area;
	public double height;
	public double width;
	private final double TARGET_FEET = 19.5 / 12;
	private final double FOV_ANGLE = 49.64;
	private double targetPixel;
	private double fovPixel;
	private double pidGet;

	private static final double totalHeight = 240.0;
	private static final double totalWidth = 320.0;

	Image frame;
	Image binaryFrame;
	NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(55, 95); //H value found for green
	NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(83, 255); //Sat value found for green
	NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(62, 255);  //Val value found for green
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0, 0, 1, 1);
	Command autonomousCommand;
	USBCamera cam;

	public void cameraInit() {
		cam = new USBCamera("cam0");
		cam.setExposureManual(-11);
		cam.setWhiteBalanceHoldCurrent();
		cam.updateSettings();

		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, 0.1, 100.0,
				0, 0);//filter out particles less than 0.1% of area.
		cam.startCapture();
	}

	public void updateVision() {
		cam.getImage(frame);
		NIVision.imaqSetImageSize(frame, 320, 240);
		NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TOTE_HUE_RANGE, TOTE_SAT_RANGE,
				TOTE_VAL_RANGE);
		CameraServer.getInstance().setImage(binaryFrame);
		NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);
		int numberOfParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		if (numberOfParticles > 0) {
			centerX = NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
			centerY = NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);
			area = NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_AREA);
			height = NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM) -
					 NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
			width = NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT) - 
					NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
		}

	}

	public double getArea() {
		return area;
	}

	public double getWidth() {
		return width;
	}

	public double getTotalWidth() {
		return totalWidth;
	}

	public double calcDistance() throws Exception {
		fovPixel = getTotalWidth();
		targetPixel = getWidth();
		double distance = TARGET_FEET * fovPixel / (2 * targetPixel * Math.tan((FOV_ANGLE / 2.0) * Math.PI / 180));
		return distance;
	}

	public double getShooterPower() {
		return 1; // Temporary
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		pidGet = centerX - (totalWidth / 2);
		return pidGet;
	}

	public double getPidGet() {
		return pidGet;
	}

	@Override
	public void setPIDSourceType(PIDSourceType arg0) {
		// TODO Auto-generated method stub
	}

}
