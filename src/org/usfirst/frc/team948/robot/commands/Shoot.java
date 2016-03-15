package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shoot extends CommandBase {
	
	public Timer timer1 = new Timer();
	private double time;
	
	private static final double PUSH_POWER = 1.0;
	
	private static final double BALL_PUSH_TIME = 1.0;
	private static double beginTime = 999999;
	private boolean triggered;
	public Shoot(double time) {
		//requires(shooterWheel);
		requires(shooterBar);
		this.time = time;                  
	}

	@Override
	protected void initialize() {
		beginTime = Integer.MAX_VALUE;
		triggered = false;
		timer1.reset();
		timer1.start();
	}

 	@Override
	protected void execute() {
 		if(!triggered && shooterArm.isArmAtDesiredAngle()){
 			triggered = true;
 			beginTime = timer1.get();
 			SmartDashboard.putString("Shooting Dat", "Left RPM: " + shooterWheel.currentLeftRPM + " Right RPM: " + shooterWheel.currentRightRPM + " Shooting Angle " + ShooterArm.degreesFromVolts(RobotMap.shooterLifterEncoder.getVoltage()) + " Difference to Center: " + (visionProcessing.centerX - CommandBase.preferences.getDouble(PreferenceKeys.CENTER_IMAGE, 160.0)));
 			shooterBar.rawBallPush(PUSH_POWER);
 		}
	}

	@Override
	protected boolean isFinished() {
		return (timer1.get() > BALL_PUSH_TIME + time + beginTime);
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
