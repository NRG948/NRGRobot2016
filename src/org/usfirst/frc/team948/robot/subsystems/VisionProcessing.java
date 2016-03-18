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
import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class VisionProcessing extends Subsystem implements PIDSource, PIDOutput {

	public double centerY;
	public double area;
	public double height;
	public double width;
	public double convexHullArea;
	public volatile double centerX;
	
	private double targetPixel;
	private double fovPixel;
	private double pidOutput;
	private double cyclesOnTarget;
	
	private final double TARGET_WIDTH_FEET = 19.5 / 12; //horizontal
	private final double TARGET_HEIGHT_FEET = 14.0 / 12; //vertical
	private final double CAMERA_OFF_GROUND = 1;
	private final double TARGET_FEET_OFF_CAMERA_HEIGHT = 84.0/12 - CAMERA_OFF_GROUND + TARGET_HEIGHT_FEET ; //84.0 is height from found in inches, camera is 1 foot off ground
	private final double GRAVITY = 32;
	private final double SPEED_OF_BALL = Math.sqrt(36.25*GRAVITY);
	private final double FOV_ANGLE_HORIZONTAL = 49.64; //horizontal
	private final double FOV_ANGLE_VERTICAL = 32.01; //vertical
	private final double CAMERA_ANGLE = (Robot.competitionRobot) ? 34 : 34;
	private final double CAMERA_TO_SHOOTER = 9.5 / 12.0;
	
	private final double TURN_TARGET_P = 0.0039;
	private final double TURN_TARGET_I =  0.00031;
	private final double TURN_TARGET_D = 0.0123;
	private final double PIXEL_TOLERANCE = 4;
	
	private final double SET_AREA = 1750;
	private final double SET_DISTANCE = 113.0 / 12;
	private static final double TOTAL_HEIGHT = 240.0;
	private static final double TOTAL_WIDTH = 320.0;

//	private boolean isUpdating;
	private boolean visionTracking;
	private boolean distanceByArea = true;
	private PIDController targetPID = new PIDController(TURN_TARGET_P, TURN_TARGET_I, TURN_TARGET_D, this, this);
	private Timer timer = new Timer();
	private final long VISION_PROCESSING_PERIOD = 100;
	
	Image frame; 
	Image binaryFrame;
	NIVision.Range TARGET_HUE_RANGE; //Hue value found for green
	NIVision.Range TARGET_SAT_RANGE; //Sat value found for green
	NIVision.Range TARGET_VAL_RANGE;  //Val value found for green
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0, 0, 1, 1);
	Command autonomousCommand;
	USBCamera targetCam;
	USBCamera ballCam;
	
	@Override
	protected void initDefaultCommand() {
				
	}

	public void cameraInit() {
		visionTracking = true;
//		visionTracking = false;
		targetCam = new USBCamera("cam0"); //create camera object
		ballCam = new USBCamera("cam1");
		//setting Cam settings
		targetCam.setExposureManual(-11);
		targetCam.setWhiteBalanceHoldCurrent();
		targetCam.setSize(320, 240);
		targetCam.updateSettings();
		ballCam.setExposureAuto();
		ballCam.setWhiteBalanceAuto();
		ballCam.updateSettings();
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, 0.1, 100.0,
				0, 0);//filter out particles less than 0.1% of area.
		targetCam.startCapture();
