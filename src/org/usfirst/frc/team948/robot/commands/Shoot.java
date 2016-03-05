package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shoot extends CommandBase {
	
	public Timer timer1 = new Timer();
	private double time;
	private double timeWhenStart;
	private boolean changedState = true;
	
	private static final double PUSH_POWER = 1.0;
	
	private static final double BALL_PUSH_TIME = 1.0;

	public Shoot(double time) {
		//requires(shooterWheel);
		requires(shooterBar);
		this.time = time;                  
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Shooting Dat", "Left RPM: " + shooterWheel.currentLeftRPM + " Right RPM: " + shooterWheel.currentRightRPM + " Shooting Angle " + ShooterArm.degreesFromVolts(RobotMap.shooterLifterEncoder.getVoltage()));
		timer1.reset();
		timer1.start();
	}

 	@Override
	protected void execute() {
		if (shooterArm.isArmAtDesiredAngle()){
			if(changedState){
				timeWhenStart = timer1.get();
				changedState = false;
			}
			shooterBar.rawBallPush(PUSH_POWER);
		}
	}

	@Override
	protected boolean isFinished() {
		if(!shooterArm.isArmAtDesiredAngle()){
			return false;
		}
		return (timer1.get() > BALL_PUSH_TIME + time+timeWhenStart);
//		Finishes command if the current time is greater than the Ball Push Time
	}

	@Override
	protected void end() {
		timer1.stop();
		shooterWheel.rawShoot(0);
		shooterBar.rawBallPush(0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
