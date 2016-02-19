package org.usfirst.frc.team948.robot.utilities;

import org.usfirst.frc.team948.robot.DS2016;
import org.usfirst.frc.team948.robot.commandgroups.AcquireMode;

import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.Button;

public class XboxTriggerButton extends Button {

	int axisNumber;

	public XboxTriggerButton(int axisNumber) {
		this.axisNumber = axisNumber;
	}

	@Override
	public boolean get() {
		return DS2016.xBoxController.getRawAxis(axisNumber) > 0.5;
	}
}
