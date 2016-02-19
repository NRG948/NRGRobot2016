package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterWheel extends Subsystem {
	public double currentLeftRPM;
	public double currentRightRPM;
	public ShooterWheel(){
		
	}

	@Override
	protected void initDefaultCommand() {
//		setDefaultCommand(new ShooterRampUp());
		
	}
	public void rawShoot(double power){
		RobotMap.leftShooterWheel.set(-power);
		RobotMap.rightShooterWheel.set(power);
	}
	public void updateLeftRPM(){
		currentLeftRPM = 60*RobotMap.leftShooterWheelEncoder.getRate();
	}
	public void updateRightRPM(){
		currentRightRPM = 60*RobotMap.rightShooterWheelEncoder.getRate();
	}
	public boolean isBallLoaded(){
		return RobotMap.ballBeamBreaker.get();
	}

}
