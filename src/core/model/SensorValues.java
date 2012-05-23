package core.model;

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
 * @author dannic, simoniv, chrfra
 * 
 */

public class SensorValues extends Observable implements Serializable{

	private ArrayList<SensorValue> l;
	private Float min,max;
	
	
	/**
	 * @param	Observer obs the object to observe new sensor values added
	 */
	public SensorValues(Observer obs) {
		// TODO HARDCODED VALUES
		min = 0f;
		max = 255f;
		l = new ArrayList<SensorValue>();
		this.addObserver(obs);
	}

	public void setValuesList(ArrayList<SensorValue> l) {
		this.l = l;
	}
	
	/**
	 * Adds a single sensorvalue to the list. Notifies main of change.
	 * @param s SensorValue to be added
	 */
	public void addValueToList(SensorValue s) {
		if(s!=null){
			this.l.add(s);
			this.setChanged();
			this.notifyObservers(s);
			float value = s.getValue();
		}
	}
	/**
	 * Removes a SensorValue from the list and notifies main.
	 * @param s SensorValue to be removed 
	 */
	public void removeValue(SensorValue s) {
		if(l.remove(s)){
			System.out.println("Successfully removed object from list");
			this.setChanged();
			this.notifyObservers(s);
		}
	}

	public ArrayList<SensorValue> getValuesList() {
		return l;
	}

}
