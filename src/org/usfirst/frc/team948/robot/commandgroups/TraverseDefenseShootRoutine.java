package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.Robot.AutoPosition;
import org.usfirst.frc.team948.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.TurnAngle;
import org.usfirst.frc.team948.robot.commands.TurnToVisionTargetContinuous;
import org.usfirst.frc.team948.robot.utilities.PositionTracker;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TraverseDefenseShootRoutine extends CommandGroup {
	// Positions 1-5(enum Robot.AutoPosition)
	private static final double AUTO_LINE_TO_OPPONENT_ALIGNMENT_LINE_DISTANCE = 12;
	private static final double TURN_TO_TARGET_POWER = 0.7;

	public TraverseDefenseShootRoutine(Robot.AutoPosition position, Robot.Defense defense) {
		addParallel(new RaiseAcquirerTo(defense.getAcquirerAngle()));
		addSequential(new DriveStraightDistance(TURN_TO_TARGET_POWER,
				AUTO_LINE_TO_OPPONENT_ALIGNMENT_LINE_DISTANCE, 0.6));
		addParallel(new RaiseAcquirerTo(0));
		addSequential(new TurnAngle(position.getAngle(), TURN_TO_TARGET_POWER));
	//	addSequential(new TurnToTarget());
		addSequential(new ShootSequence());
	}
}
