package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PullSallyPort extends CommandGroup{
	
	public PullSallyPort() {
//		Raise Acquirer to SallyPort height
		addSequential(new RaiseAcquirerTo(Robot.Level.SALLY_PORT_HIGH));
//		Go back with sally port acquired
		addSequential(new DriveStraightDistance(-0.5, 2));
//		Go back even faster
		addSequential(new DriveStraightDistance(-1, 1));
//		At the same time, raise acquirer even higher in order to let go
		addParallel(new RaiseAcquirerTo(Robot.Level.FULL_BACK));
//		Go forward with sally port opened
		addSequential(new DriveStraightDistance(1, 5));
		
//		ALL VALUES NEED TO BE CHECKED
	}
}
