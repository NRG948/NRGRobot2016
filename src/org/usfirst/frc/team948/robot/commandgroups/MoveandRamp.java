package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.commands.RaiseShooterToNextHigherAngle;
import org.usfirst.frc.team948.robot.commands.RaiseShooterToNextLowerAngle;
import org.usfirst.frc.team948.robot.commands.RampToRPM;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MoveandRamp extends CommandGroup{
	
	public MoveandRamp(boolean up , double RPM)
	{
		if (up == true)
		{
			addParallel(new RaiseShooterToNextHigherAngle());
		}
		else
		{
			addParallel(new RaiseShooterToNextLowerAngle());
		}
		addParallel(new RampToRPM(RPM));
	}
	

}
