package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.TurnAngle;
import org.usfirst.frc.team948.robot.commands.TurnToTargetDumb;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoBallAutonomous extends CommandGroup{
	private static final double FIRST_DRIVE_DISTANCE = 5.0;
	private static final double TURN_TO_FIRST_TARGET_ANGLE = 55.0;
	private static final double SECOND_TURN_HEADING = 200.0;
	private static final double SECOND_DRIVE_DISTANCE = 13.0;

    public TwoBallAutonomous(){
//		addParallel(new RampToRPM(2000));
    	addParallel(new RaiseAcquirerTo(0));
		addSequential(new DriveStraightDistance(0.8, FIRST_DRIVE_DISTANCE));
		addSequential(new TurnToTargetDumb(TURN_TO_FIRST_TARGET_ANGLE, 0.65));
		addSequential(new ShootSequence(true, true));
		addSequential(new TurnToTargetDumb(SECOND_TURN_HEADING, 0.65));
		addSequential(new DriveStraightDistance(0.8, SECOND_DRIVE_DISTANCE));
	}
}
