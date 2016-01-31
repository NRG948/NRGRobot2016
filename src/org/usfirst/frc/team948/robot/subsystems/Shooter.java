package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {

	private double currentLeftRPM;
	private double currentRightRPM;
	public Shooter() { 	
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub		
	}
	public void rawRaiseShooter(double power){
		RobotMap.shooterLifterMotor.set(power);
	}
	public void rawShoot(double power){
		RobotMap.leftShooterWheel.set(-power);
		RobotMap.rightShooterWheel.set(power);
	}
	public void updateLeftRPM(){
		RobotMap.leftShooterWheelEncoder.setDistancePerPulse(1); //place holder need a real DistancePerPusle
		currentLeftRPM = 60*RobotMap.leftShooterWheelEncoder.getRate();
	}
	public void updateRightRPM(){
		RobotMap.leftShooterWheelEncoder.setDistancePerPulse(1); //place holder need a real DistancePerPusle
		currentRightRPM = 60*RobotMap.rightShooterWheelEncoder.getRate();
	}
	
}


