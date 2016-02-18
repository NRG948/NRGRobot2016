package org.usfirst.frc.team948.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team948.robot.commands.Interrupt;
import org.usfirst.frc.team948.robot.commands.RaiseShooterToNextLowerAngle;
import org.usfirst.frc.team948.robot.commands.ManualAcquire;
import org.usfirst.frc.team948.robot.commands.ManualClimb;
import org.usfirst.frc.team948.robot.commands.ManualDrawbridge;
import org.usfirst.frc.team948.robot.commands.ManualDrive;
import org.usfirst.frc.team948.robot.commands.ManualDriveStraight;
import org.usfirst.frc.team948.robot.commands.ManualRaiseAcquirer;
import org.usfirst.frc.team948.robot.commands.RaiseShooterToNextHigherAngle;
import org.usfirst.frc.team948.robot.commands.RampToRPM;
import org.usfirst.frc.team948.robot.commands.RawRaise;
import org.usfirst.frc.team948.robot.commands.ResetSensors;
import org.usfirst.frc.team948.robot.commands.Shoot;
import org.usfirst.frc.team948.robot.commands.ShooterRampUp;

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
	public static final Joystick rightJoystick = new Joystick(2);
	public static final Button driveStraightButton = new JoystickButton(leftJoystick, 1);
	public static final Button raiseShooterArmButton = new JoystickButton(leftJoystick, 3);
	public static final Button lowerShooterArmButton = new JoystickButton(leftJoystick, 2);
	public static final Button resetSensorsButton = new JoystickButton(leftJoystick, 6);
	public static final Button acquireButton = new JoystickButton(rightJoystick, 2);
	public static final Button raiseAcquirerButton = new JoystickButton(leftJoystick, 4);
	public static final Button lowerAcquirerButton = new JoystickButton(leftJoystick, 5);
	public static final Button shootButton = new JoystickButton(rightJoystick, 1);
	public static final Button extendDrawbridgeButton = new JoystickButton(rightJoystick, 3);
	public static final Button retractDrawbridgeButton = new JoystickButton(rightJoystick, 4);
	public static final Button extendTapeMeasureButton = new JoystickButton(rightJoystick, 5);
	public static final Button climbUpButton = new JoystickButton(rightJoystick, 6);
	public static final Button shooterRampUp = new JoystickButton(rightJoystick, 10);
	public static final Joystick xBoxController = new Joystick(3);
	public static final Button xboxAButton = new JoystickButton(xBoxController, 1);
	public static final Button xboxBButton = new JoystickButton(xBoxController, 2);
	public static final Button xboxYButton = new JoystickButton(xBoxController, 4);
	public static final Button xboxLBumper = new JoystickButton(xBoxController, 5);
	public static final Button xboxRBumper = new JoystickButton(xBoxController, 6);
	public static final Button RPMButton = new JoystickButton(rightJoystick, 8);
	
	// We need to change the button numbers later
	public static double getLeftJSY() {		
		return leftJoystick.getY();
	}
	public static double getRightJSY() {
		// TODO Auto-generated method stub
		return rightJoystick.getY();
	}
    
 	public static void buttonInit() {
  		driveStraightButton.whenPressed(new ManualDriveStraight());
 		driveStraightButton.whenReleased(new ManualDrive());
 		resetSensorsButton.whenPressed(new ResetSensors());
// 		raiseAcquirerButton.whileHeld(new ManualRaiseAcquirer(0.33));//MAY NEED TO CHANGE LATER
// 		lowerAcquirerButton.whileHeld(new ManualRaiseAcquirer(-0.33));//MAY NEED TO CHANGE LATER
 		shootButton.whenPressed(new Shoot());
// 		shootButton.whenReleased(new ShooterRampUp());
 		//shootButton.whenReleased(new ShooterRampUp());
 		xboxAButton.whileHeld(new ManualAcquire(true));
 		extendDrawbridgeButton.whileHeld(new ManualDrawbridge(true));
 		retractDrawbridgeButton.whileHeld(new ManualDrawbridge(false));
 		extendTapeMeasureButton.whileHeld(new ManualClimb(true));
 		climbUpButton.whileHeld(new ManualClimb(false));
 		xboxRBumper.whenPressed(new RaiseShooterToNextHigherAngle());
 		xboxLBumper.whenPressed(new RaiseShooterToNextLowerAngle());
 		shooterRampUp.whileHeld(new ShooterRampUp(1));
 		//shooterRampUp.whenReleased(new ShooterRampUp(0));
 		xboxYButton.whileHeld(new ManualRaiseAcquirer(0.6));
 		xboxBButton.whileHeld(new ManualRaiseAcquirer(-0.6));
 		RPMButton.whileHeld(new RampToRPM(2000));
 		shootButton.whenReleased(new Interrupt());
 	}
 	
 	
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new RawTankDrive());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new RawTankDrive());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new RawTankDrive());
	
	
}

