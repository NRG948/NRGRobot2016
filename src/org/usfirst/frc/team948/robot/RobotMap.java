package org.usfirst.frc.team948.robot;

import org.usfirst.frc.team948.robot.utilities.DummyGyro;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	public static Talon rightShooterWheel;
	public static Talon leftShooterWheel;
	public static Talon shooterLifterMotor;
	public static Encoder rightShooterWheelEncoder;
	public static Encoder leftShooterWheelEncoder;
	public static Encoder shooterLifterEncoder;
	public static Victor motorFrontLeft;
	public static Victor motorFrontRight;
	public static Victor motorBackLeft;
	public static Victor motorBackRight;
	public static Gyro driveGyro;
	public static AnalogInput armAngleEncoder;
	public static Talon acquireArmTalon;
	public static Talon acquireWheelTalon;
	public static BuiltInAccelerometer accelerometer;
	public static AnalogInput aLeftGear;
	public static AnalogInput bLeftGear;
	public static AnalogInput aRightGear;
	public static AnalogInput bRightGear;
	public static AHRS ahrs;
	

	public static void init() {
//The parameters typed in for the encoder objects are random.
		rightShooterWheel=new Talon(0);
		leftShooterWheel=new Talon(1);
		shooterLifterMotor=new Talon(2);
		rightShooterWheelEncoder=new Encoder(1,2);
		leftShooterWheelEncoder=new Encoder(2,3);
		shooterLifterEncoder=new Encoder(3,4);
		
		motorFrontLeft = new Victor(2);
		LiveWindow.addActuator("Drive Subsystem", "Speed Controller Front Left Victor",(Victor) motorFrontLeft);
		
		motorFrontRight = new Victor(0);
		LiveWindow.addActuator("Drive Subsystem", "Speed Controller Front Right Victor",(Victor) motorFrontRight);
		
		motorBackLeft = new Victor(3);
		LiveWindow.addActuator("Drive Subsystem", "Speed Controller Back Left Victor",(Victor) motorBackLeft);
		
		motorBackRight = new Victor(1);
		LiveWindow.addActuator("Drive Subsystem", "Speed Controller Back Right Victor",(Victor) motorBackRight);

		

		try {
			driveGyro = new AnalogGyro(6); // Port number(channel number)
											// unknown, 6 now taken
		}

		catch (Exception e) {
			// No gyro available use DummyGyro to prevent NullPointerExceptions
			driveGyro = new DummyGyro();
		}
		LiveWindow.addSensor("Drive Subsystem", "Drive Gyro", (LiveWindowSendable) driveGyro);
		armAngleEncoder = new AnalogInput(7); // Port numbers (channel numbers)
												// unknown, 7 now taken
		LiveWindow.addSensor("Drive Subsystem", "Angle Arm Encoder", armAngleEncoder);
		aLeftGear = new AnalogInput(0);
		LiveWindow.addSensor("Drive Subsystem", "A Left Gear", aLeftGear);
		bLeftGear = new AnalogInput(1);
		LiveWindow.addSensor("Drive Subsystem", "B Left Gear", bLeftGear);
		aRightGear = new AnalogInput(2);
		LiveWindow.addSensor("Drive Subsystem", "A Right Gear", aRightGear);
		bRightGear = new AnalogInput(3); 
		LiveWindow.addSensor("Drive Subsystem", "B Right Gear", bRightGear);

		accelerometer = new BuiltInAccelerometer();
		acquireArmTalon = new Talon(9);
		LiveWindow.addActuator("Acquirer Subsystem", "Acquire Arm Talon", acquireArmTalon);
		acquireWheelTalon = new Talon(10);
		LiveWindow.addActuator("Acquirer Subsystem", "Acquire Wheel Talon", acquireWheelTalon);
	}
}