package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.Delay;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.RaiseShooterArmTo;
import org.usfirst.frc.team948.robot.commands.SpitOut;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SpitOutSequence extends CommandGroup{
	
	public SpitOutSequence() {
		addParallel(new RaiseAcquirerTo(Robot.Level.FULL_BACK));
		addParallel(new RaiseShooterArmTo(-12.5, CommandBase.shooterArm.TOLERANCE*2));
		addSequential(new SpitOut());
		addSequential(new Interrupt(CommandBase.shooterWheel, CommandBase.acquirerArm, CommandBase.acquirerArm, CommandBase.shooterBar, CommandBase.shooterArm));
	}
}
