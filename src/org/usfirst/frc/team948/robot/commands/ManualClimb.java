package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;

public class ManualClimb extends CommandBase{

	private double EXTEND_WINCH_POWER, EXTEND_TAPEMEAUSRE_POWER, WIND_WINCH_POWER, WIND_TAPEMEASURE_POWER;
	private boolean extend;

	public ManualClimb(boolean extend) {
		requires(climber);
		this.extend = extend;
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		if(extend) {
			RobotMap.climberTapeMeasure.set(EXTEND_TAPEMEAUSRE_POWER);
			RobotMap.climberWinch.set(EXTEND_WINCH_POWER);
		} else {
			RobotMap.climberTapeMeasure.set(WIND_TAPEMEASURE_POWER);
			RobotMap.climberWinch.set(WIND_WINCH_POWER);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		end();
	}
}
