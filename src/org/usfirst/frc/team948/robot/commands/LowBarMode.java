package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarMode extends CommandGroup{
	
	public LowBarMode() {
		addSequential(new RaiseAcquirerTo(Robot.Level.PORTCULLIS_LOW));
		addSequential(new RaiseShooterArmTo(0));
		
		//ALL VALUES NEED TO BE TESTED
	}
	
}
