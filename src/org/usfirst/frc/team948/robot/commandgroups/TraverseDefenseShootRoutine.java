package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.Robot.AutoPosition;
import org.usfirst.frc.team948.robot.commands.Delay;
import org.usfirst.frc.team948.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.ResetSensors;
import org.usfirst.frc.team948.robot.commands.TurnAngle;
import org.usfirst.frc.team948.robot.commands.TurnToHeading;
import org.usfirst.frc.team948.robot.commands.TurnToTargetDumb;
import org.usfirst.frc.team948.robot.commands.TurnToVisionTargetContinuous;
import org.usfirst.frc.team948.robot.utilities.PositionTracker;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TraverseDefenseShootRoutine extends CommandGroup {
	// Positions 1-5(enum Robot.AutoPosition)
	// added .5 feet because robot sees two targets and now we are adding more to it. Added 2 to 11.5
	private static final double AUTO_LINE_TO_OPPONENT_ALIGNMENT_LINE_DISTANCE = 13.5; // 17.2
	private static final double TURN_TO_TARGET_POWER = 0.64;

	public TraverseDefenseShootRoutine(double power, Robot.AutoPosition position, Robot.Defense defense,
			boolean shoot) {
		// addSequential(new ResetSensors());
		// addSequential(new Delay(0.5));
		// addParallel(new RampToRPM(2000));
		if (power != Robot.NO_AUTO) {
			addSequential(new RaiseAcquirerDriveAndShoot(power, position, defense, shoot));
			addSequential(new Interrupt());
		}
	}

	private class RaiseAcquirerDriveAndShoot extends CommandGroup {
		public RaiseAcquirerDriveAndShoot(double power, Robot.AutoPosition position, Robot.Defense defense,
				boolean shoot) {
			addParallel(new RaiseAcquirerTo(defense.getAcquirerAngle()));
			addSequential(new DriveStraightDistance(power, position.getDistance(), 0.6));
			// addParallel(new RaiseAcquirerTo(0));
			// addSequential(new TurnToHeading(position.getAngle(),
			// TURN_TO_TARGET_POWER));
			if (shoot) {
				// addSequential(new LowerAcquirerAndTurn(position));
				addParallel(new RaiseAcquirerTo(0));
				addSequential(new TurnToTargetDumb(position.getAngle(), TURN_TO_TARGET_POWER));
				addSequential(new DriveStraightDistance(power, position.getSecondDistance(), 0.6));
				if (position.getSecondAngle() != Robot.NO_TURN) {
					addSequential(new TurnToTargetDumb(position.getSecondAngle(), TURN_TO_TARGET_POWER));
				}
				addSequential(new ShootSequence(true, true));
			}
		}
	}

	private class LowerAcquirerAndTurn extends CommandGroup {
		public LowerAcquirerAndTurn(Robot.AutoPosition position) {
			addParallel(new RaiseAcquirerTo(0));
			addSequential(new TurnToTargetDumb(position.getAngle(), TURN_TO_TARGET_POWER));
		}
	}
}