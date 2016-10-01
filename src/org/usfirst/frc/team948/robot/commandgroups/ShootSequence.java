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
	public ShootSequence(boolean ramp, boolean doubleTurn)
	{
//		addSequential(new SwitchCamera());
//		addSequential(new Delay(1));
		if(ramp){
//			addParallel(new RampToRPM(2000));
		}
		addSequential(new TurnAndRaise(doubleTurn));
		if(ramp){
			addSequential(new Interrupt());
		}
//		addSequential(new SwitchCamera());
	}
	
	private class TurnAndRaise extends CommandGroup{
		public TurnAndRaise(boolean doubleTurn){
	//	addSequential(new Delay(1));
		addSequential(new TurnToVisionTarget(0.6));
		if(doubleTurn){
			addSequential(new TurnToVisionTarget(0.6));
		}
		addParallel(new RampToRPM(2000, 0.1));
//		addParallel(new RampToRPM(3000, 0.1));
		addParallel(new RaiseShooterArmTo());
		addSequential(new WaitForRPM(2000, 20));
//		addSequential(new WaitForRPM(3000, 20));
		addSequential(new Shoot(0, true));
		addSequential(new RaiseShooterArmTo(-10), 0.75);
		}
	}
	@Override
	protected void initialize(){
		super.initialize();
		System.out.println("Entering: initalize() Shoot Sequence");
	}
	
}
