package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;

public class TurnToTarget extends CommandBase implements PIDOutput {
	public PIDController targetPID;
	public double pidOutput;
	private Timer timer = new Timer();

	public TurnToTarget() {
		requires(drive);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		targetPID = new PIDController(preferences.getDouble(PreferenceKeys.VISION_P, 0.002), 
									  preferences.getDouble(PreferenceKeys.VISION_I, 0.000125), 
									  preferences.getDouble(PreferenceKeys.VISION_D, 0.002),
								      Robot.visionProcessing, this);
		targetPID.reset();
		targetPID.setSetpoint(0);
		targetPID.setOutputRange(-0.6, 0.6);
		targetPID.setAbsoluteTolerance(5);
		targetPID.enable();
		timer.reset();
		timer.start();
	}

	@Override
	protected void execute() {
		double power = pidOutput;
		drive.rawTankDrive(power, -power);
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
//		return targetPID.onTarget();
		return timer.get() >= 12;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		drive.rawStop();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

	@Override
	public void pidWrite(double arg0) {
		// TODO Auto-generated method stub
		pidOutput = arg0;

	}

}
