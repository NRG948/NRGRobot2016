package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.RobotMap;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm.ShooterAngle;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RaiseShooterToNextHigherAngle extends CommandBase{

	private ShooterAngle desiredAngle;
	private static int a = 0;
	public RaiseShooterToNextHigherAngle() {
		requires(shooterArm);
	}
	
	@Override
	protected void initialize() {
		double voltage = RobotMap.shooterLifterEncoder.getVoltage();
		double angle = shooterArm.degreesFromVolts(voltage);
		ShooterAngle currentAngle = shooterArm.findNearestAngle(angle);
		desiredAngle = shooterArm.nextHigherAngle(currentAngle);
		a++;
		SmartDashboard.putString("Next Higher Level Seek", a+""+desiredAngle.getAngleInDegrees());
		shooterArm.moveArmInit();
		/*if ((desiredAngle.getAngleInDegrees() - ShooterArm.degreesFromVolts(RobotMap.shooterLifterEncoder.getVoltage()) > 30)) {
			shooterArm.setDesiredArmAngle(desiredAngle.getAngleInDegrees() + ShooterArm.OFFSET_SLOP_DEGREES);
		}
		else {*/
			shooterArm.setDesiredArmAngle(desiredAngle.getAngleInDegrees());
		//}

	}
	
	@Override
	protected void execute() {

		shooterArm.moveArmToDesiredAngle();
	}

	@Override
	protected boolean isFinished() {
		return false;
//		return shooterArm.isArmAtDesiredAngle();
	}

	@Override
	protected void end() {
		shooterArm.stopArm();
	}

	@Override
	protected void interrupted() {
		end();
	}

}
