package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.ManualDrawbridge;
import org.usfirst.frc.team948.robot.commands.ManualDrive;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drawbridge extends Subsystem {

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ManualDrawbridge(true));
	}

	public void rawRaise(double power) {
		RobotMap.drawbridgeArm.set(power);
	}
	
	public void stopArm() {
		RobotMap.drawbridgeArm.set(0);
	}

}
