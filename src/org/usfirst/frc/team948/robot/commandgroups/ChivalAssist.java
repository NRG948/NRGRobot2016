package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ChivalAssist extends CommandGroup{
	public ChivalAssist() {
		addParallel(new RaiseAcquirerTo(Robot.Level.FULL_BACK), 1.5);
		addSequential(new DriveStraightDistance(0.8, 3.0), 1.5);
	}
}