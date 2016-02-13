package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.Robot;
import org.usfirst.frc.team948.robot.subsystems.Acquirer;
import org.usfirst.frc.team948.robot.subsystems.Climber;
import org.usfirst.frc.team948.robot.subsystems.Drawbridge;
import org.usfirst.frc.team948.robot.subsystems.Drive;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;
import org.usfirst.frc.team948.robot.subsystems.ShooterBar;
import org.usfirst.frc.team948.robot.subsystems.ShooterWheel;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandBase extends Command {
//	public static DS2016 ds = new DS2016();
	public static Drive drive = Robot.drive;
	public static ShooterWheel shooterwheel = Robot.shooterwheel;
	public static ShooterBar shooterbar = Robot.shooterbar;
	public static Acquirer acquirer = Robot.acquirer;
	public static Climber climber = Robot.climber;
	public static Drawbridge drawbridge = Robot.drawbridge;
	public static ShooterArm shooterarm = Robot.shooterarm;
	
	public static Preferences preferences = Preferences.getInstance();

}
