package org.usfirst.frc.team948.robot.commands;

public class ManualDrawbridge extends CommandBase {
	
	private final double EXTEND_POWER = .99;
	private final double RETRACT_POWER = -.99;
	private boolean extend;
	
	public ManualDrawbridge(boolean extend)
	{
		requires(drawbridge);
		this.extend = extend;
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
//		if (extend)
//		{
//			drawbridge.rawRaise(EXTEND_POWER);
//		}
//		else
//		{
//			drawbridge.rawRaise(RETRACT_POWER);
//		}
		double d = (extend) ? EXTEND_POWER : RETRACT_POWER;
		drawbridge.rawRaise(d);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drawbridge.stopArm();

	}

	@Override
	protected void interrupted() {
		
	}

}
