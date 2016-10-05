
package org.usfirst.frc.team948.robot;

import org.usfirst.frc.team948.robot.commandgroups.ChivalAssist;
import org.usfirst.frc.team948.robot.commandgroups.ShootSequence;
import org.usfirst.frc.team948.robot.commandgroups.TraverseDefenseShootRoutine;
import org.usfirst.frc.team948.robot.commandgroups.TwoBallAutonomous;
import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.RaiseShooterArmTo;
import org.usfirst.frc.team948.robot.commands.TurnAngle;
import org.usfirst.frc.team948.robot.commands.TurnToHeading;
import org.usfirst.frc.team948.robot.commands.TurnToTargetDumb;
import org.usfirst.frc.team948.robot.commands.TurnToVisionTarget;
import org.usfirst.frc.team948.robot.commands.TurnToVisionTargetContinuous;
import org.usfirst.frc.team948.robot.commands.WaitForRPM;
import org.usfirst.frc.team948.robot.subsystems.AcquirerArm;
import org.usfirst.frc.team948.robot.subsystems.AcquirerWheel;
import org.usfirst.frc.team948.robot.subsystems.Climber;
import org.usfirst.frc.team948.robot.subsystems.Drawbridge;
import org.usfirst.frc.team948.robot.subsystems.Drive;
import org.usfirst.frc.team948.robot.subsystems.ShooterArm;
import org.usfirst.frc.team948.robot.subsystems.ShooterBar;
import org.usfirst.frc.team948.robot.subsystems.ShooterWheel;
import org.usfirst.frc.team948.robot.subsystems.VisionProcessing;
import org.usfirst.frc.team948.robot.utilities.AHRSGyro;
import org.usfirst.frc.team948.robot.utilities.NavXTester;
import org.usfirst.frc.team948.robot.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static boolean competitionRobot = true;
	public static final double NO_TURN = 999;
	public static final double NO_AUTO = -10.0;

	public enum Level {
		DEFAULT(15), ACQUIRE(25), CHIVAL(85), SALLY_PORT_HIGH(110), FULL_BACK(155); // VALUE
																					// NEEDS
																					// TO
																					// BE
																					// CHECKED

		private double value;

		private Level(double value) {
			this.value = value;
		}

		public double getValue() {
			return value;
		}

	}

	public enum AutoPosition {

		// Angles at which to turn when performing autonomous routine
		// Positions 1 and 2 go into the right goal
		// Positions 3, 4, and 5 go into the middle goal
		// TWO: -46.14
		// second angle of 999 means do not execute second turn
		LOWBAR_ONE(15.5, 60.0, 0.0, NO_TURN), POSITION_TWO(19.0, 50.0, -3.0, NO_TURN), POSITION_THREE(11.0, 60.0, 3.0,
				-10.0), POSITION_FOUR(14.0, 0, 0, 0), POSITION_FIVE(11.0, -70.0, 4.0, -10.0), TEST(0.5, 20, 0.5, 0);

		private double distance;
		private double angle;
		private double secondDistance;
		private double secondAngle;

		private AutoPosition(double distance, double angle, double secondDistance, double secondAngle) {
			this.distance = distance;
			this.angle = angle;
			this.secondDistance = secondDistance;
			this.secondAngle = secondAngle;
		}

		public double getDistance() {
			return distance;
		}

		public double getAngle() {
			return angle;
		}

		public double getSecondDistance() {
			return secondDistance;
		}

		public double getSecondAngle() {
			return secondAngle;
		}
	}

	public enum Defense {
		TERRAIN(90),
		// RAMPARTS(0.75, 90),
		// ROUGH_TERRAIN(0.6, 90), //0.75, goes too far at 12
		// ROCK_WALL(0.6, 90),
		LOW_BAR(/* 0.64, */ 10);
		// MOAT(0.7, 90);

		// private double power;
		private double acquirerAngle;

		private Defense(/* double power, */double acquirerAngle) {
			// this.power = power;
			this.acquirerAngle = acquirerAngle;
		}

		// public double getPower() {
		// return power;
		// }

		public double getAcquirerAngle() {
			return acquirerAngle;
		}
	}

	public static Drive drive = new Drive();
	public static ShooterWheel shooterWheel = new ShooterWheel();
	public static ShooterBar shooterBar = new ShooterBar();
	public static ShooterArm shooterArm = new ShooterArm();
	public static AcquirerArm acquirerArm = new AcquirerArm();
	public static AcquirerWheel acquirerWheel = new AcquirerWheel();
	public static VisionProcessing visionProcessing = new VisionProcessing();
	public static Climber climber = new Climber();
	public static Drawbridge drawbridge = new Drawbridge();
	public static PowerDistributionPanel pdp = new PowerDistributionPanel();

	private int screenUpdateCounter;
	private double autoPower;
	private boolean autoShoot;
	private AutoPosition autoPosition;
	private Defense autoDefense;
	public static Timer autoTimer;
	public static double autoStartTime = 0;
	SendableChooser autoChooser;

	Command autonomousCommand;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		RobotMap.init();
		DS2016.buttonInit();
		// ArduinoSerialReader.startCapture();
		try {
			visionProcessing.cameraInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// autoChooser = new SendableChooser();
		// autoChooser.addDefault("Low Bar", new
		// TraverseDefenseShootRoutine(AutoPosition.LOWBAR_ONE,
		// Defense.LOW_BAR));
		// autoChooser.addObject("Position 2", new
		// TraverseDefenseShootRoutine(AutoPosition.POSITION_TWO,
		// Defense.ROUGH_TERRAIN));
		// autoChooser.addObject("Position 3", new
		// TraverseDefenseShootRoutine(AutoPosition.POSITION_THREE,
		// Defense.ROUGH_TERRAIN));
		// autoChooser.addObject("Position 4", new
		// TraverseDefenseShootRoutine(AutoPosition.POSITION_FOUR,
		// Defense.ROUGH_TERRAIN));
		// autoChooser.addObject("Position 5", new
		// TraverseDefenseShootRoutine(AutoPosition.POSITION_FIVE,
		// Defense.ROUGH_TERRAIN));
		// SmartDashboard.putData("Autonomous Position Chooser", autoChooser);
		autoTimer = new Timer();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		periodicAll();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	public void autonomousInit() {

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new RawTankDrive(); break; }
		 */
		autoTimer.start();
		autoStartTime = autoTimer.get();
		resetSensors();
		drive.setDesiredHeading(0);
		// choose position
		if (DS2016.pos1Button.get()) {
			System.out.println("Pos 1");
			autoPosition = AutoPosition.LOWBAR_ONE;
			autoDefense = Defense.LOW_BAR;
		} else if (DS2016.pos2Button.get()) {
			System.out.println("Pos 2");
			autoPosition = AutoPosition.POSITION_TWO;
			autoDefense = Defense.TERRAIN;
		} else if (DS2016.pos3Button.get()) {
			System.out.println("Pos 3");
			autoPosition = AutoPosition.POSITION_THREE;
			autoDefense = Defense.TERRAIN;
		} else if (DS2016.pos4Button.get()) {
			System.out.println("Pos 4");
			autoPosition = AutoPosition.POSITION_FOUR;
			autoDefense = Defense.TERRAIN;
		} else if (DS2016.pos5Button.get()) {
			System.out.println("Pos 5");
			autoPosition = AutoPosition.POSITION_FIVE;
			autoDefense = Defense.TERRAIN;
		} else if (DS2016.pos6Button.get()) {
			System.out.println("Pos 6");
			drive.setDesiredHeading(160);
			RobotMap.driveGyro.setAngleOffset(160);
			autonomousCommand = new TwoBallAutonomous();
		}

		if (DS2016.fastButton.get()) {
			System.out.println("Fast");
			autoPower = 0.75;
		} else if (DS2016.medButton.get()) {
			System.out.println("Med");
			autoPower = 0.65;
		} else if (DS2016.slowButton.get()) {
			System.out.println("Slow");
			autoPower = 0.50;
		} else if (DS2016.stayButton.get()) {
			autoPower = NO_AUTO;
		}

		autoShoot = !DS2016.autoShootButton.get();
		// autonomousCommand = ArduinoSerialReader.autoCommand();
		if (autoDefense == null) {
			autoDefense = Defense.TERRAIN;
			autoPower = 0.75;
			autoShoot = false;
			autoPosition = AutoPosition.POSITION_FOUR;
		}
		// drive.setDesiredHeading(160);
		// RobotMap.driveGyro.setAngleOffset(160);
		// autonomousCommand = new TwoBallAutonomous();
		autonomousCommand = new TraverseDefenseShootRoutine(autoPower, autoPosition, autoDefense, autoShoot);
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		RobotMap.positionTracker.update();
		periodicAll();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.

		// CommandBase.drive.initDefaultCommand();
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		SmartDashboard.putData("Raise Shooter Arm to X degrees",
				new RaiseShooterArmTo(CommandBase.preferences.getDouble(PreferenceKeys.SHOOTER_ANGLE, 45)));

		SmartDashboard.putData("Raise Acquirer to X degrees",
				new RaiseAcquirerTo(CommandBase.preferences.getDouble(PreferenceKeys.ACQUIRER_ANGLE, 90)));

		SmartDashboard.putData("Turn Angle to Target", new TurnToVisionTarget(0.6));

		SmartDashboard.putData("Chival Assist", new ChivalAssist());

		SmartDashboard.putData("Move 5 feet forward", new DriveStraightDistance(0.8, 5, 1.0 / 12));

		SmartDashboard.putData("Turn to target", new TurnToVisionTargetContinuous());

		SmartDashboard.putData("Shoot sequence", new ShootSequence(true, false));

		SmartDashboard.putData("Turn to heading 90 dumb", new TurnToTargetDumb(90, 0.6));

		SmartDashboard.putData("Wait for RPM", new WaitForRPM(2000, 20));

		SmartDashboard.putData("Turn 90 degrees", new TurnAngle(90, 0.6));

		SmartDashboard.putData("Turn 15 degrees", new TurnAngle(15, 0.6));

		SmartDashboard.putData("Switch To Area Calculation", new Command() {
			@Override
			protected void initialize() {
			}

			@Override
			protected void execute() {
				CommandBase.visionProcessing.switchToCalcDistanceFromArea();
			}

			@Override
			protected boolean isFinished() {
				return true;
			}

			@Override
			protected void end() {
			}

			@Override
			protected void interrupted() {
			}

		});
		SmartDashboard.putData("Switch to Height Calculation", new Command() {
			@Override
			protected void initialize() {
			}

			@Override
			protected void execute() {
				CommandBase.visionProcessing.switchToCalcDistanceFromHeight();
			}

			@Override
			protected boolean isFinished() {
				return true;
			}

			@Override
			protected void end() {
			}

			@Override
			protected void interrupted() {
			}
		});
		// SmartDashboard.putData("Turn set angle to target", new
		// TurnAngle(visionProcessing.getTurningAngle(), 0.7));

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("left encoder", RobotMap.leftMotorEncoder.get());
		SmartDashboard.putNumber("right encoder", RobotMap.rightMotorEncoder.get());
		SmartDashboard.putBoolean("Has Ball", shooterWheel.isBallLoaded());
		RobotMap.positionTracker.update();
		periodicAll();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
		periodicAll();
	}

	public void periodicAll() {
		// SmartDashboard.putNumber("Periodic all in nanos",
		// shooterWheel.currentTimeNanos());
		// NavXTester.parameterDisplay();

		// PositionTracker.updatePosition();
		// PositionTracker3D.computePosition();
		if (true) {
			SmartDashboard.putNumber("Left RPM", shooterWheel.currentLeftRPM);
			SmartDashboard.putNumber("Right RPM", shooterWheel.currentRightRPM);
			SmartDashboard.putNumber("Arm Encoder Value", RobotMap.armAngleEncoder.getVoltage());
			SmartDashboard.putNumber("Arm Angle", acquirerArm.degreesFromVolts(RobotMap.armAngleEncoder.getVoltage()));
			SmartDashboard.putNumber("Shooter Angle Value",
					shooterArm.degreesFromVolts(RobotMap.shooterLifterEncoder.getVoltage()));
			SmartDashboard.putNumber("Shooter Encoder Value", RobotMap.shooterLifterEncoder.getVoltage());
			SmartDashboard.putNumber("Left Shooter Encoder", RobotMap.leftShooterWheelEncoder.get());
			SmartDashboard.putNumber("Right Shooter Encoder", RobotMap.rightShooterWheelEncoder.get());

			// SmartDashboard.putNumber("Distance",
			// visionProcessing.calcDistance());
			SmartDashboard.putNumber("Shooting Angle", visionProcessing.getShootingAngle());
			// SmartDashboard.putString("Shooting Angle String",
			// Double.toString(visionProcessing.getShootingAngle()));

			// SmartDashboard.putNumber("Turning Angle",
			// visionProcessing.getTurningAngle());

			// SmartDashboard.putNumber("Turning Angle Arcsin",
			// visionProcessing.getTurningAngle());
			// SmartDashboard.putNumber("Turning Angle Proportion",
			// visionProcessing.getTurningAngleProportion());

			SmartDashboard.putNumber("Gyro", RobotMap.driveGyro.getAngle());
			// SmartDashboard.putNumber("Robot X",
			// RobotMap.positionTracker.getX());
			// SmartDashboard.putNumber("Robot Y",
			// RobotMap.positionTracker.getY());

			SmartDashboard.putBoolean("Upper Limit", RobotMap.acquireUpperLimit.get());
			SmartDashboard.putBoolean("Lower Limit", RobotMap.acquireLowerLimit.get());
			SmartDashboard.putBoolean("Robot Flat", Math.abs(RobotMap.ahrs.getRoll()) < 2.7);
			// SmartDashboard.putNumber("Robot Roll", RobotMap.ahrs.getRoll());

			SmartDashboard.putNumber("Offset CenterX",
					visionProcessing.centerX - CommandBase.preferences.getDouble(PreferenceKeys.CENTER_IMAGE, 156.5));
			/*
			 * SmartDashboard.putData(CommandBase.acquirerArm);
			 * SmartDashboard.putData(CommandBase.acquirerWheel);
			 * SmartDashboard.putData(CommandBase.drive);
			 * SmartDashboard.putData(CommandBase.shooterArm);
			 * SmartDashboard.putData(CommandBase.shooterBar);
			 * SmartDashboard.putData(CommandBase.shooterWheel);
			 */

			try {
				SmartDashboard.putData("PDP", pdp);
			} catch (Exception e) {
				// Silently ignore the exception
			}
			// for (int i = 0; i <= 15; i++) {
			// SmartDashboard.putNumber("PDP current " + i, pdp.getCurrent(i));
			// }
			// SmartDashboard.putNumber("PDP Total Voltage", pdp.getVoltage());
			// SmartDashboard.putData("ShooterRampUp", new ShooterRampUp(true));
		}
		screenUpdateCounter++;
	}

	public void resetSensors() {
		RobotMap.ahrs.resetDisplacement();
		RobotMap.driveGyro.reset();
		RobotMap.leftMotorEncoder.reset();
		RobotMap.leftShooterWheelEncoder.reset();
		RobotMap.rightMotorEncoder.reset();
		RobotMap.rightShooterWheelEncoder.reset();
	}
}
