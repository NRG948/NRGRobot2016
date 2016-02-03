package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Acquirer extends Subsystem implements PIDOutput {

	public PIDController armAnglePID = new PIDController(0.1, 0.01, 0.005, RobotMap.armAngleEncoder, this);
	private double pidOutput;
	private final double ANGLE_TO_VOLTS = 0.01389;
	private final double TOLERANCE = 1.0 * ANGLE_TO_VOLTS;
    private final double ACQUIRER_SPEED = 0.5;


public Acquirer() {
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

	

	public void setDesiredArmAngle(double angle) {
		armAnglePID.reset();
		armAnglePID.setSetpoint(angle * ANGLE_TO_VOLTS);
		armAnglePID.setAbsoluteTolerance(TOLERANCE);
		pidOutput = 0;
		armAnglePID.enable();
	}

	public void moveArmToDesiredAngle() {
		RobotMap.acquireArmVictor.set(pidOutput);
	}

	public boolean isArmAtDesiredAngle() {
		return armAnglePID.onTarget();
	}

	public void stopArm() {
		armAnglePID.reset();
		RobotMap.acquireArmVictor.disable();
	}
	public void stopAcquirer() {
		RobotMap.acquireArmVictor.disable();
		RobotMap.acquireWheelVictor.disable();;
	}
	
	public void rawAcquire(double power) {
		RobotMap.acquireWheelVictor.set(power);
	}
	
	@Override
	public void pidWrite(double arg0) {
		pidOutput = arg0;
	}
	
	public void rawRaise(double power) {
		RobotMap.acquireArmVictor.set(power);
	}

}
