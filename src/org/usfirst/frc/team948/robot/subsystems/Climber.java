package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.ManualClimb;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

	@Override
	protected void initDefaultCommand() {
//		setDefaultCommand(new ManualClimb());
	}

	public void rawClimb(double winchPower, double tapeMeasurePower) {
//		RobotMap.climberWinch.set(winchPower);
//		RobotMap.climberTapeMeasure.set(-tapeMeasurePower);
		//DIRECTIONS NEED TO BE CHECKED
		RobotMap.motorBackRight.set(winchPower);
		RobotMap.motorFrontRight.set(winchPower);
		RobotMap.motorBackLeft.set(tapeMeasurePower);
		RobotMap.motorFrontLeft.set(tapeMeasurePower);
	}
	
	public void rawStop() {
		RobotMap.climberWinch.set(0);
		RobotMap.climberTapeMeasure.set(0);
	}
}
