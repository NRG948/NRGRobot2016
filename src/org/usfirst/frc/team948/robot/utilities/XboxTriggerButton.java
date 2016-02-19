package org.usfirst.frc.team948.robot.utilities;

import edu.wpi.first.wpilibj.buttons.Button;

public class XboxTriggerButton extends Button {

	int axisNumber;

	public XboxTriggerButton(int axisNumber) {
		this.axisNumber = axisNumber;
	}

	@Override
	public boolean get() {
		return (axisNumber > 0.5);
	}
}
