package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.RaiseShooterArmTo;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.Shoot;
import org.usfirst.frc.team948.robot.commands.ShooterRampUp;
import org.usfirst.frc.team948.robot.commands.TurnToTarget;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootSequence extends CommandGroup {
	public ShootSequence()
	{
		addSequential(new TurnToTarget());
		addParallel(new RaiseAcquirerTo(10));
		addParallel(new RaiseShooterArmTo());
		addParallel(new RampToRPM(2700));
//		addParallel(new ShooterRampUp(1));
		addSequential(new Shoot(10));
		addSequential(new Interrupt());
	}
}
