package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.SpitOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SpitOutSequence extends CommandGroup{
	
	public SpitOutSequence() {
		addSequential(new RaiseAcquirerTo(Robot.Level.DEFAULT));
		addSequential(new SpitOut());
	}
}
