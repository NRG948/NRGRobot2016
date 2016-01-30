package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	private static Victor rightShooterWheel = RobotMap.rightShooterWheel;
	private static Victor leftShooterWheel = RobotMap.leftShooterWheel;
	private static Victor shooterLifterMotor = RobotMap.shooterLifterMotor;
	private static Encoder rightShooterWheelEncoder = RobotMap.rightShooterWheelEncoder;
	private static Encoder leftShooterWheelEncoder = RobotMap.leftShooterWheelEncoder;
	private static AnalogInput shooterLifterEncoder = RobotMap.shooterLifterEncoder;

	public Shooter() { 	
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub		
	}
	public void rawRaiseShooter(double power){
		shooterLifterMotor.set(power);
	}
}


