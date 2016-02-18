package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Interrupt extends CommandBase{

	public Interrupt() {
		requires(drive);
		requires(acquirer);
		requires(shooterWheel);
		requires(shooterBar);
		requires(shooterArm);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
}
