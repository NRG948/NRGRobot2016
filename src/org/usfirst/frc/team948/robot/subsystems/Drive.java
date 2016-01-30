package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.ManualDrive;
import org.usfirst.frc.team948.robot.utilities.MathHelper;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;


/**
 *
 */

public class Drive extends Subsystem implements PIDOutput {
	private static final int REQUIRED_CYCLES_ON_TARGET = 3;//NEED TO CHECK/CHANGE LATER
	private static final double TURN_TO_HEADING_P = 0; //NEED TO CHECK/CHANGE LATER
	private static final double TURN_TO_HEADING_I = 0; //NEED TO CHECK/CHANGE LATER
	private static final double TURN_TO_HEADING_D = 0; //NEED TO CHECK/CHANGE LATER

	private double PIDOutput;
	private double PID_MIN_OUTPUT = 0;
	private double PID_MAX_OUTPUT = 0.5;
	private double desiredHeading;
	private final double DRIVE_STRAIGHT_ON_HEADING_P = 0.02;
	private final double DRIVE_STRAIGHT_ON_HEADING_I = 0.005;
	private final double DRIVE_STRAIGHT_ON_HEADING_D = 0.02;

	public PIDController drivePID;
	private int cyclesOnTarget;


	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new ManualDrive());
	}

	

	public void rawTankDrive(double leftPower, double rightPower) {

		RobotMap.motorFrontLeft.set((-1) * leftPower);
		RobotMap.motorFrontRight.set(rightPower);
		RobotMap.motorBackLeft.set((-1) * leftPower);
		RobotMap.motorBackRight.set(rightPower);

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
	public double drivePIDInit(double p, double i, double d, double maxOutput) {
		drivePID = new PIDController(0.01,
				0.01 * 2 * 0.05, 0.005, (PIDSource)RobotMap.driveGyro, this);
		drivePID.reset();
		drivePID.setPID(p,i,d);
		drivePID.setOutputRange(-Math.abs(maxOutput), Math.abs(maxOutput));
		drivePID.enable();
		System.out.println("Drive P:" + p + " I:" + i + " D:" + d);
		return RobotMap.driveGyro.getAngle();
	}

	@Override
	public void pidWrite(double arg0) {
		PIDOutput = arg0;
		// TODO Auto-generated method stub

	}
	
	public double driveOnHeadingInit(double maxOutput){
		return drivePIDInit(
			CommandBase.preferences.getDouble(PreferenceKeys.Drive_Straight_On_Heading_P, DRIVE_STRAIGHT_ON_HEADING_P),
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
		SmartDashboard.putNumber("Angle", RobotMap.driveGyro.getAngle());
		SmartDashboard.putNumber("Error", error);
		double leftPower = power;
		double rightPower = power;

		if (currentPIDOutput > 0) {
			leftPower -= currentPIDOutput;
		} else {
			rightPower += currentPIDOutput;
		}

		rawTankDrive(leftPower, rightPower);
	}
	public void driveOnHeadingEnd(){ 
		rawStop();
		drivePID.reset();
		PIDOutput = 0;
	}
	
	public double turnToHeadingInit(double tolerance, double maxOutput) {
		cyclesOnTarget = getRequiredCyclesOnTarget();
		drivePID.setAbsoluteTolerance(tolerance);
		drivePIDInit(
				CommandBase.preferences.getDouble(PreferenceKeys.Turn_P, TURN_TO_HEADING_P),
				CommandBase.preferences.getDouble(PreferenceKeys.Turn_I, TURN_TO_HEADING_I), 
				CommandBase.preferences.getDouble(PreferenceKeys.Turn_D, TURN_TO_HEADING_D),
				maxOutput);
		return desiredHeading;     
	}
	public void turnToHeading(double finalHeading, double power){
		drivePID.setSetpoint(finalHeading);
		double currentPower = MathHelper.clamp(PIDOutput, -power, power);
		rawTankDrive (currentPower, -currentPower);
	}
	
	public void turnToHeadingEnd(double newHeading){
		setDesiredHeading(newHeading); 
		drivePID.reset();
		pidOutput = 0;
	}
	
	public boolean turnToHeadingComplete(){
		boolean onTarget = drivePID.onTarget();
		if (onTarget) {
			cyclesOnTarget++;
		}
		else {
			cyclesOnTarget = 0;
		}
		return cyclesOnTarget >= getRequiredCyclesOnTarget();
	}
	
	
	public int getRequiredCyclesOnTarget(){
		return REQUIRED_CYCLES_ON_TARGET;
	}
	
	
		
		
	

		
	

}
