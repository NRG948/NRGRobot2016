	
package org.usfirst.frc.team948.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.RaiseShooterArmTo;
import org.usfirst.frc.team948.robot.commands.ShooterRampUp;
import org.usfirst.frc.team948.robot.commands.TurnAngle;
import org.usfirst.frc.team948.robot.subsystems.Acquirer;
import org.usfirst.frc.team948.robot.subsystems.Climber;
import org.usfirst.frc.team948.robot.subsystems.Drawbridge;
import org.usfirst.frc.team948.robot.subsystems.Drive;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;
import org.usfirst.frc.team948.robot.subsystems.ShooterBar;
import org.usfirst.frc.team948.robot.subsystems.ShooterWheel;
import org.usfirst.frc.team948.robot.utilities.NavXTester;
import org.usfirst.frc.team948.robot.utilities.PositionTracker;
import org.usfirst.frc.team948.robot.utilities.PositionTracker3D;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;
import org.usfirst.frc.team948.robot.utilities.VisionProcessing;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public enum Level {
		PORTCULLIS_LOW(0.0),
		DEFAULT(1.0),
		PORTCULLIS_HIGH(2.0),
		SALLY_ENGAGED(3.0),
		SALLY_PORT_HIGH(4.0),
		FULL_BACK_START(5.0);
		
		private double value;

		private Level(double value) {
			this.value = value;
		}
		
		public double getValue(){
			return value;
		}
	}
	public static Drive drive = new Drive();
	public static ShooterWheel shooterWheel = new ShooterWheel();
	public static ShooterBar shooterBar = new ShooterBar();
	public static ShooterArm shooterArm = new ShooterArm();
	public static Acquirer acquirer = new Acquirer();
	public static Climber climber = new Climber();
	public static Drawbridge drawbridge = new Drawbridge();
	public static PowerDistributionPanel pdp = new PowerDistributionPanel();
	public static VisionProcessing visionProcessing = new VisionProcessing();
    Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    /*	USBCamera cam = new USBCamera("cam0");
    	cam.setExposureManual(-11);
    	cam.setWhiteBalanceHoldCurrent();
    	cam.updateSettings();
    	cam.openCamera();
    	CameraServer.getInstance().startAutomaticCapture(cam);*/
    //	am.set
		RobotMap.init();
		DS2016.buttonInit();
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		periodicAll();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new RawTankDrive();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        periodicAll();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    	
    //	CommandBase.drive.initDefaultCommand();
        if (autonomousCommand != null) autonomousCommand.cancel();

        SmartDashboard.putData("Raise Shooter Arm to X degrees", new RaiseShooterArmTo(CommandBase.preferences.getDouble(PreferenceKeys.SHOOTER_ANGLE,  45)));

        SmartDashboard.putData("Raise Acquirer to X degrees", new RaiseAcquirerTo(CommandBase.preferences.getDouble(PreferenceKeys.ACQUIRER_ANGLE, 90)));

        SmartDashboard.putData("Turn 135 degrees", new TurnAngle(135, 0.7));
    }
   
    

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("left encoder", RobotMap.leftMotorEncoder.get());
        SmartDashboard.putNumber("right encoder", RobotMap.rightMotorEncoder.get());
        periodicAll();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
        periodicAll();
    }
    public void periodicAll(){

    	SmartDashboard.putNumber("Left RPM", shooterWheel.currentLeftRPM);
    	SmartDashboard.putNumber("Right RPM", shooterWheel.currentRightRPM);
    	SmartDashboard.putNumber("Arm Angle", RobotMap.armAngleEncoder.getVoltage());
    	SmartDashboard.putNumber("Shooter Angle", RobotMap.shooterLifterEncoder.getVoltage());
    	//PositionTracker.updatePosition();
    	//PositionTracker3D.computePosition();
    	NavXTester.parameterDisplay();
    	shooterWheel.updateLeftRPM();
    	shooterWheel.updateRightRPM();
    	visionProcessing.updateVision();

    	try {
			SmartDashboard.putNumber("Distance", visionProcessing.calcDistance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	SmartDashboard.putData("PDP", pdp);
//		for (int i = 0; i <= 15; i++) {
//			SmartDashboard.putNumber("PDP current " + i, pdp.getCurrent(i));
//		}
//		SmartDashboard.putNumber("PDP Total Current", pdp.getTotalCurrent());
//		SmartDashboard.putNumber("PDP Total Voltage", pdp.getVoltage());
//		SmartDashboard.putData("ShooterRampUp", new ShooterRampUp(true));

    }
}
