package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drawbridge extends Subsystem {

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void rawRaise(double power) {
		RobotMap.drawbridgeArm.set(power);
	}
	
	public void stopArm() {
		RobotMap.drawbridgeArm.set(0);
	}

}
