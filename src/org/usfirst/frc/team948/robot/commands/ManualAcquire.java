package org.usfirst.frc.team948.robot.commands;



/**
 *
 */
public class ManualAcquire extends CommandBase {
double power;
    public ManualAcquire(double power) {
    	this.power = power;
    	requires(acquirer);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	acquirer.rawAcquire(power);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	acquirer.rawAcquire(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
