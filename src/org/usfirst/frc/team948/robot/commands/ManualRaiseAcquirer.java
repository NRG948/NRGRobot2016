package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.subsystems.Acquirer;

public class ManualRaiseAcquirer extends CommandBase{

	private double power;
	public ManualRaiseAcquirer(double power) {
		requires(acquirer);
		this.power = power;
	}
	
	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
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
