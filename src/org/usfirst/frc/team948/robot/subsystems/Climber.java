package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

	public void runClimber(double power) {
		RobotMap.climberTapeMeasure.set(power);
		RobotMap.climberWinch.set(power);
	}
}
