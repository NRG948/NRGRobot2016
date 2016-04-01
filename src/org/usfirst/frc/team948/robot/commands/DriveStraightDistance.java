package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.utilities.EncoderPidSource;
import org.usfirst.frc.team948.robot.utilities.MathHelper;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraightDistance extends CommandBase implements PIDOutput{
	public double distance;
	public double power;
	private double heading;
	private double tolerance;
	
	private double p, i, d;
	volatile double distancePIDOutput;
	public EncoderPidSource encoderPIDSource = new EncoderPidSource();
	private PIDController distancePID = new PIDController(p, i, d, encoderPIDSource, (PIDOutput) this);
	private int cyclesOnTarget;

	public DriveStraightDistance(double power, double distance, double tolerance){
		requires(drive);
		this.power = power;
		this.distance = distance;
		this.tolerance = tolerance;
	}
	
	public DriveStraightDistance(double power, double distance)
	{	
		this(power, distance, 2.0/12);
	}

	
	@Override
	protected void initialize() {
		drive.driveOnHeadingInit(power);
		heading = drive.getDesiredHeading();
		
		p = preferences.getDouble(PreferenceKeys.DRIVE_STRAIGHT_DISTANCE_P, 0.5);
		i = preferences.getDouble(PreferenceKeys.DRIVE_STRAIGHT_DISTANCE_I, 0.01);
		d = preferences.getDouble(PreferenceKeys.DRIVE_STRAIGHT_DISTANCE_D, 1.5);
		encoderPIDSource.reset();
		distancePIDOutput = 0.0;
		distancePID.reset();
		distancePID.setOutputRange(-1, 1);
		distancePID.setAbsoluteTolerance(tolerance);
		distancePID.setSetpoint(distance);
		distancePID.setToleranceBuffer(6);
		distancePID.setPID(p,i,d);
		distancePID.enable();
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("Distance PID OUTPUT", distancePIDOutput);
		double factor = MathHelper.clamp(distancePIDOutput, -1, 1);
		double revisedPower = power * factor;
		double error = distancePID.getError();
		SmartDashboard.putNumber("Distance PID ERROR", error);
		if (Math.abs(error) < tolerance) {
			revisedPower = 0.0;
		} else if (Math.abs(error) < 1.0) {
			revisedPower = 0.3 * Math.signum(error);
		}
		SmartDashboard.putNumber("Revised Power DriveStraightDistance", revisedPower);
		drive.driveOnHeading(revisedPower, heading);
	}

	
	// Finishes the command if the target distance has been exceeded
	protected boolean isFinished() {
		if (Math.abs(distancePID.getError()) < tolerance) {
			cyclesOnTarget++;
		} else {
			cyclesOnTarget = 0;
		}
		SmartDashboard.putNumber("Cycles On Target", cyclesOnTarget);
		return(cyclesOnTarget >= 6);
//		return distancePID.getError() < tolerance;
	}

	protected void end() {
		distancePID.reset();
		distancePIDOutput = 0.0;
		drive.rawStop();
		drive.driveOnHeadingEnd();
	}

	protected void interrupted() {
		end();
	}
	
	public void pidWrite(double output) {
		distancePIDOutput = output;
	}
	
}