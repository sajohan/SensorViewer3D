package core.robotarm;

import java.io.InputStream;

import model.Point3Dim;
import model.SensorValue;
import model.SensorValues;


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
	
	
	public RobotHandler(SensorValues values){
		this.values = values;
	}


	
	
	/* Reads a group of sensorvalues. Puts it in the sensorvalue datastructure. */
	public SensorValues readSensorGroup(Point3Dim[] points){

        for(Point3Dim point : points){
        	values.addValueToList(readSingleSensor((float)point.x, (float)point.y, (float)point.z));
        	System.out.println("number of sensorvalues  "+ values.getValuesList().size());
//        	values.addValueToList(new SensorValue(5,5,5,5));
		}
		return values;
	}
	
	/* Reads a single sensorvalue. Called by readSensorGroup */
	public SensorValue readSingleSensor(float x, float y, float z){
		
		
		while(inData == null){
			//Do nothing
		}
		
		float value = getFloat(inData);
		System.out.println(value);
		
		SensorValue s = new SensorValue(x, y, z, value);
		inData = null; //reset indata after having read it
		
		return s;
	}
	
	public Point3Dim getRobotPos(){
		//TODO read robotposition
		return new Point3Dim(1, 1, 2);
	}
	
	private float getFloat(byte[] byteData){
		String stringData = new String(byteData);
		try{
			float number  = new Float(stringData); 
			return number;
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		Float fl = null;
		return fl;
	}
	
	public void setInData(byte[] inData) {
		this.inData = inData;
		commandParser();
	}

	public void connect(String comPort) {
        try{
        	serialCom = new SerialCom(this);
            serialCom.connect(comPort);
        }catch ( Exception e ){
            e.printStackTrace();
        }
	}
	
	public void commandParser(){
		
		if(inData == null){
			//Do nothing
			return;
		}
		
		String stringData = new String(inData);
		
		/* Is it calibration value? */
		if(stringData.startsWith("CAL")){
			String[] data = stringData.split(";");
			Double d1 = new Double(data[1]);
			Double d2 = new Double(data[2]);
			Double d3 = new Double(data[3]);
			
			Point3Dim point = new Point3Dim(d1,d2,d3);
		}
		
		/* Is it sensor value? */
		else if(stringData.startsWith("VAL")){
			String[] data = stringData.split(";");
			if(data[0].equals("TEMP")){
				// Temperature
				Float d1 = new Float(data[2]);
				SensorValue s = new SensorValue((float)lastRobotPos.x, (float)lastRobotPos.y, (float)lastRobotPos.z, d1);
				values.addValueToList(s);
			}
			else if(data[0].equals("MAGN")){
				// Magnetic
				// Do something else
			}
			
		}
		
		
	}
	public void goToPos(Point3Dim point){
		// Save pos
		lastRobotPos = point;
		// Send stringcommand to make robot to go to (X,Y,Z)
		
	}
	
}
