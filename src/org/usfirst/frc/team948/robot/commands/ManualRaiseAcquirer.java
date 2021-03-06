package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.DS2016;
import org.usfirst.frc.team948.robot.RobotMap;


public class ManualRaiseAcquirer extends CommandBase{

	private double power;
	private boolean givenPower = false;
	public ManualRaiseAcquirer() {
		requires(acquirerArm);
	}
	
	public ManualRaiseAcquirer(double power){
		this.power = power;
		givenPower = true;
	}
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		if(givenPower){
			acquirerArm.rawRaiseArm(power);
			return;
		}
//		double pLeft = DS2016.xBoxController.getRawAxis(2);
//		double pRight = DS2016.xBoxController.getRawAxis(3);
//		double power = (pLeft != 0) ? -pLeft : pRight;
//		acquirerArm.rawRaiseArm(power * 0.4);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		acquirerArm.stopArm();
	}

	@Override
	protected void interrupted() {
		end();
	}
	
}
