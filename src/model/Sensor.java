package model;

import java.io.Serializable;

public class Sensor implements Serializable {

	/**
	 * Title: Sensor Description: A class that controls the sensor
	 * 
	 * @author dannic
	 * 
	 */

	private SensorValues values;
	private String name;
	private float rangeMin;
	private float rangeMax;
	

	public Sensor(String name, float min, float max) {
		
		this.name = name;
		this.rangeMin = min;
		this.rangeMax = max;
		
		// Init reading
		// Serial flow from hardware

		values = new SensorValues();
	}

	public SensorValues getValuesMap() {
		return values;
	}

	public void setValuesMap(SensorValues values) {
		this.values = values;
	}
	
	public String toString(){
		return "Sensor: " + name + " " + rangeMin + " " + rangeMax;
	}
}
