package org.usfirst.frc.team2586.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drawbridge extends Command {

	private Timer time;
	private Talon talonLeft;
	private Talon talonRight;
	private Solenoid soleLiftDown;

	// private Solenoid soleLiftUp;

	public Drawbridge(Talon left, Talon right, Solenoid up, Solenoid down) {

		talonLeft = left;
		talonRight = right;
		// soleLiftDown = up;
		soleLiftDown = down;

	}

	protected void initialize() {

		time = new Timer();

		time.reset();
		time.start();

	}

	protected void execute() {

		if (time.get() < 3.0) {
			talonLeft.set(1);
			talonRight.set(1);
		} else if (time.get() < 5.0) {
			talonLeft.set(0);
			talonRight.set(0);
			soleLiftDown.set(true);
		} else if (time.get() < 6.0) {
			talonLeft.set(-1);
			talonRight.set(-1);
		} else
			talonLeft.set(1);
		talonRight.set(1);
	}

	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
