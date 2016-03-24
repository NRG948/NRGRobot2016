package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ConstantRampDown extends CommandBase {

	// private final double TURN_TARGET_P = 0.0039;
	private static final double P_CONSTANT = 1;
	private double targetAngle;
	private static double error;

	public ConstantRampDown(double targetAngle) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.targetAngle = targetAngle;
		requires(shooterArm);
	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double currentAngle = shooterArm.degreesFromVolts(RobotMap.shooterLifterEncoder.getVoltage());
		error = targetAngle - currentAngle;
		shooterArm.rawRaiseShooter(P_CONSTANT * error);

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return error == 0;
	}

	// Called once after isFinished returns true
	protected void end() {
		shooterArm.rawRaiseShooter(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
