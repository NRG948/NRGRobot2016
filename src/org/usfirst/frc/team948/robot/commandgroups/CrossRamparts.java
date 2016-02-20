package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.commands.DriveStraightDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CrossRamparts extends CommandGroup {
	public CrossRamparts(){
		addSequential(new DriveStraightDistance(1,1));
	}

}
