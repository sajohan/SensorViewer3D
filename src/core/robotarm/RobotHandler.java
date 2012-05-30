package core.robotarm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import core.model.Point3Dim;
import core.model.SensorType;
import core.model.SensorValue;
import core.model.SensorValues;

import view.guicomponents.GUI;



/**
 * Title: Handler for the robotarm 
 * Description: Communicates with the robot to populate the sensordata datastructure.
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

	/**
	 * Reads a group of sensorvalues. Puts it in the sensorvalue datastructure. 
	 * @param points The points that should be read
	 * @return SensorValues The datastructure containing the read values
	 */
	public SensorValues readSensorGroup(Point3Dim[] points) {

		//TODO Make several sensorreadings
		throw new UnsupportedOperationException();
	}

	/**
	 * Reads a single sensorvalue. Called by readSensorGroup
	 * @param x read at this x-position
	 * @param y	read at this y-position
	 * @param z	read at this z-position
	 * @return SensorValue The read SensorValue
	 */
	public SensorValue readSingleSensor(float x, float y, float z, float xNormal, float yNormal, float zNormal) {

		// Send Position to robot
		robotGoTo(new Point3Dim(x,y,z), xNormal, yNormal, zNormal);
		
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
	/**
	 * Asks the robot for its position
	 * @return The position of the robot
	 */
	public Point3Dim getRobotPos() {
		
		// Request calibration point
		String t = new String("REQ_CAL");
		serialCom.writeString(t);
		
		// Wait for response from robot, time out if no response
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
	}
	
	/**
	 * @param byteData
	 * @return float
	 * @deprecated Strings are sent instead of floats
	 */
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
	
	/**
	 * Connects to a serialport to prepare for transmission
	 * @param comPort The port to connect to
	 */
	public void connect(String comPort) {
		try {
			serialCom = new SerialCom(this);
			serialCom.connect(comPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses the read data to see what type of sensor it is.
	 * @return The read SensorValue.
	 */
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
				s = readMagnetic(data);
			}
			
		}
		return s;
	}
	/**
	 * Parse a temperature sensor string
	 * @param data The string containing the data
	 * @return A temperature sensorvalue
	 */
	public SensorValue readTemperature(String[] data) {
		Float d1 = new Float(data[2]);
		return new SensorValue((float) lastRobotPos.x, (float) lastRobotPos.y,
				(float) lastRobotPos.z, d1, SensorType.TEMP);
	}
	/**
	 * Parse a magnetic sensor string
	 * @param data The string containing the data
	 * @return A magnetic sensorvalue
	 */
	public SensorValue readMagnetic(String[] data) {
		Float d1 = new Float(data[2]);
		return new SensorValue((float) lastRobotPos.x, (float) lastRobotPos.y,
				(float) lastRobotPos.z, d1, SensorType.MAGNETIC);
	}
	/**
	 * Commands the robot to go to a position and read a value.
	 * @param point The position to go to.
	 * @param zNormal 
	 * @param yNormal 
	 * @param xNormal 
	 */
	public void robotGoTo(Point3Dim point, float xNormal, float yNormal, float zNormal) {
		String t = new String("GOTO_POS;"+point.x+";"+point.y+";"+point.z+";"+xNormal+";"+yNormal+";"+zNormal);
		System.out.println(t);
		serialCom.writeString(t);
	}
	
	/**
	 * Wait for response from robot
	 * @return false if inData is still null. true if robot responded
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
		}
		return true;
	}

	public void setInData(byte[] inData) {
		this.inData = inData;
	}



}
