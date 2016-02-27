package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.CommandBase;

public class MoveDrawbridgeToEnd extends CommandBase{

	private boolean up;
	private final double POWER = 0.5;
	
	@Override
	protected void initialize() {
		up = drawbridge.hasReachedLowerLimit();
	}

	@Override
	protected void execute() {
		if (up) {
			drawbridge.rawRaise(POWER);
		}
		else {
			drawbridge.rawRaise(-POWER);
		}
	}

	@Override
	protected boolean isFinished() {
		if (up) {
			return drawbridge.hasReachedUpperLimit();
		}
		else {
			return drawbridge.hasReachedLowerLimit();
		}
	}

	@Override
	protected void end() {
		drawbridge.stopArm();
	}

	@Override
	protected void interrupted() {
		end();
	}

}
