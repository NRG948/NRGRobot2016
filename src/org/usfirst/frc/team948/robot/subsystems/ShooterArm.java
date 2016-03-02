package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterArm extends Subsystem implements PIDOutput{
	private PIDController shooterElevatePID;
	private double pidOutput;
	
	private final double TOLERANCE = 1.0 * SLOPE_VOLTS_FROM_DEGREES;
	private static final double VOLTS_0 = (Robot.competitionRobot)? 1.070 : 0.945;
	private static final double VOLTS_VARIABLE = (Robot.competitionRobot)? 1.676 : 1.460;
	private static final double VARIABLE_ANGLE = (Robot.competitionRobot) ? 45 : 37;
	private static final double SLOPE_VOLTS_FROM_DEGREES = (VOLTS_VARIABLE - VOLTS_0)/ VARIABLE_ANGLE;
	
	public enum ShooterAngle{
		GROUND(0),
		OUTERWORKS_CORNER(45),
		OUTERWORKS(50),
		LINE(55),
		TOWER(65);
		//Values need to be set
		private double angleInDegrees;

		private ShooterAngle(double angleInDegrees) {
			this.angleInDegrees = angleInDegrees;
		}
		
		public double getAngleInDegrees(){
			return angleInDegrees;
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
		shooterElevatePID =  new PIDController(CommandBase.preferences.getDouble(PreferenceKeys.SHOOTER_P, 1.0), 
											   CommandBase.preferences.getDouble(PreferenceKeys.SHOOTER_I, 0.02), 
											   CommandBase.preferences.getDouble(PreferenceKeys.SHOOTER_D, 0.5), 
											   RobotMap.shooterLifterEncoder, this);
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
		SmartDashboard.putNumber("Shooter raise output", pidOutput);
//		SmartDashboard.putNumber("Shooter desired angle", degreesFromVolts(shooterElevatePID.getSetpoint()));
//		SmartDashboard.putNumber("Shooter angle", degreesFromVolts(RobotMap.shooterLifterEncoder.getVoltage()));
		SmartDashboard.putNumber("Shooter error", shooterElevatePID.getError());
		RobotMap.shooterLifterMotor.set(pidOutput);
	}
	
	public boolean isArmAtDesiredAngle() {
//		return shooterElevatePID.onTarget();
		return Math.abs(shooterElevatePID.getError()) < TOLERANCE;
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
