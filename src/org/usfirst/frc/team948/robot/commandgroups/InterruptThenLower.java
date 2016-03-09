package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.LowerShooterToNextLowerAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class InterruptThenLower extends CommandGroup{
	public InterruptThenLower(){
		addSequential(new Interrupt(Robot.shooterArm));
		addSequential(new LowerShooterToNextLowerAngle());
	}
}
