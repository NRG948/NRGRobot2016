package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.RaiseShooterArmTo;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarMode extends CommandGroup{
	
	public LowBarMode() {
		addParallel(new RaiseAcquirerTo(Robot.Level.PORTCULLIS_LOW));
		addParallel(new RaiseShooterArmTo(0));
		
		//ALL VALUES NEED TO BE TESTED
	}
	
}
