package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.utilities.MathHelper;
import org.usfirst.frc.team948.robot.utilities.PositionTracker;

public class DriveStraightFeet extends CommandBase{
	public double feet;
	public double power;
	public double startN, startE;
	public double angle;
	public double distanceSoFar;
	public static final double SLOWING_DOWN_DISTANCE = 1.0;

	public DriveStraightFeet(double power, double feet) {
		requires(drive);
		this.power = power;
		this.feet = feet;
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		// We take the absolute values, since we just need the total distance
		// traveled if the robot is going backwards
		double currentN = PositionTracker.currentPN;
		double currentE = PositionTracker.currentPE;
		distanceSoFar = Math.sqrt((currentN - startN) * (currentN - startN)
				+ (currentE - startE) * (currentE - startE));
		double feetRemaining = feet - distanceSoFar;
		// This ramps the power down to zero when we reach the target distance
		double powerMod = MathHelper.clamp(power * feetRemaining
				/ SLOWING_DOWN_DISTANCE, -Math.abs(power), Math.abs(power));
		drive.rawTankDrive(powerMod, powerMod);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		startN = PositionTracker.currentPN;
		startE = PositionTracker.currentPE;
		angle = RobotMap.driveGyro.getAngle();

	}

	// Finishes the command if the target distance has been exceeded
	protected boolean isFinished() {
		return (distanceSoFar >= feet);
	}

	protected void end() {
		drive.rawStop();
	}

	protected void interrupted() {
		end();
	}
}
