package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.vecmath.Point3d;

/**
 * Title: SensorValues Description: A class that holds values read by sensor
 * 
 * @author dannic
 * 
 */

public class SensorValues implements Serializable {

	// Map of points connected to values
//	private HashMap<Point3d, Float> map;
	private ArrayList<SensorValue> l;
	
	public SensorValues() {
//		map = new HashMap<Point3d, Float>();
		l = new ArrayList<SensorValue>();
	}

	public void setValuesList(ArrayList<SensorValue> l) {
		this.l = l;
	}
	public void addValueToList(SensorValue s) {
		this.l.add(s);
	}

	public ArrayList<SensorValue> getValuesList() {
		return l;
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
