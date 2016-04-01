package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.DS2016;
import org.usfirst.frc.team948.robot.RobotMap;

public class ManualClimb extends CommandBase{

	private boolean extend;
	private double winchPower;
	private double tapeMeasurePower;

	public ManualClimb() {
		requires(climber);
	}
	
	@Override
	protected void initialize() {
		winchPower = 0;
		tapeMeasurePower = 0;
	}

	@Override
	protected void execute() {
		if(DS2016.getLeftXboxY() > 0.9) {
			winchPower = -1.0;
		} else if (DS2016.getLeftXboxY() > 0.1) {
			winchPower = -0.5;
		} else if (DS2016.getLeftXboxY() < -0.9){
			winchPower = 1.0;
		} else if (DS2016.getLeftXboxY() < -0.1) {
			winchPower = 0.5;
		} else {
			winchPower = 0.0;
		}
		
		if(Math.abs(DS2016.getRightXboxY()) < 0.1) {
			tapeMeasurePower = 0.0;
		} else {
			tapeMeasurePower = DS2016.getRightXboxY();
		}
		climber.rawClimb(winchPower, tapeMeasurePower);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		climber.rawStop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
