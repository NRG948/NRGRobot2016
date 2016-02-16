package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.ManualAcquire;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AcquireMode extends CommandGroup {

	public AcquireMode(){
		addSequential(new RaiseAcquirerTo(Robot.Level.DEFAULT));
		addSequential(new ManualAcquire(false));
	}
}
