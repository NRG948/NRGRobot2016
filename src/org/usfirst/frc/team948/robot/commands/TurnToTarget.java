package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

public class TurnToTarget extends CommandBase {

	public TurnToTarget() {
		requires(drive);
	}

	@Override
	protected void initialize() {
		visionProcessing.turnToTargetInit();
		visionProcessing.setImageCenter(CommandBase.preferences.getDouble(
				PreferenceKeys.CENTER_IMAGE, 160.0));
		if (!visionProcessing.getMode()) {
			visionProcessing.setToVisionCamera();
		}
	}

	@Override
	protected void execute() {
		visionProcessing.turnToTarget();
	}

	@Override
	protected boolean isFinished() {
		return visionProcessing.turnToTargetFinished();
	}

	@Override
	protected void end() {
		visionProcessing.turnToTargetEnd();
	}

	@Override
	protected void interrupted() {
		end();
	}
}