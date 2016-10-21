package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.Delay;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.RaiseShooterArmTo;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.Shoot;
import org.usfirst.frc.team948.robot.commands.TurnToVisionTarget;
import org.usfirst.frc.team948.robot.commands.WaitForRPM;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootSequence extends CommandGroup {
	public ShootSequence(boolean ramp, boolean doubleTurn) {
		// addSequential(new SwitchCamera());
		// addSequential(new Delay(1));
		if (ramp) {
			// addParallel(new RampToRPM(2000));
		}
		addSequential(new TurnAndRaise(doubleTurn));
		if (ramp) {
			addSequential(new Interrupt());
		}
		// addSequential(new SwitchCamera());
	}

	private class TurnAndRaise extends CommandGroup {
		public TurnAndRaise(boolean doubleTurn) {
			// addSequential(new Delay(1));
			addSequential(new TurnToVisionTarget(0.6));
			if (doubleTurn) {
				addSequential(new TurnToVisionTarget(0.6));
			}
			addParallel(new RampToRPM(2250, 0.1)); // previously 2000 rpm
			// addParallel(new RampToRPM(3000, 0.1));
			addParallel(new RaiseShooterArmTo());
			addSequential(new WaitForRPM(2250, 20)); // previously 2000 rpm
			// addSequential(new WaitForRPM(3000, 20));
			addSequential(new Shoot(0, true));
			addSequential(new RaiseShooterArmTo(-10), 0.75);
		}
	}

	@Override
	protected void initialize() {
		super.initialize();

		RobotMap.shooterBallPusher.set(0); // this is a hack (bar is rotating prematurely)
		System.out.println("power to shooterbar killed");

		System.out.println("Entering: initalize() Shoot Sequence");
	}

}
