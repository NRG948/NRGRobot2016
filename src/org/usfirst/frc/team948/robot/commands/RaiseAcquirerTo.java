package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team948.robot.subsystems.Drive;

public class RaiseAcquirerTo extends CommandBase{

	public double angle;
	
	public RaiseAcquirerTo(double angle){
		requires(CommandBase.acquirer);
		this.angle = angle;
	}
	
	protected void initialize (){
		CommandBase.acquirer.setDesiredArmAngle(angle);
	}
	
	protected void execute(){
		CommandBase.acquirer.moveArmToDesiredAngle();
	}
	
	protected boolean isFinished(){
		return CommandBase.acquirer.isArmAtDesiredAngle();
	}
	
	protected void end(){
		CommandBase.acquirer.stopArm();
	}
	
	protected void interrupted(){
		end();
	}
}
