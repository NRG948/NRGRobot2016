package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class TurnToTarget extends CommandBase implements PIDOutput {
	public PIDController targetPID;
	public double pidOutput;

	public TurnToTarget() {
		requires(drive);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		targetPID = new PIDController(0.003, 0.003 * 2 * 0.05, 0.0015,Robot.visionProcessing, this);
		targetPID.reset();
		targetPID.setSetpoint(0);
		targetPID.setOutputRange(-0.6, 0.6);
		targetPID.setAbsoluteTolerance(5);
		targetPID.enable();
	}

	@Override
	protected void execute() {
		double power = pidOutput;
		drive.rawTankDrive(-power, power);
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return targetPID.onTarget();
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
