package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterArm extends Subsystem implements PIDOutput{
	private PIDController shooterElevatePID = new PIDController(1, 0.01, 0.005, RobotMap.shooterLifterEncoder, this);
	private double pidOutput;
	private final double ANGLE_TO_VOLTS = 0.01367;
	private final double TOLERANCE = 1.0 * ANGLE_TO_VOLTS;
	
	public ShooterArm() {
	}
	
	@Override
	protected void initDefaultCommand() {
	}
	
	public void rawRaiseShooter(double power){
		if(RobotMap.shooterLifterEncoder.getVoltage()>4.6){
			RobotMap.shooterLifterMotor.set(0);
			return;
		}
		RobotMap.shooterLifterMotor.set(power);
	}

	public void setDesiredArmAngle(double angle) {
		shooterElevatePID.reset();
		shooterElevatePID.setSetpoint(angle * ANGLE_TO_VOLTS+3.488);
		shooterElevatePID.setAbsoluteTolerance(TOLERANCE);
		shooterElevatePID.setOutputRange(-0.1, 0.3);
		pidOutput = 0;
		shooterElevatePID.enable();
	}
	
	public void moveArmToDesiredAngle() {
		SmartDashboard.putNumber("Shooter Raise pidOutput", pidOutput);
		SmartDashboard.putNumber("Shooter Raise Error", shooterElevatePID.getError());
		rawRaiseShooter(pidOutput);
	}
	
	public boolean isArmAtDesiredAngle() {
		return shooterElevatePID.onTarget();
	}

	public void stopArm() {
		shooterElevatePID.reset();
		RobotMap.shooterLifterMotor.set(0);
	}

	public void pidWrite(double arg0) {
		pidOutput = arg0;
	}
}
	
