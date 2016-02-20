package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;


import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowerChival extends CommandGroup{
	
	public LowerChival() {
		//Move acquirer to DEFAULT
		addSequential(new RaiseAcquirerTo(Robot.Level.ACQUIRE));
		//Move acquirer to PORTCULLIS_LOW
		addSequential(new RaiseAcquirerTo(Robot.Level.DEFAULT));
		//Drive forward for 1 foot 
		addSequential(new DriveStraightDistance(1, 1));//Value needs to be checked
	}	
}
