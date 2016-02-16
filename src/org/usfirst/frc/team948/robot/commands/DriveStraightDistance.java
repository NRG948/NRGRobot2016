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
	{
		requires(drive);
		this.power = power;
		this.distance = distance;
	}
	
	@Override
	protected void initialize() {
		drive.driveOnHeadingInit(power);
		heading = drive.getDesiredHeading();
		
		p = preferences.getDouble(PreferenceKeys.DRIVE_STRAIGHT_DISTANCE_P, 0.5);
		i = preferences.getDouble(PreferenceKeys.DRIVE_STRAIGHT_DISTANCE_I, 0.02);
		d = preferences.getDouble(PreferenceKeys.DRIVE_STRAIGHT_DISTANCE_D, 1.5);
		
		encoderPIDSource.reset();
		distancePIDOutput = 0.0;
		distancePID.reset();
		distancePID.setOutputRange(-Math.abs(power), Math.abs(power));
		distancePID.setAbsoluteTolerance(tolerance);
		distancePID.setSetpoint(distance);
		distancePID.setPID(p,i,d);
		distancePID.enable();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
//		SmartDashboard.putNumber("Distance PID OUTPUT", distancePIDOutput);
//		SmartDashboard.putNumber("Distance PID ERROR", distancePID.getError());
		double factor = MathHelper.clamp(distancePIDOutput, -1, 1);
		drive.driveOnHeading(heading, power*factor);
	}

	
	// Finishes the command if the target distance has been exceeded
	protected boolean isFinished() {
		if (distancePID.onTarget()) {
			cyclesOnTarget++;
		} else {
			cyclesOnTarget = 0;
		}
		
		return(cyclesOnTarget >= 3) || (distancePID.getError() < 0);
	}

	protected void end() {
		distancePID.reset();
		distancePIDOutput = 0.0;
//		drive.rawStop();
		drive.driveOnHeadingEnd();
	}

	protected void interrupted() {
		end();
	}
	
	public void pidWrite(double output) {
		distancePIDOutput = output;
	}
}