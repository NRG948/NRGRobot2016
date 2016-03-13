package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToHeading extends CommandBase{
	private static final double DEFAULT_TOLERANCE = 3.0; //This needs value needs to be changed
	private double finalHeading;
	private double power;
	private double tolerance;
	
	public TurnToHeading(double heading, double power, double tolerance){
		requires(drive);
		this.finalHeading = heading;
		this.power = power;
		this.tolerance = tolerance;
	}
	
	public TurnToHeading(double heading, double power){
		this(heading, power, DEFAULT_TOLERANCE);
	}

	public TurnToHeading(double heading){
		this(heading, 0.64, DEFAULT_TOLERANCE);
		}
	
	
	protected void initialize (){
		SmartDashboard.putNumber("turn final heading", finalHeading);
		drive.turnToHeadingInit(tolerance, power);
	}
	
	protected void execute(){
		drive.turnToHeading(finalHeading, power);
	}
	
	protected boolean isFinished(){
		return drive.turnToHeadingComplete(tolerance); //|| timer.get() > 5;
	}
	
	protected void end(){
		drive.rawStop();
		drive.turnToHeadingEnd(finalHeading);  
	}

	protected void interrupted() {
		end();
	}
}
