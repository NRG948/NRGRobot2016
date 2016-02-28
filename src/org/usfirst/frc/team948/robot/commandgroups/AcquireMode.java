package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.ManualAcquire;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.RaiseShooterArmTo;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AcquireMode extends CommandGroup {

	public AcquireMode(){
		addParallel(new RaiseShooterArmTo(-10));
//		addParallel(new RaiseAcquirerTo(Robot.Level.ACQUIRE));
		addSequential(new ManualAcquire(false));
		addSequential(new Interrupt(CommandBase.acquirerArm));
	}
}
