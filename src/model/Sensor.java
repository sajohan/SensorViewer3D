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

	public Sensor() {

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
}
