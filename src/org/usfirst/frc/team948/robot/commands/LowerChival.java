package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.subsystems.Acquirer;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowerChival extends CommandGroup{
	
	public static double DEFAULT;
	public static double PORTCULLIS_LOW;
	
	public LowerChival() {
		//Move acquirer to DEFAULT
		addSequential(new RaiseAcquirerTo(DEFAULT));
		//Move acquirer to PORTCULLIS_LOW
		addSequential(new RaiseAcquirerTo(PORTCULLIS_LOW));		
		//Drive forward for 1 foot 
		addSequential(new DriveStraightFeet(1, 1));//Value needs to be checked
	}	
}
