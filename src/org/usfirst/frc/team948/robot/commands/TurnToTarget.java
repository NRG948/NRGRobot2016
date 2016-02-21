package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToTarget extends CommandBase implements PIDOutput {
	public PIDController targetPID;
	public double pidOutput;
	private int cyclesOnTarget;
	private Timer timer = new Timer();

	public TurnToTarget() {
		requires(drive);
	}

	@Override
	protected void initialize() {
		targetPID = new PIDController(preferences.getDouble(PreferenceKeys.VISION_P, 0.0039), 
									  preferences.getDouble(PreferenceKeys.VISION_I, 0.00031), 
									  preferences.getDouble(PreferenceKeys.VISION_D, 0.0123),
								      Robot.visionProcessing, this);
		targetPID.reset();
		targetPID.setSetpoint(CommandBase.preferences.getDouble(PreferenceKeys.CENTER_IMAGE, 160.0));
		targetPID.setOutputRange(-0.5, 0.5);
		targetPID.setAbsoluteTolerance(5);
//		targetPID.setPercentTolerance(1.0);
		targetPID.setToleranceBuffer(3);
		targetPID.enable();
//		timer.reset();
//		timer.start();
	}

	@Override
	protected void execute() {
		double power = pidOutput;
		SmartDashboard.putNumber("TurnToTarget error", targetPID.getError());
		SmartDashboard.putNumber("TurnToTarget pidOutput", pidOutput);
		SmartDashboard.putNumber("Center X", Robot.visionProcessing.centerX);
		drive.rawTankDrive(power, -power);
	}

	@Override
	protected boolean isFinished() {
		SmartDashboard.putNumber("Turn to Target finish Error", targetPID.getAvgError());
		if (Math.abs(targetPID.getError()) < 5) { //getAvgError() doesn't work
			cyclesOnTarget++;
		}
		else {
			cyclesOnTarget = 0;
		}
		return cyclesOnTarget >= 3;
//		return targetPID.onTarget(); onTarget() is bugged - command never ends
//		return timer.get() >= 12;
	}

	@Override
	protected void end() {
		drive.rawStop();
		targetPID.disable();
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	public void pidWrite(double arg0) {
		pidOutput = arg0;
	}

}
