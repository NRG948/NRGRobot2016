
package org.usfirst.frc.team948.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.ManualDrive;
import org.usfirst.frc.team948.robot.subsystems.Acquirer;
import org.usfirst.frc.team948.robot.subsystems.Drive;
import org.usfirst.frc.team948.robot.subsystems.Shooter;
import org.usfirst.frc.team948.robot.utilities.NavXTester;
import org.usfirst.frc.team948.robot.utilities.PositionTracker;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public enum ACQUIRER_ARM_LEVEL_HEIGHT {
		PORTCULLIS_LOW(0),
		DEFAULT(30),
		PORTCULLIS_HIGH(65),
		SALLY_ENGAGED(70),
		SALLY_PORT_HIGH(75),
		FULL_BACK_START(105);
		
		private double value;

		private ACQUIRER_ARM_LEVEL_HEIGHT(double value) {
			this.value = value;
		}
	}
	public static Drive drive = new Drive();
	public static Shooter shooter;
	public static Acquirer acquirer;
    Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
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
    }
   
    

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
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
    	SmartDashboard.putNumber("Joy1 Y", DS2016.getLeftJSY());
    	//PositionTracker.updatePosition();
    	NavXTester.parameterDisplay();
    	shooter.updateLeftRPM();
    	shooter.updateRightRPM();
    }
}
