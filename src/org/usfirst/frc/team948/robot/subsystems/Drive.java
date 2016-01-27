package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.ManualDrive;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */

public class Drive extends Subsystem implements PIDOutput {
	public static Victor motorFrontLeft = RobotMap.motorBackLeft;
	public static Victor motorFrontRight = RobotMap.motorBackRight;
	public static Victor motorBackLeft = RobotMap.motorFrontLeft;
	public static Victor motorBackRight = RobotMap.motorFrontRight;

	private double PIDOutput;
	private double desiredHeading;
	
	private double driveStraightP;
	private double driveStraightI;
	private double driveStraightD;
	
	private PIDController drivePID = new PIDController(driveStraightP,driveStraightI,driveStraightD, RobotMap.driveGyro, this);

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new ManualDrive());
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
}
