package org.usfirst.frc.team948.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwitchCamera extends CommandBase {

    public SwitchCamera() {
        requires(visionProcessing);
    }

    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	visionProcessing.switchMode();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    	end();
    }
}
