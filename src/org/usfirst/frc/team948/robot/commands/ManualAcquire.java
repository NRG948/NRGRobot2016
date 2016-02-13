package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;

public class ManualAcquire extends CommandBase {
	
	double power = -0.6;
	boolean manual;
	
    public ManualAcquire(boolean manual) {
    	
    	requires(acquirer);
    
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }
   

    // Called repeatedly when this Command is scheduled to run
    
     
    protected void execute() {
    	acquirer.rawAcquire(-1);
    	RobotMap.shooterBallPusher.set(-power);
    	RobotMap.rightShooterWheel.set(-power);
    	RobotMap.leftShooterWheel.set(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (manual)? false : shooterWheel.isBallLoaded();
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	acquirer.rawAcquire(0.0);
    	RobotMap.rightShooterWheel.set(0.0);
    	RobotMap.leftShooterWheel.set(0.0);
    	RobotMap.shooterBallPusher.set(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}