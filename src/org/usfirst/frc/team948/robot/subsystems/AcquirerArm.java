package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.Robot.Level;
import org.usfirst.frc.team948.robot.commands.ManualRaiseAcquirer;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AcquirerArm extends Subsystem implements PIDOutput{
	private PIDController acquirerAnglePID = new PIDController(ACQUIRER_P, ACQUIRER_I, ACQUIRER_D, RobotMap.armAngleEncoder, this);;
	private double pidOutput;
	private final double TOLERANCE = 1.0 * SLOPE_VOLTS_FROM_DEGREES;
	 
	private static final double VOLTS_0 = (Robot.competitionRobot)? 3.300 : 3.325;
	private static final double VOLTS_90 = (Robot.competitionRobot)? 2.060 : 2.075;
	    
	
	
	private static final double SLOPE_VOLTS_FROM_DEGREES = (VOLTS_90 - VOLTS_0) / 90;

	private static final double ACQUIRER_P = 0.5;
	private static final double ACQUIRER_I = 0.003;
	private static final double ACQUIRER_D = 0.02;

	
	public AcquirerArm(){
		
	}
	@Override
	public void pidWrite(double arg0) {
		pidOutput = arg0;
		
	}
	@Override
	protected void initDefaultCommand() {
//		setDefaultCommand(new ManualRaiseAcquirer());
		
	}
	private double voltsFromDegrees(double degrees)
	{
		double volts = degrees * SLOPE_VOLTS_FROM_DEGREES + VOLTS_0;
		return volts;
	}
	
	private double degreesFromVolts(double volts)
	{
		return (volts - VOLTS_0) / SLOPE_VOLTS_FROM_DEGREES;
	}

	public void rawRaiseArm(double power) {
		if ((power > 0 && hasReachedUpperLimit()) || (power < 0 && hasReachedLowerLimit()))
		{
			RobotMap.acquireArmVictor.set(0);
		}
		else {
			RobotMap.acquireArmVictor.set(power);
	
		}
	}
	public void setDesiredArmAngle(double angleDegrees) {
		acquirerAnglePID.setSetpoint(voltsFromDegrees(angleDegrees));
	}
	
	public void raiseArmToAngleInit() {
		acquirerAnglePID.reset();
		acquirerAnglePID.setAbsoluteTolerance(TOLERANCE);
		acquirerAnglePID.setOutputRange(-.2, .6);
		pidOutput = 0;
		acquirerAnglePID.enable();
	}
	
	public void raiseArmToAngle(double angleDegrees) {
		acquirerAnglePID.setSetpoint(voltsFromDegrees(angleDegrees));
		rawRaiseArm(-pidOutput);
	}
	
	public void raiseArmToAngle() {
		rawRaiseArm(-pidOutput);
	}

	public void raiseArmToAngleEnd(){ 
		stopArm();
	}
	
	public boolean isArmAtDesiredAngle() {
		return acquirerAnglePID.onTarget();
	}
	
	public void stopArm() {
		acquirerAnglePID.reset();
		RobotMap.acquireArmVictor.disable();
		pidOutput = 0;
	}
	public void stopAcquirerArm() {
		rawRaiseArm(0);
		
	}
	
	public Level nextHigherLevel(Level currentLevel) {
		Level[] levels = Level.values();
		for (int i = 0; i < levels.length; i++) {
			if (currentLevel.equals(levels[i])) {
				return levels[Math.min(levels.length - 1, i + 1)];
			}
		}
	return null;
	}

	public Level nextLowerLevel(Level currentLevel) {
		Level[] levels = Level.values();
		for (int i = 0; i < levels.length; i++) {
			if (currentLevel.equals(levels[i])) {
				return levels[Math.max(0, i - 1)];
			}
		}
		return null;
	}

	/**
	 * Returns the level nearest to the given angle.
	 */
	public Level findNearestLevel(Level currentLevel) {
		Level[] levels = Level.values();
		int nearest = 0;
		double diff = Math.abs(currentLevel.getValue()- levels[nearest].getValue());
		for (int i = 1; i < levels.length; i++) {
			double d = Math.abs(currentLevel.getValue() - levels[i].getValue());
			if (d < diff) {
				diff = d;
				nearest = i;
			}
		}
		return levels[nearest];
	}
	
	public boolean hasReachedUpperLimit() {
		return RobotMap.acquireUpperLimit.get();
	}
	
	public boolean hasReachedLowerLimit() {
		return RobotMap.acquireLowerLimit.get();
	}
}


