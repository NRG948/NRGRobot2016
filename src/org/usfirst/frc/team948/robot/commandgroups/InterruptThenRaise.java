package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.RaiseShooterToNextHigherAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class InterruptThenRaise extends CommandGroup{
	public InterruptThenRaise(){
		addSequential(new Interrupt(Robot.shooterArm));
		addSequential(new RaiseShooterToNextHigherAngle());
	}
}
