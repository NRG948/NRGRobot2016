package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class VisionProcessing extends Subsystem implements PIDSource, PIDOutput {

	public double centerY;
	public double area;
	public double height;
	public double width;
	public volatile double centerX;
	
	private double targetPixel;
	private double fovPixel;
	private double pidOutput;
	private double cyclesOnTarget;
	
	private final double TARGET_WIDTH_FEET = 19.5 / 12; //horizontal
	private final double TARGET_HEIGHT_FEET = 14.0 / 12; //vertical
	private final double CAMERA_OFF_GROUND = 1;
	private final double TARGET_FEET_OFF_CAMERA_HEIGHT = 84.0/12 - CAMERA_OFF_GROUND; //84.0 is height from found in inches, camera is 1 foot off ground
	private final double GRAVITY = 32;
	private final double SPEED_OF_BALL = 7.1/6 * GRAVITY; //Three trials of shooting straight up, total time was 7.1 seconds
	private final double FOV_ANGLE_HORIZONTAL = 49.64; //horizontal
	private final double FOV_ANGLE_VERTICAL = 32.01; //vertical
	private final double CAMERA_ANGLE = 28;
	private final double CAMERA_TO_SHOOTER = 9.5 / 12.0;
	
	private final double TURN_TARGET_P = 0.0039;
	private final double TURN_TARGET_I =  0.00031;
	private final double TURN_TARGET_D = 0.0123;
	
	private static final double TOTAL_HEIGHT = 240.0;
	private static final double TOTAL_WIDTH = 320.0;

	private boolean visionTracking;
	
	private PIDController targetPID = new PIDController(TURN_TARGET_P, TURN_TARGET_I, TURN_TARGET_D, this, this);
	
	Image frame;
	Image binaryFrame;
	NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(55, 125); //Hue value found for green
	NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(83, 255); //Sat value found for green
	NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(62, 255);  //Val value found for green
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0, 0, 1, 1);
	Command autonomousCommand;
	USBCamera cam;
	
	@Override
	protected void initDefaultCommand() {
		
		
	}

	public void cameraInit() {
		visionTracking = true;
		cam = new USBCamera("cam0"); //create camera object
		//setting Cam settings
//		cam.setExposureManual(-11);
//		cam.setWhiteBalanceHoldCurrent();
		cam.setSize(320, 240);
		cam.updateSettings();

		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, 0.1, 100.0,
				0, 0);//filter out particles less than 0.1% of area.
		cam.startCapture();
	}

	public void updateVision() {
		if (visionTracking) {
			cam.getImage(frame);
			NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TARGET_HUE_RANGE, TARGET_SAT_RANGE,
					TARGET_VAL_RANGE); //filter particles by HSV
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
		else {
			cam.getImage(frame);
			CameraServer.getInstance().setImage(frame);
		}
//		cam.getImage(frame);
////		NIVision.imaqSetImageSize(frame, 320, 240); //shrink frame to 320px by 240px
//		NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TARGET_HUE_RANGE, TARGET_SAT_RANGE,
//				TARGET_VAL_RANGE); //filter particles by HSV
//		CameraServer.getInstance().setImage(binaryFrame); //dump image to SmartDashboard
//		NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null); //Filter small particles
//		int numberOfParticles = NIVision.imaqCountParticles(binaryFrame, 1); //Get number of particles
//		if (numberOfParticles > 0) {
//			centerX = NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
//			centerY = NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);
//			area = NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_AREA);
//			height = NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM) -
//					 NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
//			width = NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT) - 
//					NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
//		}
	}

	public void switchMode() {
		visionTracking = !visionTracking;
		if (visionTracking) {
			cam.setExposureManual(-11);
			cam.setWhiteBalanceHoldCurrent();
		}
		else {
			cam.setExposureAuto();
			cam.setWhiteBalanceAuto();
		}
		cam.updateSettings();
	}
	
	public double getArea() {
		return area;
	}

	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}

	public double getTotalWidth() {
		return TOTAL_WIDTH;
	}

	public double getTotalHeight() {
		return TOTAL_HEIGHT;
	}
	
	public double calcDistance() {
		fovPixel = getTotalWidth();
		targetPixel = (getHeight() / Math.cos(CAMERA_ANGLE * Math.PI / 180)) * TARGET_WIDTH_FEET / TARGET_HEIGHT_FEET; //use ratio of vert to hor to calculate
//		targetPixel = getWidth();
		double distance = TARGET_WIDTH_FEET * fovPixel / (2 * targetPixel * Math.tan((FOV_ANGLE_HORIZONTAL / 2.0) * Math.PI / 180));
		return distance;
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
	
	public double getTurningAngle() {
		targetPixel = getWidth();
		double d = calcDistance();
		d = Math.sqrt(Math.pow(d, 2)- Math.pow(TARGET_FEET_OFF_CAMERA_HEIGHT,2)) + CAMERA_TO_SHOOTER;
		d *= targetPixel / TARGET_WIDTH_FEET;
		double w = centerX - CommandBase.preferences.getDouble(PreferenceKeys.CENTER_IMAGE, 160.0);
		double angle = Math.atan(w / d) * 180 / Math.PI;
		return angle;
	}
	public double getShooterPower() {
		return 1; // Temporary
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return centerX;
	}

	@Override
	public void setPIDSourceType(PIDSourceType arg0) {
		
	}
	
	public void turnToTargetInit() {
		cyclesOnTarget = 0;
		targetPID.setPID(CommandBase.preferences.getDouble(PreferenceKeys.VISION_P, TURN_TARGET_P), 
				  CommandBase.preferences.getDouble(PreferenceKeys.VISION_I, TURN_TARGET_I), 
				  CommandBase.preferences.getDouble(PreferenceKeys.VISION_D, TURN_TARGET_D));
		targetPID.reset();
		targetPID.setOutputRange(-0.5, 0.5);
		targetPID.setAbsoluteTolerance(5);
		targetPID.setToleranceBuffer(3);
		targetPID.enable();
	}
	
	public void setImageCenter(double center) {
		targetPID.setSetpoint(center);
	}

	public void turnToTarget() {
		double power = pidOutput;
		SmartDashboard.putNumber("TurnToTarget error", targetPID.getError());
		SmartDashboard.putNumber("TurnToTarget pidOutput", pidOutput);
		SmartDashboard.putNumber("Center X", Robot.visionProcessing.centerX);
		Robot.drive.rawTankDrive(power, -power);
	}
	
	public boolean turnToTargetFinished() {
//		SmartDashboard.putNumber("Turn to Target finish Error", targetPID.getAvgError());
		if (Math.abs(targetPID.getError()) < 5) { //getAvgError() doesn't work
			cyclesOnTarget++;
		}
		else {
			cyclesOnTarget = 0;
		}
		return cyclesOnTarget >= 3;
//		return targetPID.onTarget(); onTarget() is bugged - command never ends
	}
	
	public void turnToTargetEnd() {
		Robot.drive.rawStop();
		targetPID.disable();
	}
	
	@Override
	public void pidWrite(double arg0) {
		pidOutput = arg0;
	}
}
