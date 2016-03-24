package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.Robot.Level;
import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.ManualRaiseAcquirer;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AcquirerArm extends Subsystem implements PIDOutput{
	private PIDController acquirerAnglePID = new PIDController(ACQUIRER_P, ACQUIRER_I, ACQUIRER_D, RobotMap.armAngleEncoder, this);;
	private double pidOutput;
	 
	public final double VOLTS_0 = (Robot.competitionRobot)? 3.58 : 2.996;
	private final double VOLTS_90 = (Robot.competitionRobot)? 2.347 : 1.758;
	    
	
	
	public final double SLOPE_VOLTS_FROM_DEGREES = (VOLTS_90 - VOLTS_0) / 90.0;
	private final double TOLERANCE = Math.abs(2.0 * SLOPE_VOLTS_FROM_DEGREES);
	private static double ACQUIRER_P = 1.5; //1.2
	private static double ACQUIRER_I = 0.065; //0.05
	private static double ACQUIRER_D = 0.02;

	
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
	
	public double degreesFromVolts(double volts)
	{
	//	SmartDashboard.putString("Volt 0 and Slope", VOLTS_0 + " " + SLOPE_VOLTS_FROM_DEGREES);
		return (volts - VOLTS_0) / SLOPE_VOLTS_FROM_DEGREES;
	}

	public void rawRaiseArm(double power) {
		if ((hasReachedUpperLimit() && power > 0) || (hasReachedLowerLimit() && power < 0))
		{
			RobotMap.acquireArmVictor.set(0);
		} else {
			RobotMap.acquireArmVictor.set(power);
		}
	}
	public void setDesiredArmAngle(double angleDegrees) {
		acquirerAnglePID.setSetpoint(voltsFromDegrees(angleDegrees));
	}
	
	public void raiseArmToAngleInit() {
		ACQUIRER_P = CommandBase.preferences.getDouble(PreferenceKeys.ACQUIRER_P, 1.2);
		ACQUIRER_I = CommandBase.preferences.getDouble(PreferenceKeys.ACQUIRER_I, 0.02);
		ACQUIRER_D = CommandBase.preferences.getDouble(PreferenceKeys.ACQUIRER_D, 0.02);
		acquirerAnglePID.setPID(ACQUIRER_P, 0, ACQUIRER_D);
		acquirerAnglePID.reset();
		acquirerAnglePID.setAbsoluteTolerance(TOLERANCE);
		//NOTE, When pidOUTPUT is negative, the acquirer goes up
		acquirerAnglePID.setOutputRange(-.6, .3);
		acquirerAnglePID.setToleranceBuffer(3);
		pidOutput = 0;
		acquirerAnglePID.enable();
	}
	
	public void raiseArmToAngle(double angleDegrees) {
		acquirerAnglePID.setSetpoint(voltsFromDegrees(angleDegrees));
		rawRaiseArm(-pidOutput);
	}
	
	public void raiseArmToAngle() {
		SmartDashboard.putNumber("Acquirer Error", acquirerAnglePID.getError());
		SmartDashboard.putNumber("Acquirer Output", pidOutput);
		if(Math.abs(acquirerAnglePID.getError()) < Math.abs(5*SLOPE_VOLTS_FROM_DEGREES)){
			acquirerAnglePID.setPID(ACQUIRER_P, ACQUIRER_I, ACQUIRER_D);
		}else{
			acquirerAnglePID.setPID(ACQUIRER_P, 0, ACQUIRER_D);
		}
		rawRaiseArm(-pidOutput);
	}

	public void raiseArmToAngleEnd(){ 
		stopArm();
	}
	
	public boolean isArmAtDesiredAngle() {
		return Math.abs(acquirerAnglePID.getError()) < TOLERANCE;
	}
	
	public void stopArm() {
		acquirerAnglePID.reset();
		RobotMap.acquireArmVictor.set(0);
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


