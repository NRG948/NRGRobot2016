package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.subsystems.ShooterWheel;

public class WaitForRPM extends CommandBase{

//	targetRPM and tolerance values
	private double targetRPM;
	private double tolerance;
	
	public WaitForRPM(double targetRPM, double tolerance) {
		this.targetRPM = targetRPM;
		this.tolerance = tolerance;
	}
	
	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		
	}

//	If the nonnegative difference between avg rpm and target rpm is less than or equal to tolerance, return true
	@Override
	protected boolean isFinished() {
		return Math.abs(shooterWheel.getAverageRPM(ShooterWheel.MAX_RPM_SAMPLES) - targetRPM) <= tolerance;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
	
	}
}
