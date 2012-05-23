package view.graphicscomponents;

import java.util.ArrayList;
import java.util.Iterator;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Node;
import javax.media.j3d.RenderingAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;


import com.sun.j3d.utils.geometry.Sphere;

import core.model.SensorValue;
import core.model.SensorValues;

/**
 * Holds all SensorRepresentation objects which are used to render them to the GraphicsPane
 * @author chrfra
 *
 */
public class SensorValuesDrawer extends BranchGroup{

	public float transparency = 0.3f; // transparency of sensorvalue representations
	
	public ArrayList<SensorRepresentation> sensorRepList;
	
	/**
	 * Creates data structure holding all SensorRepresentation objects
	 */
	public SensorValuesDrawer(){
		sensorRepList = new ArrayList<SensorRepresentation>();
	}

	/**
	 * Draws a sphere
	 * @param	sensVal	the sensorvalue to be represented in the GraphicsPane
	 */
	public void drawSphere(SensorValue sensVal){
		Appearance ap = new Appearance();

		ap.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NICEST,transparency));
		
		float a = sensVal.getValue();
		
		ColoringAttributes color = new ColoringAttributes(new Color3f(a/255,0.0f,(1-(a/255))), 0);
		ap.setColoringAttributes(color);

		SensorRepresentation sphere = new SensorRepresentation(0.2f,100,ap, sensVal);
		sensorRepList.add(sphere);
		
		TransformGroup transformGrp = new TransformGroup();
		Transform3D transform = new Transform3D();

		transform.setTranslation(new Vector3f(sensVal.getX(), sensVal.getY(), sensVal.getZ()));
		transformGrp.setTransform(transform);

		transformGrp.addChild(sphere);
		this.addChild(transformGrp);
	}

	/**
	 * renders all sensor values in argument
	 */
	public void drawSensorValue(SensorValues sensorValues){
		//remove all previously drawn values, then redraw all values left in list
		this.removeAllChildren();
		sensorRepList = new ArrayList<SensorRepresentation>();

		ArrayList<SensorValue> values = sensorValues.getValuesList();
		
		SensorValue s;
		Iterator it = values.iterator();
		
		while(it.hasNext()){
			 s = (SensorValue) it.next();
				drawSphere(s);
		}
		

		
	}
	
	/**
	 * Visualises selection ov ain sphere
	 * @param sv the sensorvalue belonging to said spehere
	 */
	public void selectSphere(SensorValue sv){
		SensorRepresentation s;
		SensorValue tempSens;
		Iterator it = sensorRepList.iterator();
		
		while(it.hasNext()){
			 s = (SensorRepresentation)it.next();
			 tempSens = s.getSensorValue();
			 if(tempSens == sv){
				 s.setSelected(true);
			 }
		}
		
	}
	
	/**
	 * Visualises deselection ov ain sphere
	 * @param sv the sensorvalue belonging to said spehere
	 */
	public void deselectSphere(SensorValue sv){
		SensorRepresentation s;
		SensorValue tempSens;
		Iterator it = sensorRepList.iterator();
		
		while(it.hasNext()){
			 s = (SensorRepresentation)it.next();
			 tempSens = s.getSensorValue();
			 if(tempSens == sv){
				 s.setSelected(false);
			 }
		}
		
	}
	
	/**
	 * Toggles the visibility of argument SensorValue
	 * @param sv
	 */
	public void toggleVisibility(SensorValue sv){
		Iterator it = sensorRepList.iterator();
		SensorRepresentation s;
		SensorValue tempSens;
		while(it.hasNext()){
			s = (SensorRepresentation)it.next();
			tempSens = s.getSensorValue();
			if(tempSens == sv){
				 if(s.isVisible())
					 s.setVisible(false);
				 else
					 s.setVisible(true);
			 }
		}
	}
	
	/**
	 * Visualises hiding ov ain sphere
	 * @param sv the sensorvalue belonging to said spehere
	 */
	public void hideSphere(SensorValue sv){
		SensorRepresentation s;
		SensorValue tempSens;
		Iterator it = sensorRepList.iterator();
		
		while(it.hasNext()){
			 s = (SensorRepresentation)it.next();
			 tempSens = s.getSensorValue();
			 if(tempSens == sv){
				 s.setVisible(false);
			 }
		}
	}
		
	/**
	 * Visualises showing ov ain sphere
	 * @param sv the sensorvalue belonging to said spehere
	 */
	public void showSphere(SensorValue sv){
		SensorRepresentation s;
		SensorValue tempSens;
		Iterator it = sensorRepList.iterator();
		
		while(it.hasNext()){
			 s = (SensorRepresentation)it.next();
			 tempSens = s.getSensorValue();
			 if(tempSens == sv){
				 s.setVisible(true);
			 }
		}
		
	}
	
	
}
