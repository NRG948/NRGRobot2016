package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class AcquirerWheel extends Subsystem{

	@Override
	protected void initDefaultCommand() {
		
	}
	public void rawAcquireWheels(double speed) {
		RobotMap.acquireWheelVictor.set(speed);
	}
	public void stopAcquirerWheels () {
		rawAcquireWheels(0);
	}

	
}
