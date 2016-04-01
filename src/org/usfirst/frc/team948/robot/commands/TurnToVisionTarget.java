package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;

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
		System.out.println("Entering: Initalize() on TurnToVisionTarget"); 
		angle = visionProcessing.getTurningAngleProportion();
//		finalHeading = drive.getDesiredHeading() + angle;
		finalHeading = RobotMap.driveGyro.getAngle() + angle;
		drive.turnToHeadingInit(finalHeading, VISION_TOLERANCE, power);
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
