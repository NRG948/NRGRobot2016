package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.RaiseShooterArmTo;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.Shoot;
import org.usfirst.frc.team948.robot.commands.TurnToTarget;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootSequence extends CommandGroup {
	public ShootSequence()
	{
		addSequential(new TurnToTarget());
		addParallel(new RaiseShooterArmTo());
		addSequential(new RampToRPM(3300));
		addSequential(new Shoot());
		addSequential(new Interrupt(CommandBase.shooterArm));
	}
}
