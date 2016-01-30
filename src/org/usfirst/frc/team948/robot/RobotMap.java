package org.usfirst.frc.team948.robot;

import org.usfirst.frc.team948.robot.utilities.DummyGyro;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.SerialPort;

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
	public static Victor rightShooterWheel;
	public static Victor leftShooterWheel;
	public static Victor shooterLifterMotor;
	public static Encoder rightShooterWheelEncoder;
	public static Encoder leftShooterWheelEncoder;
	public static AnalogInput shooterLifterEncoder;
	public static Victor motorFrontLeft;
	public static Victor motorFrontRight;
	public static Victor motorBackLeft;
	public static Victor motorBackRight;
	public static Gyro driveGyro;
	public static AnalogInput armAngleEncoder;
	public static Victor acquireArmVictor;
	public static Victor acquireWheelVictor;
	public static Encoder leftMotorEncoder;
	public static Encoder rightMotorEncoder;
	public static AHRS ahrs;
	public static BuiltInAccelerometer accelerometer;
	

	public static void init() {
//The parameters typed in for the encoder objects are random.
		rightShooterWheel = new Victor(4);
		leftShooterWheel = new Victor(5);
		shooterLifterMotor = new Victor(6);
		
		rightShooterWheelEncoder = new Encoder(0, 1);
		leftShooterWheelEncoder = new Encoder(2, 3);
		shooterLifterEncoder = new AnalogInput(4);
		
		ahrs = new AHRS(SerialPort.Port.kMXP);
		
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
			LiveWindow.addSensor("Drive Subsystem", "Drive Gyro", (LiveWindowSendable) driveGyro);							// unknown, 6 now taken
		}

		catch (Exception e) {
			// No gyro available use DummyGyro to prevent NullPointerExceptions
			driveGyro = new DummyGyro();
		}
		
		armAngleEncoder = new AnalogInput(5); // Port numbers (channel numbers)
												// unknown, 7 now taken
		LiveWindow.addSensor("Drive Subsystem", "Angle Arm Encoder", armAngleEncoder);
		
		leftMotorEncoder = new Encoder(7, 8);
		LiveWindow.addSensor("Drive Subsystem", "Left Motor Encoder", leftMotorEncoder);
		
		rightMotorEncoder= new Encoder(9, 10);
		LiveWindow.addSensor("Drive Subsystem", "Right Motor Encoder", rightMotorEncoder);
		
		acquireArmVictor = new Victor(7);
		LiveWindow.addActuator("Acquirer Subsystem", "Acquire Arm Victor", acquireArmVictor);
		
		acquireWheelVictor = new Victor(8);
		LiveWindow.addActuator("Acquirer Subsystem", "Acquire Wheel Victor", acquireWheelVictor);
		
		accelerometer = new BuiltInAccelerometer();
	}
}