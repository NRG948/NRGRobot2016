package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Acquirer extends Subsystem implements PIDOutput {

	private AnalogInput armAngleEncoder = RobotMap.armAngleEncoder;
	private Talon armController = RobotMap.acquireArmTalon;
	private Talon rollerController = RobotMap.acquireWheelTalon;
	private PIDController armAnglePID = new PIDController(0.1, 0.01, 0.005, armAngleEncoder, this);
	private double pidOutput;
	private final double ANGLE_TO_VOLTS = 0.01389;
	private final double TOLERANCE = 1.0 * ANGLE_TO_VOLTS;

	public Acquirer() {
		
	}

	public void setArmControllerPower(double power) {
		armController.set(power);
	}

	public void stopArmController() {
		armController.disable();
	}

	public void setRollerControllerPower(double power) {
		rollerController.set(power);
	}

	public void stopRollerController() {
		rollerController.disable();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

	public void stopAcquirer() {
		stopArmController();
		stopRollerController();
	}

	public void setDesiredArmAngle(double angle) {
		armAnglePID.reset();
		armAnglePID.setSetpoint(angle * ANGLE_TO_VOLTS);
		armAnglePID.setAbsoluteTolerance(TOLERANCE);
		pidOutput = 0;
		armAnglePID.enable();
	}
	
	public void RaiseArmTo(Robot.ACQUIRER_ARM_LEVEL_HEIGHT ARM_ANGLE){
		setDesiredArmAngle(ARM_ANGLE.getValue());
		moveArmToDesiredAngle();
		stopArm();
	}

	public void moveArmToDesiredAngle() {
		setArmControllerPower(pidOutput);
	}

	public boolean isArmAtDesiredAngle() {
		return armAnglePID.onTarget();
	}

	public void stopArm() {
		armAnglePID.reset();
		stopArmController();
	}

	@Override
	public void pidWrite(double arg0) {
		pidOutput = arg0;
	}
	
	public void rawRaise(double power) {
		armController.set(power);
	}

}
