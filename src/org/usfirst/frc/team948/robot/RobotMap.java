package org.usfirst.frc.team948.robot;

import org.usfirst.frc.team948.robot.utilities.AHRSAccelerometer;
import org.usfirst.frc.team948.robot.utilities.AHRSGyro;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
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
	
	public static final boolean competitionBot = false;
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	public static Victor motorFrontLeft = new Victor(2);
	public static Victor motorFrontRight = new Victor(0);
	public static Victor motorBackLeft = new Victor(3);
	public static Victor motorBackRight = new Victor(1);
	public static Victor acquireArmVictor = new Victor(5);
	public static Victor acquireWheelVictor = new Victor(4);
	public static Victor rightShooterWheel = new Victor(6);
	public static Victor leftShooterWheel = new Victor(7);
	public static Victor shooterLifterMotor = new Victor(8);
	public static Victor shooterBallPusher=new Victor(9);
	public static Victor drawbridgeArm = new Victor(11); //Might Become CANTalons
	public static Victor climberTapeMeasure = new Victor(12); //Might Become CANTalons
	public static Victor climberWinch = new Victor(14); //Might Become CANTalons
	public static Encoder rightShooterWheelEncoder = new Encoder(4, 5);
	public static Encoder leftShooterWheelEncoder = new Encoder(6, 7);
	public static Encoder leftMotorEncoder = new Encoder(2, 3);
	public static Encoder rightMotorEncoder  = new Encoder(0, 1);
	public static DigitalInput acquireUpperLimit = new DigitalInput(9);
	public static DigitalInput acquireLowerLimit = new DigitalInput(8);
	public static AnalogInput shooterLifterEncoder = new AnalogInput(2);
	public static AnalogInput armAngleEncoder = new AnalogInput(1);
	public static AHRS ahrs = new AHRS(SPI.Port.kMXP);
	public static Gyro driveGyro = new AHRSGyro();
	public static Accelerometer accelerometer = new AHRSAccelerometer();
	public static AnalogInput drawbridgeEncoder = new AnalogInput(3);//CHECK VALUE

	public static void init() {
//The parameters typed in for the encoder objects are random.
		LiveWindow.addActuator("Drive Subsystem", "Speed Controller Front Left Victor",(Victor) motorFrontLeft);
		
		LiveWindow.addActuator("Drive Subsystem", "Speed Controller Front Right Victor",(Victor) motorFrontRight);
		
		LiveWindow.addActuator("Drive Subsystem", "Speed Controller Back Left Victor",(Victor) motorBackLeft);
		
		LiveWindow.addActuator("Drive Subsystem", "Speed Controller Back Right Victor",(Victor) motorBackRight);
		
		LiveWindow.addSensor("Drive Subsystem", "Drive Gyro", (LiveWindowSendable) driveGyro);
		
		LiveWindow.addSensor("Drive Subsystem", "Right Drive Encoder", rightMotorEncoder);
		
		LiveWindow.addSensor("Drive Subsystem", "Left Drive Encoder", leftMotorEncoder);
		
		LiveWindow.addActuator("Acquirer Subsytem", "Acquire Wheel Victor", acquireWheelVictor);
		
		LiveWindow.addSensor("Acquirer Subsystem", "Angle Arm Encoder", armAngleEncoder);
		
		LiveWindow.addActuator("Acquirer Subsystem", "Acquire Arm Victor", acquireArmVictor);
		
		LiveWindow.addActuator("Shooter Subsystem", "Right Shooter Wheel", rightShooterWheel);
		
		LiveWindow.addActuator("Shooter Subsystem", "Left Shooter Wheel", leftShooterWheel);
		
		LiveWindow.addActuator("ShooterBar Subsystem", "Shooter Ball Pusher", shooterBallPusher);
		
		LiveWindow.addSensor("ShooterWheel Subsystem", "Right Shooter Wheel Encoder", rightShooterWheelEncoder);
		
		LiveWindow.addSensor("ShooterWheel Subsystem", "Left Shooter Wheel Encoder", leftShooterWheelEncoder);
		
		LiveWindow.addActuator("Shooter Arm Subsystem", "Shooter Lifter Motor", shooterLifterMotor);
		
		LiveWindow.addSensor("Shooter Arm Subsystem","Shooter Lifter Encoder", shooterLifterEncoder);
		LiveWindow.addSensor("Acquirer Subsystem", "Acquire Upper Limit", acquireUpperLimit);
		LiveWindow.addSensor("Acquirer Subsystem", "Acquire Lower Limit", acquireLowerLimit);
		//LiveWindow.addActuator("Drawbridge Subsystem", "Speed Controller Drawbridge Victor",(Victor) drawbridgeArm);
		
		//LiveWindow.addActuator("Climber Subsystem", "Speed Controller Climber Tapemeasure Victor",(Victor) climberTapeMeasure);
		
		//LiveWindow.addActuator("Climber Subsystem", "Speed Controller Climber Winch Victor",(Victor) climberWinch);

									// unknown, 6 now taken

		LiveWindow.addActuator("Drawbridge Subsystem", "Drawbridge Encoder", drawbridgeEncoder);
		
		//place holder need a real DistancePerPusle
		RobotMap.leftShooterWheelEncoder.setDistancePerPulse(1/1038.75);
		RobotMap.rightShooterWheelEncoder.setDistancePerPulse(1/1035.15);
	}
}