package org.usfirst.frc.team948.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team948.robot.commands.ManualAcquire;
import org.usfirst.frc.team948.robot.commands.ManualDrive;
import org.usfirst.frc.team948.robot.commands.ManualDriveStraight;
import org.usfirst.frc.team948.robot.commands.ResetSensors;
import org.usfirst.frc.team948.robot.commands.Shoot;

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
	public static final Button resetSensorsButton = new JoystickButton(leftJoystick, 6);
	public static final Button acquireButton = new JoystickButton(rightJoystick, 2);
	public static final Button raiseAcquirerButton = new JoystickButton(leftJoystick, 4);
	public static final Button lowerAcquirerButton = new JoystickButton(leftJoystick, 5);
	public static final Button shootButton = new JoystickButton(rightJoystick, 1);
	
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
 		//raiseAcquirerButton.whileHeld(new ManualRaiseAcquirer());
 		//lowerAcquirerButton.whileHeld(new ManualRaiseAcquirer());
 		shootButton.whenPressed(new Shoot());
 		acquireButton.whileHeld(new ManualAcquire());
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

