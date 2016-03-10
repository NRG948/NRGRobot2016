package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.commands.Delay;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.RaiseShooterArmTo;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.Shoot;
import org.usfirst.frc.team948.robot.commands.TurnToVisionTarget;
import org.usfirst.frc.team948.robot.commands.WaitForRPM;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootSequence extends CommandGroup {
	public ShootSequence(boolean ramp)
	{
//		addSequential(new SwitchCamera());
//		addSequential(new Delay(1));
		if(ramp){
			addParallel(new RampToRPM(2000));
		}
		addSequential(new TurnAndRaise());
		if(ramp){
			addSequential(new Interrupt());
		}
//		addSequential(new SwitchCamera());
	}
	
	private class TurnAndRaise extends CommandGroup{
		public TurnAndRaise(){
		addSequential(new Delay(1));
		addSequential(new TurnToVisionTarget(0.6));
		addParallel(new RaiseShooterArmTo());
		addSequential(new WaitForRPM(2000, 20));
		addSequential(new Shoot(0));
		addSequential(new RaiseShooterArmTo(-10));
		}
	}
}
