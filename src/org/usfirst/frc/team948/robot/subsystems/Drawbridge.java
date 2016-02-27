package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.RawRaiseDrawbridge;
import org.usfirst.frc.team948.robot.commands.ManualDrive;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drawbridge extends Subsystem {

	@Override
	protected void initDefaultCommand() {
		//setDefaultCommand(new ManualDrawbridge(true));
	}

	public void rawRaise(double power) {
		if ((power > 0 && hasReachedUpperLimit()) || (power < 0 && hasReachedLowerLimit()))
		{
			RobotMap.acquireArmVictor.set(0);
		}
		else {
			RobotMap.drawbridgeArm.set(power);
		}
	}
	
	public void stopArm() {
		RobotMap.drawbridgeArm.set(0);
	}
	
	public boolean hasReachedUpperLimit() {
		return RobotMap.drawbridgeArm.isFwdLimitSwitchClosed();
	}
	
	public boolean hasReachedLowerLimit() {
		return RobotMap.drawbridgeArm.isRevLimitSwitchClosed();
	}

}
