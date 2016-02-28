package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnAngle extends CommandBase{
	private final double DEFAULT_TOLERANCE = 2.0; //This needs value needs to be changed
	private double finalHeading;
	private double angle;
	private double power;
	private double tolerance;
	private boolean vision = false;
	private final double VISION_TOLERANCE = 1;
	
	public TurnAngle(double angle, double power, double tolerance){
		requires(drive);
		this.angle = angle;
		this.power = power;
		this.tolerance = tolerance;
	}
	
	public TurnAngle(double angle, double power){
		requires(drive);
		this.angle = angle;
		this.power = power;
		tolerance = DEFAULT_TOLERANCE;
	}
	public TurnAngle(double power){
		requires(drive);
		vision = true;
		tolerance = VISION_TOLERANCE;
		this.power = power;
		}
	
	
	protected void initialize (){
		if (vision) {
			angle = visionProcessing.getTurningAngleProportion();
		}
		double initialHeading = drive.turnToHeadingInit(tolerance, power);
		finalHeading = initialHeading + angle;
		SmartDashboard.putNumber("turn final heading", finalHeading);
	}
	
	protected void execute(){
		drive.turnToHeading(finalHeading, power);
	}
	
	protected boolean isFinished(){
		return angle == 0 || drive.turnToHeadingComplete(tolerance); //|| timer.get() > 5;
	}
	
	protected void end(){
		drive.rawStop();
		drive.turnToHeadingEnd(finalHeading);  
	}

	protected void interrupted() {
		end();
	}

}
