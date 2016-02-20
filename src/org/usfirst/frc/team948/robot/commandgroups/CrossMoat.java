package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.commands.DriveStraightDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CrossMoat extends CommandGroup {
	public CrossMoat(){
		addSequential(new DriveStraightDistance(1,2));
		
	}
}
