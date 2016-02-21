package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Interrupt extends CommandBase{

	public Interrupt() {
		requires(drive);
		requires(shooterWheel);
		requires(shooterBar);
		requires(shooterArm);
		requires(acquirerArm);
		requires(acquirerWheel);
	}
	
	public Interrupt(Subsystem...subsystems)
	{
		for (Subsystem subsystem : subsystems)
		{
			requires(subsystem);
		}
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}
}
