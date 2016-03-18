package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToVisionTarget extends CommandBase {
	private static final double VISION_TOLERANCE = 1;
	private double finalHeading;
	private double angle;
	private double power;

	public TurnToVisionTarget(double power) {
		requires(drive);
		requires(visionProcessing);
		this.power = power;
	}

	protected void initialize() {
		finalHeading = drive.getDesiredHeading() + angle;
		drive.turnToHeadingInit(finalHeading, VISION_TOLERANCE, power);
		angle = visionProcessing.getTurningAngleProportion();
		SmartDashboard.putNumber("turn final heading", finalHeading);
	}

	protected void execute() {
		drive.turnToHeading(finalHeading, power);
	}

	protected boolean isFinished() {
		return angle == 0 || drive.turnToHeadingComplete(VISION_TOLERANCE);
	}

	protected void end() {
		drive.rawStop();
		drive.turnToHeadingEnd(finalHeading);
	}

	protected void interrupted() {
		end();
	}
}
