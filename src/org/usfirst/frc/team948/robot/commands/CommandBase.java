package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.DS2016;
import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.subsystems.Acquirer;
import org.usfirst.frc.team948.robot.subsystems.Drive;
import org.usfirst.frc.team948.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandBase extends Command {
//	public static DS2016 ds = new DS2016();
	public static Drive drive = Robot.drive;
	public static Shooter shooter = Robot.shooter;
	public static Acquirer acquirer = Robot.acquirer;

	public static Preferences preferences = Preferences.getInstance();

}
