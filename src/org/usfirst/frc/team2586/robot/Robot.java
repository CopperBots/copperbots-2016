package org.usfirst.frc.team2586.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.command.Command;
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
	private static final int LEFT_DRIVE_PWM = 1;
	private static final int RIGHT_DRIVE_PWM = 2;
	private static final int LEFT_JOYSTICK_PORT = 1;
	private static final int RIGHT_JOYSTICK_PORT = 2;
	private static final int LIFT_SOLENOID_UP_CHANNEL = 1;
	private static final int LIFT_SOLENOID_DOWN_CHANNEL = 2;

	private static final int XBAX_JOYSTICK_PORT = 1;
	private static final int XBAX_CAM_PORT = 1;

	private Command autoCommand;
	private SendableChooser autoSelect;

	private Talon talonLeft;
	private Talon talonRight;
	private Joystick joyLeft;
	private Joystick joyRight;
	private Solenoid soleLiftUp;
	private Solenoid soleLiftDown;

	private Joystick xbaxJoy;
	private CameraServer xbaxCam;
	private Servo camServoX;
	private Servo camServoY;

	public void robotInit() {
		talonLeft = new Talon(LEFT_DRIVE_PWM);
		talonRight = new Talon(RIGHT_DRIVE_PWM);
		joyLeft = new Joystick(LEFT_JOYSTICK_PORT);
		joyRight = new Joystick(RIGHT_JOYSTICK_PORT);
		soleLiftUp = new Solenoid(LIFT_SOLENOID_UP_CHANNEL);
		soleLiftDown = new Solenoid(LIFT_SOLENOID_DOWN_CHANNEL);

		xbaxJoy = new Joystick(XBAX_JOYSTICK_PORT);
		//xbaxCam = new CameraServer(XBAX_CAM_PORT);


		autoSelect = new SendableChooser();
		autoSelect.addDefault("Just Drive",
				new JustDrive(talonLeft, talonRight));
		autoSelect.addObject("Portculis", new Portculis(talonLeft, talonRight,
				soleLiftUp, soleLiftDown));
		autoSelect.addObject("Cheval de Freise", new ChevaldeFreise(talonLeft,
				talonRight, soleLiftDown, soleLiftUp));
		autoSelect.addObject("Sally Port Door", new SallyPortDoor(talonLeft,
				talonRight, soleLiftDown));
		autoSelect.addObject("Drawbridge", new Drawbridge(talonLeft,
				talonRight, soleLiftDown, soleLiftUp));

		SmartDashboard.putData(autoCommand);
		SmartDashboard.putData("Just Drive", new JustDrive(talonLeft,
				talonRight));
		SmartDashboard.putData("Portculis", new Portculis(talonLeft,
				talonRight, soleLiftDown, soleLiftUp));
		SmartDashboard.putData("Cheval de Freise", new ChevaldeFreise(
				talonLeft, talonRight, soleLiftDown, soleLiftUp));
		SmartDashboard.putData("Sally Port Door", new SallyPortDoor(talonRight,
				talonLeft, soleLiftDown));
		SmartDashboard.putData("Drawbridge", new Drawbridge(talonRight,
				talonLeft, soleLiftDown, soleLiftUp));

		xbaxCam = CameraServer.getInstance();
		xbaxCam.setQuality(50);
		xbaxCam.startAutomaticCapture("cam0");
	}

	/**
	 * S This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

		autoCommand = (Command) autoSelect.getSelected();
		autoCommand.start();

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {

		double joyChannelLeft = valueWithDeadzone(
				joyLeft.getRawAxis(Joystick.AxisType.kY.value), 0.1d);
		double joyChannelRight = valueWithDeadzone(
				joyRight.getRawAxis(Joystick.AxisType.kY.value), 0.1d);

		double xbaxRX = xbaxJoy.getRawAxis(4);
		double xbaxRY = xbaxJoy.getRawAxis(5);
		double lTrigger = xbaxJoy.getRawAxis(2);
		double rTrigger = xbaxJoy.getRawAxis(3);

		double camX = xbaxJoy.getRawAxis(1);
		camServoX.set(camX);
		double camY = xbaxJoy.getRawAxis(0);
		camServoY.set(camY);
		

		SmartDashboard.putNumber("Left Joystick Value", joyChannelLeft);
		SmartDashboard.putNumber("Right Joystick Value", joyChannelRight);

		talonLeft.set(joyChannelLeft);
		talonRight.set(joyChannelRight);

		// soleLiftUp.set(joyLeft.getButton(ButtonType.kTrigger));

		if (lTrigger > 0.6) {
			camServoX.set(0.45);
		} else {
			camServoX.set(xbaxRX / 4 + 0.45);
		}

		if (rTrigger > 0.6) {
			camServoY.set(0.5);
		} else {
			camServoY.set((-xbaxRY / 4) + 0.5);
		}

		if (joyLeft.getButton(ButtonType.kTrigger)) {
			soleLiftUp.set(true);
			soleLiftDown.set(false);

		} else {
			soleLiftDown.set(true);
			soleLiftUp.set(false);
		}
	}

	public double valueWithDeadzone(double in, double dead) {
		if (-dead < in && in < dead)
			return 0;
		if (in < 0) {
			return in + dead;
		} else {
			return in - dead;
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
