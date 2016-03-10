package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterArm extends Subsystem implements PIDOutput {
	private double pidOutput;

	private static final double VOLTS_0 = (Robot.competitionRobot) ? 1.070 : 0.950;
	private static final double VOLTS_VARIABLE = (Robot.competitionRobot) ? 1.676 : 2.160;
	private static final double VARIABLE_ANGLE = (Robot.competitionRobot) ? 45 : 90;
	private static final double SLOPE_VOLTS_FROM_DEGREES = (VOLTS_VARIABLE - VOLTS_0) / VARIABLE_ANGLE;
	public final static double TOLERANCE = 1.0 * SLOPE_VOLTS_FROM_DEGREES;
	private static double p = 0;
	private static double i = 0;
	private static double d = 0;
	private PIDController shooterElevatePID = new PIDController(p,i,d, RobotMap.shooterLifterEncoder, this);
	
	public enum ShooterAngle {
		GROUND(-15), OUTERWORKS_CORNER(45), OUTERWORKS(50), LINE(55), TOWER(65);
		// Values need to be set
		private double angleInDegrees;

		private ShooterAngle(double angleInDegrees) {
			this.angleInDegrees = angleInDegrees;
		}

		public double getAngleInDegrees() {
			return angleInDegrees;
		}
	}

	public ShooterArm() {

	}

	@Override
	protected void initDefaultCommand() {
		// setDefaultCommand(new MoveShooterLevel());
	}

	public void rawRaiseShooter(double power) {
		if(degreesFromVolts(RobotMap.shooterLifterEncoder.getVoltage()) < 16 || power < 0){
			RobotMap.shooterLifterMotor.set(0);
		}else{
			RobotMap.shooterLifterMotor.set(power);
		}
	}

	public void moveArmInit() {
		p = CommandBase.preferences.getDouble(PreferenceKeys.SHOOTER_P, 1.0);
		i = CommandBase.preferences.getDouble(PreferenceKeys.SHOOTER_I, 0.02);
		d = CommandBase.preferences.getDouble(PreferenceKeys.SHOOTER_D, 0.5);
		shooterElevatePID.setPID(p, i, d);
		shooterElevatePID.reset();
		shooterElevatePID.setAbsoluteTolerance(TOLERANCE);
		shooterElevatePID.setOutputRange(-.5, 1.0);
		pidOutput = 0;
		shooterElevatePID.enable();
	}

	public void setTolerance(double tolerance){
		shooterElevatePID.setAbsoluteTolerance(tolerance);
	}
	public void setDesiredArmAngle(double angle) {
		shooterElevatePID.setSetpoint(voltsFromDegrees(angle));
	}

	public void moveArmToDesiredAngle() {
		if(shooterElevatePID.getError() > 5*SLOPE_VOLTS_FROM_DEGREES){
			shooterElevatePID.setPID(p, 0,  d);
		}else{
			shooterElevatePID.setPID(p, i, d);
		}
		
		SmartDashboard.putNumber("Shooter raise output", pidOutput);
		// SmartDashboard.putNumber("Shooter desired angle",
		// degreesFromVolts(shooterElevatePID.getSetpoint()));
		// SmartDashboard.putNumber("Shooter angle",
		// degreesFromVolts(RobotMap.shooterLifterEncoder.getVoltage()));
		SmartDashboard.putNumber("Shooter error degs", shooterElevatePID.getError()/SLOPE_VOLTS_FROM_DEGREES);
		rawRaiseShooter(pidOutput);
	}

	public void moveArmToDesiredAngleVisionTracking() {
		moveArmToDesiredAngle();
		shooterElevatePID.setSetpoint(voltsFromDegrees(Robot.visionProcessing.getShootingAngle()));
	}

	public boolean isArmAtDesiredAngle() {
		// return shooterElevatePID.onTarget();
		return Math.abs(shooterElevatePID.getError()) < TOLERANCE;
	}

	public void stopArm() {
		shooterElevatePID.reset();
		shooterElevatePID.disable();
		RobotMap.shooterLifterMotor.set(0);
	}

	public void pidWrite(double arg0) {
		pidOutput = arg0;
	}

	public static double voltsFromDegrees(double degrees) {
		double volts = degrees * SLOPE_VOLTS_FROM_DEGREES + VOLTS_0;
		return volts;
	}

	public static double degreesFromVolts(double volts) {
		return (volts - VOLTS_0) / SLOPE_VOLTS_FROM_DEGREES;

	}

	public ShooterAngle nextHigherAngle(ShooterAngle currentAngle) {
		ShooterAngle[] angles = ShooterAngle.values();
		for (int i = 0; i < angles.length; i++) {
			if (currentAngle.equals(angles[i])) {
				return angles[Math.min(angles.length - 1, i + 1)];

			}
		}
		return null;
	}

	public ShooterAngle nextLowerAngle(ShooterAngle currentAngle) {
		ShooterAngle[] angles = ShooterAngle.values();
		for (int i = 0; i < angles.length; i++) {
			if (currentAngle.equals(angles[i])) {
				return angles[Math.max(0, i - 1)];
			}
		}
		return null;
	}

	/**
	 * Returns the ShooterAngle nearest to the given voltage.
	 */
	public ShooterAngle findNearestAngle(double degree) {
		ShooterAngle[] angles = ShooterAngle.values();
		int nearest = 0;
		double diff = Math.abs(degree - angles[nearest].getAngleInDegrees());
		for (int i = 1; i < angles.length; i++) {
			double d = Math.abs(degree - angles[i].getAngleInDegrees());
			if (d < diff) {
				diff = d;
				nearest = i;
			}
		}
		return angles[nearest];
	}

}
