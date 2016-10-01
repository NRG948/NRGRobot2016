package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.TurnAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoBallAutonomous extends CommandGroup{
	private static int FIRST_DRIVE_DISTANCE = 2;
	private static int TURN_TO_FIRST_TARGET_ANGLE = 80;

    public TwoBallAutonomous(){
		addParallel(new RampToRPM(2000));
		addSequential(new DriveStraightDistance(0.8, FIRST_DRIVE_DISTANCE));
		addSequential(new TurnAngle(TURN_TO_FIRST_TARGET_ANGLE, 0.8));
		addSequential(new ShootSequence(true, true));
	}
}
