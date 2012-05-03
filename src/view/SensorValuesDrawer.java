package view;

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

import model.SensorValue;
import model.SensorValues;

import com.sun.j3d.utils.geometry.Sphere;

public class SensorValuesDrawer extends BranchGroup{

	public float transparency = 0.3f; // transparency of values
	
	public ArrayList<SensorRepresentation> sensorRepList;
	public SensorValuesDrawer(){
		sensorRepList = new ArrayList<SensorRepresentation>();
	}

	/**
	 * Draws a sphere to represent a sensor value
	 * @param	x	position x of quantity
	 * @param	y	position y of quantity
	 * @param	z	position z of quantity
	 * @param	a	measured amplitude of quantity
	 */
	public void drawSphere(SensorValue sensVal){
		Appearance ap = new Appearance();
		//		RenderingAttributes ra = new RenderingAttributes();
		//		ra.setAlphaTestValue(0.5f);
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
