package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {

	public Shooter() { 	
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub		
	}
	public void rawRaiseShooter(double power){
		RobotMap.shooterLifterMotor.set(power);
	}
}


