package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.vecmath.Point3d;

/**
 * Title: SensorValues Description: A class that holds values read by sensor
 * 
 * @author dannic
 * 
 */

public class SensorValues extends Observable implements Serializable{

	// Map of points connected to values
	//	private HashMap<Point3d, Float> map;
	private ArrayList<SensorValue> l;
	private Float min,max;
	/**
	 * @param	Observer	obs	the object to observe new sensor values added
	 */
	public SensorValues(Observer obs) {
		/* TODO
		 * HARDCODED VALUES
		 */
		min = 0f;
		max = 255f;
		//		map = new HashMap<Point3d, Float>();
		l = new ArrayList<SensorValue>();
		this.addObserver(obs);
	}

	public void setValuesList(ArrayList<SensorValue> l) {
		this.l = l;
	}
	public void addValueToList(SensorValue s) {
		if(s!=null){
			this.l.add(s);
			this.setChanged();
			this.notifyObservers(s);
			float value = s.getValue();
			//store min, max values
			//			if(min == null){
			//				min = max = value;
			//			}
			//			else if( value < min ){
			//				min = value;
			//			}
			//			else if( value > max ){
			//				max = value;
			//			}
		}
	}

	public ArrayList<SensorValue> getValuesList() {
		return l;
	}

	public void removeValue(SensorValue s) {
		if(l.remove(s)){
			System.out.println("Successfully removed object from list");
			this.setChanged();
			this.notifyObservers(s);
		}
	}




	//
	//	public HashMap<Point3d, Float> getMap() {
	//		return map;
	//	}
	//
	//	public void setMap(HashMap<Point3d, Float> map) {
	//		this.map = map;
	//	}
	//
	//	public void addValue(Point3d key, Float value) {
	//		// TODO Check if it a good value?
	//		map.put(key, value);
	//	}
	//
	//	public Float getValue(Point3d key) {
	//		Float val = map.get(key);
	//		if (val != null) {
	//			return val;
	//		} else {
	//			return null;
	//		}
	//	}
}
