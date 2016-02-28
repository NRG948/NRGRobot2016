package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.Delay;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.RaiseShooterArmTo;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.Shoot;
import org.usfirst.frc.team948.robot.commands.ShooterRampUp;
import org.usfirst.frc.team948.robot.commands.SwitchCamera;
import org.usfirst.frc.team948.robot.commands.TurnAngle;
import org.usfirst.frc.team948.robot.commands.TurnToTarget;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootSequence extends CommandGroup {
	public ShootSequence()
	{
		addSequential(new SwitchCamera());
		addSequential(new Delay(1));
		addParallel(new RampToRPM(2000));
		addSequential(new TurnAndRaise());
		addSequential(new Interrupt());
		addSequential(new SwitchCamera());
	}
	
	private class TurnAndRaise extends CommandGroup{
		public TurnAndRaise(){
		addSequential(new TurnAngle(0.6));
		addSequential(new RaiseShooterArmTo());
		addSequential(new Shoot(0));
		addSequential(new RaiseShooterArmTo(0));
		}
	}
}
