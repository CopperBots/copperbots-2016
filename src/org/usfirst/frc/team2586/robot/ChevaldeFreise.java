package org.usfirst.frc.team2586.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChevaldeFreise extends Command {

	private Timer time;
	private Talon talonLeft;
	private Talon talonRight;
	private Solenoid soleLiftDown;
	//private Solenoid soleLiftUp;

	public ChevaldeFreise(Talon left, Talon right, Solenoid up, Solenoid down) {
		talonLeft = left;
		talonRight = right;
		soleLiftDown = up;
		//soleLiftUp = down;

	}

	// Called just before this Command runs the first time
	protected void initialize() {

		time = new Timer();

		time.reset();
		time.start();

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		if (time.get() < 3.0) {
			talonRight.set(1);
			talonLeft.set(1);
		} else if (time.get() < 5.0) {
			talonLeft.set(0);
			talonRight.set(0);
			soleLiftDown.set(true);
		} else if (time.get() < 8.0) {
			talonLeft.set(1);
			talonRight.set(1);
			soleLiftDown.set(false);
		} else {
			talonLeft.set(0);
			talonRight.set(0);

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
