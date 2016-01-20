
package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.ManualDrive;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class Drive extends Subsystem {
	public static Victor motorFrontLeft;
	public static Victor motorFrontRight;
	public static Victor motorBackLeft;
	public static Victor motorBackRight;

	
	private double desiredHeading;

	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Drive() {
		motorBackLeft = RobotMap.motorBackLeft;
		motorBackLeft = RobotMap.motorBackRight;
		motorFrontLeft = RobotMap.motorFrontLeft;
		motorFrontRight = RobotMap.motorFrontRight;

	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new ManualDrive());
	}

	public void rawTankDrive(double leftPower, double rightPower) {

		motorFrontLeft.set((-1)*leftPower);
		motorFrontRight.set(rightPower);
		motorBackLeft.set((-1)*leftPower);
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
		desiredHeading =  RobotMap.driveGyro.getAngle();
	}
}
