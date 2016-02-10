package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowerDrawbridge extends CommandGroup{
	
	public LowerDrawbridge() {
		
		//Extends Drawbridge
		addSequential(new ManualDrawbridge(true));

		//Drives backwards 1 foot
		addSequential(new DriveStraightFeet(-1, 1));
		
		//ALL VALUES NEED TO BE CHECKED
	}
}
