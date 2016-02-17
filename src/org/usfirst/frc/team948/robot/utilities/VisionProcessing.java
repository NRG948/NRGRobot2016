package org.usfirst.frc.team948.robot.utilities;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class VisionProcessing implements PIDSource {

	public double centerX;
	public double centerY;
	public double area;
	public double height;
	public double width;
	private final double TARGET_FEET = 19.5 / 12;
	private final double CAMERA_OFF_GROUND = 1;
	private final double TARGET_FEET_OFF_CAMERA_HEIGHT = 84.0/12 - CAMERA_OFF_GROUND; //97.0 is height from found in inches, camera is 1 foot off ground
	private final double GRAVITY = 32;
	private final double SPEED_OF_BALL = 6.55/6 * GRAVITY; //Three trials of shooting straight up, total time was 6.55 seconds
	private final double FOV_ANGLE = 49.64;
	private final double CAMERA_TO_SHOOTER = 14 / 12.0;
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
		cam = new USBCamera("cam0"); //create camera object
		//setting Cam settings
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
		NIVision.imaqSetImageSize(frame, 320, 240); //shrink frame to 320px by 240px
		NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TOTE_HUE_RANGE, TOTE_SAT_RANGE,
				TOTE_VAL_RANGE); //filter particles by HSV
		CameraServer.getInstance().setImage(binaryFrame); //dump image to SmartDashboard
		NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null); //Filter small particles
		int numberOfParticles = NIVision.imaqCountParticles(binaryFrame, 1); //Get number of particles
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

	public double calcDistance()  {
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
	
	public double getShootingAngle(){
		double d = calcDistance();
		d = Math.sqrt(Math.pow(d, 2)- Math.pow(TARGET_FEET_OFF_CAMERA_HEIGHT,2)) + CAMERA_TO_SHOOTER;
		double sqrtTerm = d*d - GRAVITY*GRAVITY*Math.pow(d, 4)/Math.pow(SPEED_OF_BALL, 4)- 2*TARGET_FEET_OFF_CAMERA_HEIGHT*GRAVITY*d*d/Math.pow(SPEED_OF_BALL, 2);
		sqrtTerm = Math.sqrt(sqrtTerm);
		double numerator = d-sqrtTerm;
		double denom = GRAVITY*d*d/Math.pow(SPEED_OF_BALL, 2);
		return Math.atan(numerator/denom)*180/Math.PI;
	}
}
