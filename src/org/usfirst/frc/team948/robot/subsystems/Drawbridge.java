package org.usfirst.frc.team948.robot.subsystems;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.commands.RawRaiseDrawbridge;
import org.usfirst.frc.team948.robot.commands.ManualDrive;
import org.usfirst.frc.team948.robot.commands.TestDrawbridge;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drawbridge extends Subsystem {

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new TestDrawbridge(1));
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
//		return RobotMap.drawbridgeArm.isFwdLimitSwitchClosed();
		return true;
	}
	
	public boolean hasReachedLowerLimit() {
//		return RobotMap.drawbridgeArm.isRevLimitSwitchClosed();
		return true;
	}

}
