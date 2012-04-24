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
	
	
	public RobotHandler(SensorValues values){
		this.values = values;
	}


	
	
	/* Reads a group of sensorvalues. Puts it in the sensorvalue datastructure. */
	public SensorValues readSensorGroup(Point3Dim[] points, String comPort){
		
        try{
        	serialCom = new SerialCom(this);
            serialCom.connect(comPort);
        }catch ( Exception e ){
            e.printStackTrace();
        }
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
//		read
	}
	
}
