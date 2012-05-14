package core.robotarm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import view.GUI;

import core.Calibrator;

import model.Point3Dim;
import model.SensorType;
import model.SensorValue;
import model.SensorValues;

/**
 * Title: Handler for the robotarm Description: Communicates with the robot to
 * populate the sensordata datastructure.
 * 
 * @author sajohan & dannic
 * @version 1.0
 * 
 */
public class RobotHandler {

	private SerialCom serialCom;
	private InputStream in;
	private SensorValues values;
	private byte[] inData = null;
	private Point3Dim lastRobotPos;
	private Calibrator calibrator;

	public RobotHandler(SensorValues values, Calibrator calibrator) {
		this.values = values;
		this.calibrator = calibrator;
	}

	/* Reads a group of sensorvalues. Puts it in the sensorvalue datastructure. */
	public SensorValues readSensorGroup(Point3Dim[] points) {

		for (Point3Dim point : points) {
			values.addValueToList(readSingleSensor((float) point.x,
					(float) point.y, (float) point.z));
			System.out.println("Number of sensorvalues:  "
					+ values.getValuesList().size());
		}
		return values;
	}

	/* Reads a single sensorvalue. Called by readSensorGroup */
	public SensorValue readSingleSensor(float x, float y, float z) {

		// Send Position to robot
		robotGoTo(new Point3Dim(x,y,z));
		
		SensorValue s = null;
		// Wait for response from robot, time out if no response
		if(!waitForResponse()){
			return null;
		}
		
		s = readValueParser();
		System.out.println("Read value: " + s.getValue());
		inData = null; // reset indata after having read it

		return s;
	}

	public Point3Dim getRobotPos() {
		
		// TestString
		String t = new String("REQ_CAL");
		serialCom.writeString(t);
		//
		//		// Wait for response from robot, time out if no response
		if(!waitForResponse()){
			return null;
		}

		String stringData = new String(inData);

		/* Is it calibration value? */
		if (stringData.startsWith("CAL")) {
			String[] data = stringData.split(";");
			Double d1 = new Double(data[1]);
			Double d2 = new Double(data[2]);
			Double d3 = new Double(data[3]);

			Point3Dim point = new Point3Dim(d1, d2, d3);
			inData = null; // reset indata after having read it
			System.out.println("Read robotvalue: " + point.toString());
			return point;
		} else
			return null;
//		return new Point3Dim(0,0,0);
	}

	public float getFloat(byte[] byteData) {
		String stringData = new String(byteData);
		try {
			float number = new Float(stringData);
			return number;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		Float fl = null;
		return fl;
	}

	public void setInData(byte[] inData) {
		this.inData = inData;
	}

	public void connect(String comPort) {
		try {
			serialCom = new SerialCom(this);
			serialCom.connect(comPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SensorValue readValueParser() {
		SensorValue s = null;
		System.out.println("Parsing value..");

		String stringData = new String(inData);

		/* Is it sensor value? */
		if (stringData.startsWith("VAL")) {
			String[] data = stringData.split(";");
			if (data[1].equals("TEMP")) {
				// Temperature
				s = readTemperature(data);
			} else if (data[1].equals("MAGN")) {
				// Magnetic
				// Do something else
				s = readMagnetic(data);
			}

		}
		return s;
	}

	public SensorValue readTemperature(String[] data) {
		Float d1 = new Float(data[2]);
		return new SensorValue((float) lastRobotPos.x, (float) lastRobotPos.y,
				(float) lastRobotPos.z, d1, SensorType.TEMP);
	}

	public SensorValue readMagnetic(String[] data) {
		return null;
		// TODO Auto-generated method stub

	}

	public void robotGoTo(Point3Dim point) {
		// Save pos
		lastRobotPos = point;
		System.out.println("Position sent to robot X: " + point.x + " Y: " +  point.y + " Z: " + point.z);

		// TODO Construct a string with XYZ and send to SerialCom outputstream
		String s = new String();
	}

	/*
	 * Wait for responese
	 * @return false if inData is still null
	 * 
	 */
	public boolean waitForResponse(){
		int timeout = 100;
		while (inData == null) {
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(timeout>=1000){
				GUI.printErrorToStatus("No repsonse from robot.");
				return false;
			}
			timeout = timeout+100;
//			System.out.println(timeout);
		}
		return true;
	}

}
