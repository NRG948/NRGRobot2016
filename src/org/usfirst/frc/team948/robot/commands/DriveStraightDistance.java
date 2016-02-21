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
	double distancePIDOutput;
	public EncoderPidSource encoderPIDSource = new EncoderPidSource();
	private PIDController distancePID = new PIDController(p, i, d, encoderPIDSource, (PIDOutput) this);
	private int cyclesOnTarget;

	public DriveStraightDistance(double power, double distance)
	{	requires(drive);
		this.power = power;
		this.distance = distance;
	}
	
	@Override
	protected void initialize() {
		drive.driveOnHeadingInit(power);
		heading = drive.getDesiredHeading();
		
		p = preferences.getDouble(PreferenceKeys.DRIVE_STRAIGHT_DISTANCE_P, 0.5);
		i = preferences.getDouble(PreferenceKeys.DRIVE_STRAIGHT_DISTANCE_I, 0.001);
		d = preferences.getDouble(PreferenceKeys.DRIVE_STRAIGHT_DISTANCE_D, 1.5);
		tolerance = 1/12;
		encoderPIDSource.reset();
		distancePIDOutput = 0.0;
		distancePID.reset();
		distancePID.setOutputRange(-1, 1);
		distancePID.setAbsoluteTolerance(tolerance);
		distancePID.setSetpoint(distance);
		distancePID.setPID(p,i,d);
		distancePID.enable();
	}

	@Override
	protected void execute() {
//		SmartDashboard.putNumber("Distance PID OUTPUT", distancePIDOutput);
//		SmartDashboard.putNumber("Distance PID ERROR", distancePID.getError());
		double factor = MathHelper.clamp(distancePIDOutput, -1, 1);
		SmartDashboard.putNumber("Drive Distance Error", distancePID.getError());
		drive.driveOnHeading(-power*factor, heading);
	}

	
	// Finishes the command if the target distance has been exceeded
	protected boolean isFinished() {
		if (distancePID.getError() < tolerance) {
			cyclesOnTarget++;
		} else {
			cyclesOnTarget = 0;
		}
		SmartDashboard.putNumber("Cycles On Target", cyclesOnTarget);
		return(cyclesOnTarget >= 6);
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