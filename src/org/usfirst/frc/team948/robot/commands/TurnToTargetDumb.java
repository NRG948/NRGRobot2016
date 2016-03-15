package org.usfirst.frc.team948.robot.commands;

public class TurnToTargetDumb extends CommandBase{

	private double power;
	private double heading;
	private double previousError;
	public TurnToTargetDumb(double heading, double power){
		requires(drive);
		this.power = power;
		this.heading = heading;
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		drive.drivePID.setSetpoint(heading);
		previousError = drive.drivePID.getError();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		if(drive.drivePID.getError() < 0){
			drive.rawTankDrive(power, -power);
		}else{
			drive.rawTankDrive(-power, power);
		}
	}

	@Override
	protected boolean isFinished() {
		if(previousError * drive.drivePID.getError() < 0){
			return true;
		}else{
			previousError = drive.drivePID.getError();
			return false;
		}
	}

	@Override
	protected void end() {
		drive.rawStop();
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
