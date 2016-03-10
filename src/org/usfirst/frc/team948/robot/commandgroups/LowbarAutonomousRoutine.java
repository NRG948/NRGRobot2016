package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.TurnAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowbarAutonomousRoutine extends CommandGroup {
	private static final double AUTO_LINE_TO_OPPONENT_ALIGNMENT_LINE_DISTANCE = 17.22;
	private static final double AUTO_LINE_TO_OPPONENT_ALIGNMENT_LINE_POWER = 0.7;
	private static final double TURN_TO_TARGET_ANGLE = -58.69;
	private static final double TURN_TO_TARGET_POWER = 0.7;

	
	public LowbarAutonomousRoutine()
	{
		addParallel(new RampToRPM(2000));
		addSequential(new DriveTurnAndShoot());
	}
	private class DriveTurnAndShoot extends CommandGroup{
		public DriveTurnAndShoot(){
			addSequential(new DriveStraightDistance(AUTO_LINE_TO_OPPONENT_ALIGNMENT_LINE_POWER, AUTO_LINE_TO_OPPONENT_ALIGNMENT_LINE_DISTANCE));
			addSequential(new TurnAngle(TURN_TO_TARGET_ANGLE, TURN_TO_TARGET_POWER));
			addSequential(new ShootSequence(false));
		}
	}
}
