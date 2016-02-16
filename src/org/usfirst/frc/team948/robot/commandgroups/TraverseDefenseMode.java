package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.RaiseShooterArmTo;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TraverseDefenseMode extends CommandGroup  {

	public TraverseDefenseMode() {
		addSequential(new RaiseAcquirerTo(Robot.ShooterAngle.FULL_BACK_START));
		addSequential(new RaiseShooterArmTo(5));//This value might need to be changed later
	}

	
}
