package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team948.robot.commands.ManualDrawbridge;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowerDrawbridge extends CommandGroup{
	
	public LowerDrawbridge() {
		
		//Extends Drawbridge
		addSequential(new ManualDrawbridge(true), 10);//CHECK TIME LATER

		//Drives backwards 1 foot
		addSequential(new DriveStraightDistance(-1, 1));
		
		//ALL VALUES NEED TO BE CHECKED
	}
}
