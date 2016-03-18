package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnAngle extends CommandBase {
	private static final double DEFAULT_TOLERANCE = 2.0;
	private double finalHeading;
	private double angle;
	private double power;
	private double tolerance;

	public TurnAngle(double angle, double power, double tolerance) {
		requires(drive);
		this.angle = angle;
		this.power = power;
		this.tolerance = tolerance;
	}

	public TurnAngle(double angle, double power) {
		this(angle, power, DEFAULT_TOLERANCE);
	}

	protected void initialize() {
		finalHeading = drive.getDesiredHeading() + angle;
		drive.turnToHeadingInit(finalHeading, tolerance, power);
//		finalHeading = initialHeading + angle;
//		drive.drivePID.setSetpoint(finalHeading);
//		drive.drivePID.enable();
		SmartDashboard.putNumber("turn final heading", finalHeading);
	}

	protected void execute() {
		drive.turnToHeading(finalHeading, power);
	}

	protected boolean isFinished() {
		return angle == 0 || drive.turnToHeadingComplete(tolerance);
	}

	protected void end() {
		drive.rawStop();
		drive.turnToHeadingEnd(finalHeading);
	}

	protected void interrupted() {
		end();
	}
}
