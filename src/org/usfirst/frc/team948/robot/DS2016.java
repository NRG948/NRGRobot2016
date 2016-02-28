package org.usfirst.frc.team948.robot;

import org.usfirst.frc.team948.robot.commandgroups.AcquireMode;
import org.usfirst.frc.team948.robot.commandgroups.MoveandRamp;
import org.usfirst.frc.team948.robot.commandgroups.ShootSequence;
import org.usfirst.frc.team948.robot.commandgroups.SpitOutSequence;
import org.usfirst.frc.team948.robot.commands.CommandBase;
import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.ManualAcquire;
import org.usfirst.frc.team948.robot.commands.ManualClimb;
import org.usfirst.frc.team948.robot.commands.ManualDrive;
import org.usfirst.frc.team948.robot.commands.ManualDriveStraight;
import org.usfirst.frc.team948.robot.commands.ManualRaiseAcquirer;
import org.usfirst.frc.team948.robot.commands.ManualTrackAcquirer;
import org.usfirst.frc.team948.robot.commands.MoveDrawbridgeToEnd;
import org.usfirst.frc.team948.robot.commands.RaiseAcquirerTo;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.RawRaiseDrawbridge;
import org.usfirst.frc.team948.robot.commands.ResetSensors;
import org.usfirst.frc.team948.robot.commands.Shoot;
import org.usfirst.frc.team948.robot.commands.ShooterRampUp;
import org.usfirst.frc.team948.robot.commands.SwitchCamera;
import org.usfirst.frc.team948.robot.utilities.XboxTriggerButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class DS2016 {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
	
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
	public static final Joystick leftJoystick = new Joystick(1);
	public static final Button driveStraightButton = new JoystickButton(leftJoystick, 1);
	public static final Button lowerShooterArmButton = new JoystickButton(leftJoystick, 2);

	public static final Button raiseShooterArmButton = new JoystickButton(leftJoystick, 3);
	public static final Button raiseAcquirerButton = new JoystickButton(leftJoystick, 4);
	public static final Button lowerAcquirerButton = new JoystickButton(leftJoystick, 5);
	public static final Button resetSensorsButton = new JoystickButton(leftJoystick, 6);
	public static final Button cameraButton = new JoystickButton(leftJoystick, 7);
	public static final Button acquireTrackButton = new JoystickButton(leftJoystick, 8);

	public static final Joystick rightJoystick = new Joystick(2);
	public static final Button shootButton = new JoystickButton(rightJoystick, 1);
	public static final Button acquireButton = new JoystickButton(rightJoystick, 2);
	public static final Button extendDrawbridgeButton = new JoystickButton(rightJoystick, 3);
	public static final Button retractDrawbridgeButton = new JoystickButton(rightJoystick, 4);
	public static final Button extendTapeMeasureButton = new JoystickButton(rightJoystick, 5);
	public static final Button climbUpButton = new JoystickButton(rightJoystick, 6);
	public static final Button RPMButton = new JoystickButton(rightJoystick, 8);
	public static final Button shooterRampUp = new JoystickButton(rightJoystick, 10);
	
	public static final Joystick xBoxController = new Joystick(3);
	public static final Button xboxAButton = new JoystickButton(xBoxController, 1);
	public static final Button xboxBButton = new JoystickButton(xBoxController, 2);
	public static final Button xboxXButton = new JoystickButton(xBoxController, 3);
	public static final Button xboxYButton = new JoystickButton(xBoxController, 4);
	public static final Button xboxLBumper = new JoystickButton(xBoxController, 5);
	public static final Button xboxRBumper = new JoystickButton(xBoxController, 6);
	public static final Button xboxSelectButton = new JoystickButton(xBoxController, 7);
	public static final Button xboxStartButton = new JoystickButton(xBoxController, 8);
	public static final Button xboxLTrigger = new XboxTriggerButton(2);
	public static final Button XboxRTrigger = new XboxTriggerButton(3);
	
	// We need to change the button numbers later
	public static double getLeftJSY() {		
		return leftJoystick.getY();
	}
	public static double getRightJSY() {
		return rightJoystick.getY();
	}
    
 	public static void buttonInit() {
  		driveStraightButton.whenPressed(new ManualDriveStraight());
 		driveStraightButton.whenReleased(new ManualDrive());
 		resetSensorsButton.whenPressed(new ResetSensors());
// 		raiseAcquirerButton.whileHeld(new ManualRaiseAcquirer(0.33));//MAY NEED TO CHANGE LATER
// 		lowerAcquirerButton.whileHeld(new ManualRaiseAcquirer(-0.33));//MAY NEED TO CHANGE LATER
 		shootButton.whenPressed(new Shoot(0));
// 		shootButton.whenPressed(new ShootSequence());
// 		shootButton.whenReleased(new ShooterRampUp());
 		//xboxAButton.whileHeld(new ManualAcquire(false));
// 		extendDrawbridgeButton.whileHeld(new RawRaiseDrawbridge(true));
 		extendDrawbridgeButton.whenPressed(new ShootSequence());
 		retractDrawbridgeButton.whileHeld(new RawRaiseDrawbridge(false));
 		extendTapeMeasureButton.whileHeld(new ManualClimb(true));
 		climbUpButton.whileHeld(new ManualClimb(false));
 		xboxRBumper.whenPressed(new MoveandRamp(true , 2700));
 		xboxLBumper.whenPressed(new MoveandRamp(false, 0));
// 		xboxLBumper.whenPressed(new MoveDrawbridgeToEnd());
 		xboxSelectButton.whileHeld(new ManualRaiseAcquirer(-0.25));
 		xboxStartButton.whileHeld(new ManualRaiseAcquirer(0.65));
// 		xboxRBumper.whenPressed(new RaiseShooterToNextHigherAngle());
// 		xboxLBumper.whenPressed(new RaiseShooterToNextLowerAngle());
 		shooterRampUp.whileHeld(new ShooterRampUp(1));
// 		xboxYButton.whileHeld(new ManualRaiseAcquirer(0.6));
// 		xboxBButton.whileHeld(new ManualRaiseAcquirer(-0.6));
 		acquireTrackButton.whenPressed(new ManualTrackAcquirer());
 		RPMButton.whileHeld(new RampToRPM(2700));
 		shootButton.whenReleased(new Interrupt());
 		xboxLTrigger.whenPressed(new AcquireMode());
 		XboxRTrigger.whenPressed(new SpitOutSequence());
 		xboxAButton.whenPressed(new RaiseAcquirerTo(Robot.Level.DEFAULT));
 		xboxBButton.whenPressed(new RaiseAcquirerTo(Robot.Level.CHIVAL));
 		xboxXButton.whenPressed(new RaiseAcquirerTo(Robot.Level.FULL_BACK));
 		xboxYButton.whenPressed(new Interrupt());
 		cameraButton.whenPressed(new SwitchCamera());
 	}	
}

