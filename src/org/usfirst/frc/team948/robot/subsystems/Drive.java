package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.ManualDrive;
import org.usfirst.frc.team948.robot.utilities.MathHelper;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;	
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;


/**
 *
 */

public class Drive extends Subsystem implements PIDOutput {
	public static Victor motorFrontLeft = RobotMap.motorBackLeft;
	public static Victor motorFrontRight = RobotMap.motorBackRight;
	public static Victor motorBackLeft = RobotMap.motorFrontLeft;
	public static Victor motorBackRight = RobotMap.motorFrontRight;

	private double PIDOutput;
	private double PID_MIN_OUTPUT;
	private double PID_MAX_OUTPUT;
	private double desiredHeading;
	private double DRIVE_STRAIGHT_ON_HEADING_P;
	private double DRIVE_STRAIGHT_ON_HEADING_I;
	private double DRIVE_STRAIGHT_ON_HEADING_D;

	public final PIDController drivePID = new PIDController(0.01, 
			0.01 * 2 * 0.05, 0.005, (AnalogGyro)RobotMap.driveGyro, this);


	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new ManualDrive());
	}

	public double drivePIDInit(double p, double i, double d, double maxOutput) {
		drivePID.reset();
		drivePID.setPID(p,i,d);
		drivePID.setOutputRange(-Math.abs(maxOutput), Math.abs(maxOutput));
		drivePID.enable();
		System.out.println("Drive P:" + p + " I:" + i + " D:" + d);
		return RobotMap.driveGyro.getAngle();
	}

	public void rawTankDrive(double leftPower, double rightPower) {

		motorFrontLeft.set((-1) * leftPower);
		motorFrontRight.set(rightPower);
		motorBackLeft.set((-1) * leftPower);
		motorBackRight.set(rightPower);

	}

	public void rawStop() {
		RobotMap.motorBackLeft.disable();
		RobotMap.motorBackRight.disable();
		RobotMap.motorFrontLeft.disable();
		RobotMap.motorFrontRight.disable();
	}

	public void setDesiredHeadingFromGyro() {
		setDesiredHeading(RobotMap.driveGyro.getAngle());
	}

	public void setDesiredHeading(double angle) {
		desiredHeading = RobotMap.driveGyro.getAngle();
	}

	@Override
	public void pidWrite(double arg0) {
		PIDOutput = arg0;
		// TODO Auto-generated method stub

	}
	public void driveOnHeadingEnd(){ 
		rawStop();
		drivePID.reset();
		PIDOutput = 0;
	}
	public double driveOnHeadingInit(double maxOutput){
		return drivePIDInit(
			CommandBase.preferences.getDouble(PreferenceKeys.Drive_Straight_On_Hea678ding_P, DRIVE_STRAIGHT_ON_HEADING_P),
			CommandBase.preferences.getDouble(PreferenceKeys.Drive_Straight_On_Heading_I, DRIVE_STRAIGHT_ON_HEADING_I), 
			CommandBase.preferences.getDouble(PreferenceKeys.Drive_Straight_On_Heading_D, DRIVE_STRAIGHT_ON_HEADING_D),
			maxOutput); 	
	}
	public void driveOnHeading(double power, double heading) {
		drivePID.setSetpoint(heading);

		double error = heading - RobotMap.driveGyro.getAngle();
		double outputRange = MathHelper.clamp(PID_MIN_OUTPUT
				+ (Math.abs(error) / 15.0) * (PID_MAX_OUTPUT - PID_MIN_OUTPUT),
				0, PID_MAX_OUTPUT);
		drivePID.setOutputRange(-outputRange, outputRange);

		double currentPIDOutput = MathHelper.clamp(PIDOutput, -outputRange,
				outputRange);
		SmartDashboard.putNumber("Current PID OUTPUT", currentPIDOutput);	
		double leftPower = power;
		double rightPower = power;

		if (currentPIDOutput > 0) {
			rightPower -= currentPIDOutput;
		} else {
			leftPower += currentPIDOutput;
		}

		rawTankDrive(leftPower, rightPower);
	}
}
