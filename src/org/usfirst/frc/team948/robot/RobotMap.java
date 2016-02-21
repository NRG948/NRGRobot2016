package org.usfirst.frc.team948.robot;

import org.usfirst.frc.team948.robot.utilities.AHRSAccelerometer;
import org.usfirst.frc.team948.robot.utilities.AHRSGyro;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
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
	
	public static Victor acquireWheelVictor = new Victor(4);
	public static Victor acquireArmVictor = new Victor(5);
	
	public static Victor rightShooterWheel = new Victor(6);
	public static Victor leftShooterWheel = new Victor(7);
	public static Victor shooterLifterMotor = new Victor(8);
	public static Victor shooterBallPusher=new Victor(9);
	
	public static CANTalon drawbridgeArm = new CANTalon(0); //Might Become CANTalons
	
	public static Victor climberTapeMeasure = new Victor(12); //Might Become CANTalons
	public static Victor climberWinch = new Victor(14); //Might Become CANTalons
	
	public static Encoder rightShooterWheelEncoder = new Encoder(4, 5, false, EncodingType.k1X);
	public static Encoder leftShooterWheelEncoder = new Encoder(6, 7, true, EncodingType.k1X);
	
	public static Encoder rightMotorEncoder  = new Encoder(0, 1);
	public static Encoder leftMotorEncoder = new Encoder(2, 3, true);
	
	public static AnalogInput armAngleEncoder = new AnalogInput(1);
	
	public static AnalogInput shooterLifterEncoder = new AnalogInput(2);
	
	public static AnalogInput drawbridgeEncoder = new AnalogInput(3);//CHECK VALUE
	
	public static DigitalInput acquireLowerLimit = new DigitalInput(8);
	public static DigitalInput acquireUpperLimit = new DigitalInput(9);
	
	public static DigitalInput ballButton = new DigitalInput(10);

	public static AHRS ahrs = new AHRS(SPI.Port.kMXP);
	public static Gyro driveGyro = new AHRSGyro();
	public static Accelerometer accelerometer = new AHRSAccelerometer();
	
	public static Solenoid leds = new Solenoid(7);


	public static void init() {
//The parameters typed in for the encoder objects are random.
		LiveWindow.addActuator("Drive Subsystem", "Speed Controller Front Left Victor",(Victor) motorFrontLeft);
		LiveWindow.addActuator("Drive Subsystem", "Speed Controller Front Right Victor",(Victor) motorFrontRight);
		LiveWindow.addActuator("Drive Subsystem", "Speed Controller Back Left Victor",(Victor) motorBackLeft);
		LiveWindow.addActuator("Drive Subsystem", "Speed Controller Back Right Victor",(Victor) motorBackRight);
		
		LiveWindow.addSensor("Drive Subsystem", "Drive Gyro", (LiveWindowSendable) driveGyro);
		LiveWindow.addSensor("Drive Subsystem", "Right Drive Encoder", rightMotorEncoder);
		LiveWindow.addSensor("Drive Subsystem", "Left Drive Encoder", leftMotorEncoder);
		
		LiveWindow.addActuator("Acquirer Subsystem", "Acquire Wheel Victor", acquireWheelVictor);
		LiveWindow.addActuator("Acquirer Subsystem", "Acquire Arm Victor", acquireArmVictor);
		
		LiveWindow.addSensor("Acquirer Subsystem", "Angle Arm Encoder", armAngleEncoder);
		LiveWindow.addSensor("Acquirer Subsystem", "Acquire Upper Limit", acquireUpperLimit);
		LiveWindow.addSensor("Acquirer Subsystem", "Acquire Lower Limit", acquireLowerLimit);
		
		LiveWindow.addActuator("ShooterWheel Subsystem", "Right Shooter Wheel", rightShooterWheel);
		LiveWindow.addActuator("ShooterWheel Subsystem", "Left Shooter Wheel", leftShooterWheel);
		
		LiveWindow.addActuator("ShooterBar Subsystem", "Shooter Ball Pusher", shooterBallPusher);
		
		LiveWindow.addSensor("ShooterWheel Subsystem", "Right Shooter Wheel Encoder", rightShooterWheelEncoder);
		LiveWindow.addSensor("ShooterWheel Subsystem", "Left Shooter Wheel Encoder", leftShooterWheelEncoder);
		LiveWindow.addSensor("ShooterWheel Subsystem", "Ball Button", ballButton);

		LiveWindow.addActuator("Shooter Arm Subsystem", "Shooter Lifter Motor", shooterLifterMotor);
		
		LiveWindow.addSensor("Shooter Arm Subsystem","Shooter Lifter Encoder", shooterLifterEncoder);

		//LiveWindow.addActuator("Drawbridge Subsystem", "Speed Controller Drawbridge Victor",(Victor) drawbridgeArm);
		
		//LiveWindow.addActuator("Climber Subsystem", "Speed Controller Climber Tapemeasure Victor",(Victor) climberTapeMeasure);
		
		//LiveWindow.addActuator("Climber Subsystem", "Speed Controller Climber Winch Victor",(Victor) climberWinch);

									// unknown, 6 now taken

//		LiveWindow.addActuator("Drawbridge Subsystem", "Drawbridge Encoder", drawbridgeEncoder);
		
		leds.set(true);
		
		RobotMap.leftShooterWheelEncoder.setDistancePerPulse(10.0/10240);
		RobotMap.rightShooterWheelEncoder.setDistancePerPulse(10.0/10011);
		RobotMap.leftShooterWheelEncoder.setPIDSourceType(PIDSourceType.kRate);
		RobotMap.rightShooterWheelEncoder.setPIDSourceType(PIDSourceType.kRate);
		RobotMap.leftShooterWheelEncoder.setSamplesToAverage(5);
		RobotMap.rightShooterWheelEncoder.setSamplesToAverage(5);
		
		RobotMap.leftMotorEncoder.setDistancePerPulse(5.682/2494.67);
		RobotMap.rightMotorEncoder.setDistancePerPulse(5.682/2431.33);
	}
}