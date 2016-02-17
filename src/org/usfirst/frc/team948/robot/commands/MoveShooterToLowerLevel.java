package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.subsystems.ShooterArm;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm.ShooterAngle;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveShooterToLowerLevel extends CommandBase {
	private double angle;
	private ShooterAngle currentAngle;
	

    public MoveShooterToLowerLevel(double angle) {
    	requires(shooterArm);
    	this.angle = angle;
    	
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	currentAngle = shooterArm.findNearestAngle(angle);
    	ShooterAngle nextLowerAngle = shooterArm.nextLowerAngle(currentAngle);
    	shooterArm.setDesiredArmAngle(nextLowerAngle.getValue());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	shooterArm.moveArmToDesiredAngle();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooterArm.stopArm();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
