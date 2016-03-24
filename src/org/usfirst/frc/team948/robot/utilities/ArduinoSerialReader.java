package org.usfirst.frc.team948.robot.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.usfirst.frc.team948.robot.Robot.AutoPosition;
import org.usfirst.frc.team948.robot.Robot.Defense;
import org.usfirst.frc.team948.robot.commandgroups.TraverseDefenseShootRoutine;

import edu.wpi.first.wpilibj.command.Command;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class ArduinoSerialReader implements SerialPortEventListener {
	SerialPort serialPort;
	/** The port we're normally going to use. */
	private BufferedReader input;
	private OutputStream output;
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 9600;
	private static String inputLine = "";

	public void initialize() {
		CommPortIdentifier portId = null;
		try {
			portId = CommPortIdentifier.getPortIdentifier("COM3");
		} catch (NoSuchPortException e1) {
			System.out.println("Could not find COM port.");
			e1.printStackTrace();
			return;
		}

		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				if (input.ready()) {
					inputLine = input.readLine();
					System.out.println(inputLine);
				}

			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other
		// ones.
	}

	public static void startCapture() {
		ArduinoSerialReader main = new ArduinoSerialReader();
		main.initialize();
		Thread t = new Thread() {
			public void run() {
				// the following line will keep this app alive for 1000 seconds,
				// waiting for events to occur and responding to them (printing
				// incoming messages to console).
				try {
					Thread.sleep(1000000);
				} catch (InterruptedException ie) {
				}
			}
		};
		t.start();
		System.out.println("Started Capture of Serial");
	}
	
	public static String getData(){
		return inputLine;
	}
	
	public static Command autoCommand(){
		Command c = new TraverseDefenseShootRoutine(AutoPosition.POSITION_FIVE, Defense.MOAT);
		return c;
	}
}