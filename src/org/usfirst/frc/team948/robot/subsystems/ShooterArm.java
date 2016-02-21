package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterArm extends Subsystem implements PIDOutput{
	private PIDController shooterElevatePID = new PIDController(0.8, 0.02, 0.5, RobotMap.shooterLifterEncoder, this);
	private double pidOutput;
	
	private final double TOLERANCE = 1.0 * SLOPE_VOLTS_FROM_DEGREES;
	private static final double VOLTS_0 = 1.049;
	private static final double VOLTS_90 = 2.25;
	private static final double SLOPE_VOLTS_FROM_DEGREES = (VOLTS_90 - VOLTS_0) / 90;
	
	public enum ShooterAngle{
		GROUND(0),
		OUTERWORKS_CORNER(45),
		OUTERWORKS(50),
		LINE(55),
		TOWER(65);
		//Values need to be set
		private double value;

		private ShooterAngle(double value) {
			this.value = value;
		}
		
		public double getValue(){
			return value;
		}
	}
	public ShooterArm() {
		
	}
	
	@Override
	protected void initDefaultCommand() {
//		setDefaultCommand(new MoveShooterLevel());
	}
	
	public void rawRaiseShooter(double power){
		RobotMap.shooterLifterMotor.set(power);
	}
	
	public void moveArmInit() {
		shooterElevatePID.reset();
		shooterElevatePID.setAbsoluteTolerance(TOLERANCE);
		shooterElevatePID.setOutputRange(-.2, 0.6);
		pidOutput = 0;
		shooterElevatePID.enable();
	}
	
	public void setDesiredArmAngle(double angle) {
		shooterElevatePID.setSetpoint(voltsFromDegrees(angle));
		RobotMap.shooterLifterMotor.set(pidOutput);
	}
	
	public void moveArmToDesiredAngle() {
//		SmartDashboard.putNumber("Shooter raise output", pidOutput);
//		SmartDashboard.putNumber("Shooter desired angle", degreesFromVolts(shooterElevatePID.getSetpoint()));
//		SmartDashboard.putNumber("Shooter angle", degreesFromVolts(RobotMap.shooterLifterEncoder.getVoltage()));
//		SmartDashboard.putNumber("Shooter error", shooterElevatePID.getError());
		RobotMap.shooterLifterMotor.set(pidOutput);
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
	
	public static double voltsFromDegrees(double degrees)
	{
		double volts = degrees * SLOPE_VOLTS_FROM_DEGREES + VOLTS_0;
		return volts;
	}
	
	public static double degreesFromVolts(double volts)
	{
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
	public ShooterAngle findNearestAngle(double voltage) {
		double degree = degreesFromVolts(voltage);
		ShooterAngle[] angles = ShooterAngle.values();
		int nearest = 0;
		double diff = Math.abs(degree - angles[nearest].getValue());
		for (int i = 1; i < angles.length; i++) {
			double d = Math.abs(degree - angles[i].getValue());
			if (d < diff) {
				diff = d;
				nearest = i;
			}
		}
		return angles[nearest];
	}
	
}
