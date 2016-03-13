package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.Robot.AutoPosition;
import org.usfirst.frc.team948.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.ResetSensors;
import org.usfirst.frc.team948.robot.commands.TurnAngle;
import org.usfirst.frc.team948.robot.commands.TurnToHeading;
import org.usfirst.frc.team948.robot.commands.TurnToVisionTargetContinuous;
import org.usfirst.frc.team948.robot.utilities.PositionTracker;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TraverseDefenseShootRoutine extends CommandGroup {
	// Positions 1-5(enum Robot.AutoPosition)
	private static final double AUTO_LINE_TO_OPPONENT_ALIGNMENT_LINE_DISTANCE = 11; //17.2
	private static final double TURN_TO_TARGET_POWER = 0.64;

	public TraverseDefenseShootRoutine(Robot.AutoPosition position, Robot.Defense defense) {
//		addSequential(new ResetSensors());
		addParallel(new RampToRPM(2000));
		addSequential(new RaiseAcquirerDriveAndShoot(position, defense));
		addSequential(new Interrupt());
	}
	private class RaiseAcquirerDriveAndShoot extends CommandGroup{
		public RaiseAcquirerDriveAndShoot(Robot.AutoPosition position, Robot.Defense defense){
			addParallel(new RaiseAcquirerTo(defense.getAcquirerAngle()));
			addSequential(new DriveStraightDistance(defense.getPower(),
					position.getDistance(), 0.6));
//			addParallel(new RaiseAcquirerTo(0));
//			addSequential(new TurnToHeading(position.getAngle(), TURN_TO_TARGET_POWER));
			addSequential(new LowerAcquirerAndTurn(position));
			addSequential(new DriveStraightDistance(Math.signum(position.getSecondDistance()) * defense.getPower(),
					Math.abs(position.getSecondDistance()), 0.6));
			addSequential(new TurnToHeading(position.getSecondAngle(), TURN_TO_TARGET_POWER, 5));
			addSequential(new ShootSequence(false));
		}
	}
	
	private class LowerAcquirerAndTurn extends CommandGroup {
		public LowerAcquirerAndTurn(Robot.AutoPosition position) {
			addParallel(new RaiseAcquirerTo(0));
			addSequential(new TurnToHeading(position.getAngle(), TURN_TO_TARGET_POWER, 5));
		}
	}
}
