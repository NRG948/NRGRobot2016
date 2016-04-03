package org.usfirst.frc.team948.robot.commandgroups;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.commands.Delay;
import org.usfirst.frc.team948.robot.commands.RaiseShooterArmTo;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.Shoot;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class BatterShot extends CommandGroup{

	public BatterShot() {
		addParallel(new RaiseShooterArmTo(ShooterArm.ShooterAngle.BATTER));
		addSequential(new DelayAndShoot());
	}
	
	private class DelayAndShoot extends CommandGroup{
		public DelayAndShoot() {
			addParallel(new RampToRPM(1400));
			addSequential(new Delay(3.0));
			addSequential(new Shoot(0, false));
		}
	}
}

