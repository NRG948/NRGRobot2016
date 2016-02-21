package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.Delay;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.SpitOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SpitOutSequence extends CommandGroup{
	
	public SpitOutSequence() {
		addParallel(new RaiseAcquirerTo(Robot.Level.FULL_BACK));
		addSequential(new Delay(0.75));
		addSequential(new SpitOut());
		addSequential(new Interrupt(CommandBase.shooterWheel, CommandBase.acquirerArm, CommandBase.acquirerArm, CommandBase.shooterBar));
	}
}
