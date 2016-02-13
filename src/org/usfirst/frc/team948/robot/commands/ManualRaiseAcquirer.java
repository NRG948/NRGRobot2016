package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.DS2016;
import org.usfirst.frc.team948.robot.RobotMap;


public class ManualRaiseAcquirer extends CommandBase{

	private double power;
	public ManualRaiseAcquirer() {
		requires(acquirer);
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		double pLeft = DS2016.xBoxController.getRawAxis(2);
		double pRight = DS2016.xBoxController.getRawAxis(3);
		double power = (pLeft != 0) ? -pLeft : pRight;
		RobotMap.acquireArmVictor.set(power);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		acquirer.stopArm();
	}

	@Override
	protected void interrupted() {
		end();
	}
	
}
