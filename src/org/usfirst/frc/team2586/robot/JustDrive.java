package org.usfirst.frc.team2586.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JustDrive extends Command {

	private Timer time;
	private Talon talonLeft;
	private Talon talonRight;

	public JustDrive(Talon left, Talon right) {
	//name thy talons
		talonLeft = left;
		talonRight = right;

	}

	// init the timer and reset.start the thing
	protected void initialize() {
		time = new Timer();

		time.reset();
		time.start();

	}

	// if not ten go all
	// if not ten then go not at all
	protected void execute() {

		if (time.get() < 10.0) {
			talonRight.set(1);
			talonLeft.set(1);

		} else {
			talonRight.set(0);
			talonLeft.set(0);

		}

	}

	// Make this return true when this Command no longer needs to run execute()
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
