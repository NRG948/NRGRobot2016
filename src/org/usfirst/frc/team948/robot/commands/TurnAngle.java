package org.usfirst.frc.team948.robot.commands;

public class TurnAngle extends CommandBase{
	private final double DEFAULT_TOLERANCE = 1.0; //This needs value needs to be changed
	private double finalHeading;
	private double angle;
	private double power;
	private double tolerance;
	
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
	
	protected void initialize (){
		double initialHeading = drive.turnToHeadingInit(tolerance, power);
		finalHeading = initialHeading + angle;
	}
	
	protected void execute(){
		drive.turnToHeading(finalHeading, power);
	}
	
	protected boolean isFinished(){
		return angle == 0 || drive.turnToHeadingComplete();
	}
	
	protected void end(){
		drive.rawStop();
		drive.turnToHeadingEnd(finalHeading);  
	}

	protected void interrupted() {
		end();
	}

}