package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.ManualAcquire;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

	@Override
	protected void initDefaultCommand() {
		//setDefaultCommand(new ManualAcquire());
	}

	public void runClimber(double power) {
		RobotMap.climberTapeMeasure.set(power);
		RobotMap.climberWinch.set(power);
	}
}
