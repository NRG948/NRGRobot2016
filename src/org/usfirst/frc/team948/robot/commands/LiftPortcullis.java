package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LiftPortcullis extends CommandGroup{
	
	public LiftPortcullis() {
		addSequential(new RaiseAcquirerTo(Robot.Level.PORTCULLIS_LOW));
		
		addSequential(new RaiseAcquirerTo(Robot.Level.PORTCULLIS_HIGH));
		
		addSequential(new DriveStraightFeet(1,10)); //CHECK VALUE
	}
}
