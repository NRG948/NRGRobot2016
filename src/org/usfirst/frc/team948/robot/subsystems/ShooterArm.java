package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterArm extends Subsystem implements PIDOutput{
	public PIDController armAnglePID = new PIDController(0.1, 0.01, 0.005, RobotMap.shooterLifterEncoder, this);
	private double pidOutput;
	private final double ANGLE_TO_VOLTS = 0.01389;
	private final double TOLERANCE = 1.0 * ANGLE_TO_VOLTS;
	public ShooterArm() {
	}
	
	
	@Override
	protected void initDefaultCommand() {
	}
	
	public void rawRaiseShooter(double power){
		RobotMap.shooterLifterMotor.set(power);
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
		RobotMap.acquireArmVictor.set(0);
	}
	public void pidWrite(double arg0) {
		pidOutput = arg0;
	}


}
