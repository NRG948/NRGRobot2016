package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.RaiseShooterToNextHigherAngle;
import org.usfirst.frc.team948.robot.commands.RaiseShooterToNextLowerAngle;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.ShooterRampUp;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MoveandRamp extends CommandGroup{
	
	public MoveandRamp(boolean up , double RPM)
	{
		addParallel(new RaiseAcquirerTo(Robot.Level.DEFAULT));
		if (up)
		{
			addParallel(new RaiseShooterToNextHigherAngle());
		//	addSequential(new ShooterRampUp());
		}
		else
		{
			addParallel(new RaiseShooterToNextLowerAngle());
		}
		addSequential(new RampToRPM(RPM));
	}
	

}
