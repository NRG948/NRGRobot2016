package org.usfirst.frc.team948.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;

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
	public static Victor motorFrontLeft;
	public static Victor motorFrontRight;
	public static Victor motorBackLeft;
	public static Victor motorBackRight;
	public static Gyro driveGyro;
	public static AnalogInput armAngleEncoder;
	public static Talon acquireArmTalon;
	public static Talon acquireWheelTalon;
	public static BuiltInAccelerometer accelerometer;
	
	public static void init() {

		motorFrontLeft = new Victor(2);
		motorFrontRight = new Victor(3);
		motorBackLeft = new Victor(4);
		motorBackRight = new Victor(5);
		driveGyro = new AnalogGyro(6); //Port number(channel number) unknown, 6 now taken
		armAngleEncoder = new AnalogInput(7); //Port numbers (channel numbers) unknown, 7 now taken
		accelerometer = new BuiltInAccelerometer();
		acquireArmTalon = new Talon(9);
		acquireWheelTalon = new Talon(10);
	}
}