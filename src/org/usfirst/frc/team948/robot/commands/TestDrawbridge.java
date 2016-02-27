package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.DS2016;

public class TestDrawbridge extends CommandBase {
	public TestDrawbridge(double power) {
		requires(CommandBase.drawbridge);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		drawbridge.rawRaise(DS2016.xBoxController.getRawAxis(0));
	}

	@Override
	protected boolean isFinished() {
		return false;
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
