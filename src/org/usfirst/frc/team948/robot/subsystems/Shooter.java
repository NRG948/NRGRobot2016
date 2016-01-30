package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	private static Talon rightShooterWheel = RobotMap.rightShooterWheel;
	private static Talon leftShooterWheel = RobotMap.leftShooterWheel;
	private static Talon shooterLifterMotor = RobotMap.shooterLifterMotor;
	private static Encoder rightShooterWheelEncoder = RobotMap.rightShooterWheelEncoder;
	private static Encoder leftShooterWheelEncoder = RobotMap.leftShooterWheelEncoder;
	private static Encoder shooterLifterEncoder = RobotMap.shooterLifterEncoder;

	public Shooter() { 
	
	
	
	
	
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
}