//		ballCam.startCapture();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				updateVision();
			}}, 0 , VISION_PROCESSING_PERIOD);
		
	
	}

	public void updateVision() {
		
//		isUpdating = true;
		TARGET_HUE_RANGE = new NIVision.Range(CommandBase.preferences.getInt("Hue_Low", 55), CommandBase.preferences.getInt("Hue_High", 125)); //Hue value found for green
		TARGET_SAT_RANGE = new NIVision.Range(CommandBase.preferences.getInt("Sat_Low", 83), CommandBase.preferences.getInt("Sat_High", 255)); //Sat value found for green
		TARGET_VAL_RANGE = new NIVision.Range(CommandBase.preferences.getInt("Val_Low", 62), CommandBase.preferences.getInt("Val_High", 255));  //Val value found for green
		if (visionTracking) {
			long prevMillis = System.currentTimeMillis();
			targetCam.getImage(frame);
			NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TARGET_HUE_RANGE, TARGET_SAT_RANGE,
					TARGET_VAL_RANGE); //filter particles by HSV
			CameraServer.getInstance().setImage(binaryFrame); //dump image to SmartDashboard
			NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null); //Filter small particles
			int numberOfParticles = NIVision.imaqCountParticles(binaryFrame, 1); //Get number of particles
			//finding the particle closest to center
			int centerParticleIndex = 0;
			double centerParticleDistanceToCenter = Integer.MAX_VALUE;
			for(int i = 0; i < numberOfParticles; i++){
				centerX = NIVision.imaqMeasureParticle(binaryFrame, i, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
				double temp = Math.abs(centerX - 160);
				if(temp < centerParticleDistanceToCenter){
					centerParticleDistanceToCenter = temp;
					centerParticleIndex = i;
				}
			}
			if (numberOfParticles > 0) {
				centerX = NIVision.imaqMeasureParticle(binaryFrame, centerParticleIndex, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
				centerY = NIVision.imaqMeasureParticle(binaryFrame, centerParticleIndex, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);
				area = NIVision.imaqMeasureParticle(binaryFrame, centerParticleIndex, 0, NIVision.MeasurementType.MT_AREA);
				height = NIVision.imaqMeasureParticle(binaryFrame, centerParticleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM) -
						 NIVision.imaqMeasureParticle(binaryFrame, centerParticleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				width = NIVision.imaqMeasureParticle(binaryFrame, centerParticleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT) - 
						NIVision.imaqMeasureParticle(binaryFrame, centerParticleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				convexHullArea = NIVision.imaqMeasureParticle(binaryFrame, centerParticleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
			}
		//	SmartDashboard.putNumber("Time To Do Vision", System.currentTimeMillis() - prevMillis);
		}
		else {
			ballCam.getImage(frame);
			CameraServer.getInstance().setImage(frame);
		}
//		isUpdating = false;
	}

	public void setToDriverCamera() {
		visionTracking = false;
		
		targetCam.stopCapture();
		ballCam.startCapture();
	}
	
	public void setToVisionCamera() {
		visionTracking = true;
		
		ballCam.stopCapture();
		targetCam.startCapture();
	}
	
	public void switchCamera() {
		if (visionTracking)
			setToDriverCamera();
		else
			setToVisionCamera();
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
		double distance = 0;
		SmartDashboard.putNumber("Area", convexHullArea);
		SmartDashboard.putNumber("Height of Object", getHeight());
		if(!distanceByArea){
			fovPixel = getTotalWidth();
			//SmartDashboard.putNumber("Length of Object", getWidth());
			//double realHeight = ((4*convexHullPerimeter - 2*getHeight()) - Math.sqrt(Math.pow(4*convexHullPerimeter - 2*getHeight(),2) - 12*(Math.pow(convexHullPerimeter, 2)- 2*Math.pow(getWidth(), 2) - Math.pow(getHeight(), 2))))/6.0;
			//SmartDashboard.putNumber("Real Height", realHeight);
			//SmartDashboard.putNumber("Obs Length", getWidth());
			targetPixel = (getHeight() / Math.cos(CAMERA_ANGLE * Math.PI / 180)) * TARGET_WIDTH_FEET / TARGET_HEIGHT_FEET; //use ratio of vert to hor to calculate
	//		targetPixel = getWidth();
			distance = TARGET_WIDTH_FEET * fovPixel / (2 * targetPixel * Math.tan((FOV_ANGLE_HORIZONTAL / 2.0) * Math.PI / 180));
		}else{
			distance = Math.sqrt(SET_AREA/convexHullArea)*SET_DISTANCE;
		}
		return distance;
	}
	
	public void switchToCalcDistanceFromArea(){
		distanceByArea = true;
	}
	
	public void switchToCalcDistanceFromHeight(){
		distanceByArea = false;
	}
	
	public double getShootingAngle(){
		double d = calcDistance();
		d = Math.sqrt(d * d - TARGET_FEET_OFF_CAMERA_HEIGHT * TARGET_FEET_OFF_CAMERA_HEIGHT) + CAMERA_TO_SHOOTER;
		double sqrtTerm = d*d - GRAVITY*GRAVITY*Math.pow(d, 4)/Math.pow(SPEED_OF_BALL, 4)- 2*TARGET_FEET_OFF_CAMERA_HEIGHT*GRAVITY*d*d/Math.pow(SPEED_OF_BALL, 2);
		sqrtTerm = Math.sqrt(sqrtTerm);
		double numerator = d-sqrtTerm;
		double denom = GRAVITY*d*d/Math.pow(SPEED_OF_BALL, 2);
		return Math.toDegrees(Math.atan(numerator/denom));
		//return Math.atan(TARGET_FEET_OFF_CAMERA_HEIGHT/d)*180/Math.PI;
	}
	
	public double getTurningAngle() {
		targetPixel = (getHeight() / Math.cos(CAMERA_ANGLE * Math.PI / 180)) * TARGET_WIDTH_FEET / TARGET_HEIGHT_FEET; //use ratio of vert to hor to calculate
		double d = calcDistance();
		d = Math.sqrt(Math.pow(d, 2)- Math.pow(TARGET_FEET_OFF_CAMERA_HEIGHT,2)) + CAMERA_TO_SHOOTER;
		d *= targetPixel / TARGET_WIDTH_FEET;
		double w = centerX - CommandBase.preferences.getDouble(PreferenceKeys.CENTER_IMAGE, 160.0);
		double angle = Math.asin(w / d) * 180 / Math.PI;
		return angle;
	}
	
	public double getTurningAngleProportion() {
		double diff = centerX - CommandBase.preferences.getDouble(PreferenceKeys.CENTER_IMAGE, 160.0);
		double angle = (diff / TOTAL_WIDTH) * FOV_ANGLE_HORIZONTAL;
		return angle;
	}
	
	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}
	
	@Override
	public double pidGet() {
		
		updateVision();
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
		targetPID.setOutputRange(-0.45, 0.45);
		targetPID.setAbsoluteTolerance(PIXEL_TOLERANCE);
		targetPID.setToleranceBuffer(3);
		targetPID.enable();
	}
	
	public void setImageCenter(double center) {
		targetPID.setSetpoint(center);
	}

	public void turnToTarget() {
		double power = pidOutput;
//		SmartDashboard.putNumber("TurnToTarget error", targetPID.getError());
//		SmartDashboard.putNumber("TurnToTarget pidOutput", pidOutput);
//		SmartDashboard.putNumber("Center X", Robot.visionProcessing.centerX);
//		if (power > 0) {
//			Robot.drive.rawTankDrive(power, -power / 2);
//		}
//		else {
//			Robot.drive.rawTankDrive(power, -power);
//		}
		Robot.drive.rawTankDrive(power, -power);
	}
	
	public boolean turnToTargetFinished() {
//		SmartDashboard.putNumber("Turn to Target finish Error", targetPID.getAvgError());
		if (Math.abs(targetPID.getError()) < PIXEL_TOLERANCE) { //getAvgError() doesn't work
			cyclesOnTarget++;
		}
		else {
			cyclesOnTarget = 0;
		}
		return cyclesOnTarget >= 3;
//		return targetPID.onTarget();
	}
	
	public void turnToTargetEnd() {
		Robot.drive.rawStop();
		targetPID.disable();
	}
	
	@Override
	public void pidWrite(double arg0) {
		pidOutput = arg0;
	}

	public boolean getMode() {
		return visionTracking;
	}
	
//	public boolean isUpdating(){
//		return isUpdating;
//	}
	
}
