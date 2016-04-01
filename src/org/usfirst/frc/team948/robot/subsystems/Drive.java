package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.ManualDrive;
import org.usfirst.frc.team948.robot.utilities.MathHelper;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */

public class Drive extends Subsystem implements PIDOutput {
	private static final int REQUIRED_CYCLES_ON_TARGET = 5;//NEED TO CHECK/CHANGE LATER
	private static final double TURN_TO_HEADING_P = 0.039; //NEED TO CHECK/CHANGE LATER
	private static final double TURN_TO_HEADING_I = 0.0056; //NEED TO CHECK/CHANGE LATER
	private static final double TURN_TO_HEADING_D = 0.06825; //NEED TO CHECK/CHANGE LATER

	private volatile double PIDOutput;
	private double PID_MIN_OUTPUT = 0.05;
	private double PID_MAX_OUTPUT = 0.5;
	private double desiredHeading;
	private double prevError;
	private double counter;
	private final double DRIVE_STRAIGHT_ON_HEADING_P = 0.025;
	private final double DRIVE_STRAIGHT_ON_HEADING_I = 0.01;
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

		RobotMap.motorFrontLeft.set(leftPower);
		RobotMap.motorFrontRight.set(-1 * rightPower);
		RobotMap.motorBackLeft.set(leftPower);
		RobotMap.motorBackRight.set(-1 * rightPower);

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
	
	public double getDesiredHeading() {
		return desiredHeading;
	}
	
	public void setDesiredHeading(double angle) {
		desiredHeading = angle;
	}
	
	public double drivePIDInit(double p, double i, double d, double maxOutput) {
		drivePID = new PIDController(p, i, d, (PIDSource)RobotMap.driveGyro, this);
		drivePID.reset();
		drivePID.setOutputRange(-Math.abs(maxOutput), Math.abs(maxOutput));
		PIDOutput = 0;
		drivePID.enable();
		System.out.println("Drive P:" + p + " I:" + i + " D:" + d);
		return RobotMap.driveGyro.getAngle();
	}

	@Override
	public void pidWrite(double arg0) {
		PIDOutput = arg0;
	}
	
	public double driveOnHeadingInit(double maxOutput){
		return drivePIDInit(
			CommandBase.preferences.getDouble(PreferenceKeys.DRIVE_STRAIGHT_ON_HEADING_P, DRIVE_STRAIGHT_ON_HEADING_P),
			CommandBase.preferences.getDouble(PreferenceKeys.DRIVE_STRAIGHT_ON_HEADING_I, DRIVE_STRAIGHT_ON_HEADING_I), 
			CommandBase.preferences.getDouble(PreferenceKeys.DRIVE_STRAIGHT_ON_HEADING_D, DRIVE_STRAIGHT_ON_HEADING_D),
			maxOutput); 	

	}
	public void driveOnHeading(double power, double heading) {
		drivePID.setSetpoint(heading);

		double error = heading - RobotMap.driveGyro.getAngle();
		double outputRange = MathHelper.clamp(PID_MIN_OUTPUT
				+ (Math.abs(error) / 15.0) * (PID_MAX_OUTPUT - PID_MIN_OUTPUT),
				0, PID_MAX_OUTPUT);
		drivePID.setOutputRange(-outputRange, outputRange);

		double currentPIDOutput = MathHelper.clamp(PIDOutput, -PID_MAX_OUTPUT,
				PID_MAX_OUTPUT);
		SmartDashboard.putNumber("Current PID OUTPUT", currentPIDOutput);
		//SmartDashboard.putNumber("Angle", RobotMap.driveGyro.getAngle());
		SmartDashboard.putNumber("Error", error);
		double leftPower = power;
		double rightPower = power;

		if (currentPIDOutput > 0) {
			rightPower -= currentPIDOutput;
		} else {
			leftPower += currentPIDOutput;
		}
		SmartDashboard.putNumber("Left Drive Straight output", leftPower);
		SmartDashboard.putNumber("Right Drive Straight output", rightPower);
		rawTankDrive(leftPower, rightPower);
	}
	public void driveOnHeadingEnd(){ 
		rawStop();
		drivePID.reset();
		PIDOutput = 0;
	}
	
	public void turnToHeadingInit(double finalHeading, double tolerance, double maxOutput) {
		cyclesOnTarget = getRequiredCyclesOnTarget();
		drivePIDInit(
				CommandBase.preferences.getDouble(PreferenceKeys.Turn_P, TURN_TO_HEADING_P),
				CommandBase.preferences.getDouble(PreferenceKeys.Turn_I, TURN_TO_HEADING_I), 
				CommandBase.preferences.getDouble(PreferenceKeys.Turn_D, TURN_TO_HEADING_D),
				maxOutput);
		drivePID.setSetpoint(finalHeading);
		drivePID.setAbsoluteTolerance(tolerance);
		SmartDashboard.putNumber("Desired Heading", desiredHeading);
		prevError = 0;
		counter = 0;
	}
	public void turnToHeading(double finalHeading, double power){
//		drivePID.setSetpoint(finalHeading);
		double currentError = drivePID.getError();
		SmartDashboard.putNumber("Turn PIDOutput", PIDOutput);
		double revisedPower = MathHelper.clamp(PIDOutput, -power, power);
//		if (Math.abs(revisedPower) < 0.22) {
//			revisedPower = 0.22 * Math.signum(currentError);
//		}
		if(prevError * currentError < 0) { //if cross over setpoint, apply brakes
//			revisedPower = 0.5 * Math.signum(prevError);
			SmartDashboard.putNumber("Brake Power", revisedPower);
			counter++;
		}
		prevError = currentError;
		SmartDashboard.putNumber("Turn Heading Cross Setpoint", counter);
		rawTankDrive(revisedPower, -revisedPower);
	}
	
	public void turnToHeadingEnd(double newHeading){
		setDesiredHeading(newHeading); 
		drivePID.reset();
		PIDOutput = 0;
	}
	
	public boolean turnToHeadingComplete(double tolerance){
		boolean onTarget = Math.abs(drivePID.getError()) < tolerance;
		SmartDashboard.putNumber("Turn Error", drivePID.getError());
		
		
		//boolean onTarget = drivePID.onTarget();
		SmartDashboard.putBoolean("Turn onTarget?", onTarget);
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
