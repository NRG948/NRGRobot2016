package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterBar extends Subsystem{
	public ShooterBar(){
		
	}
	@Override
	protected void initDefaultCommand() {
		
	}
	public void rawBallPush(double power){
		RobotMap.shooterBallPusher.set(-power);
	}
	
	

}
