package org.usfirst.frc.team948.robot.commands;

import org.usfirst.frc.team948.robot.Robot;

import org.usfirst.frc.team948.robot.subsystems.AcquirerArm;
import org.usfirst.frc.team948.robot.subsystems.AcquirerWheel;
import org.usfirst.frc.team948.robot.subsystems.Climber;
import org.usfirst.frc.team948.robot.subsystems.Drawbridge;
import org.usfirst.frc.team948.robot.subsystems.Drive;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;
import org.usfirst.frc.team948.robot.subsystems.ShooterBar;
import org.usfirst.frc.team948.robot.subsystems.ShooterWheel;
import org.usfirst.frc.team948.robot.subsystems.VisionProcessing;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandBase extends Command {
//	public static DS2016 ds = new DS2016();
	public static Drive drive = Robot.drive;
	public static ShooterWheel shooterWheel = Robot.shooterWheel;
	public static ShooterBar shooterBar = Robot.shooterBar;
	public static AcquirerArm acquirerArm = Robot.acquirerArm;
	public static AcquirerWheel acquirerWheel = Robot.acquirerWheel;
	public static Climber climber = Robot.climber;
	public static Drawbridge drawbridge = Robot.drawbridge;
	public static ShooterArm shooterArm = Robot.shooterArm;
	public static VisionProcessing visionProcessing = Robot.visionProcessing;
	public static Preferences preferences = Preferences.getInstance();

}
