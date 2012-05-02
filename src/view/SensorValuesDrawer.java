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

	public SensorValuesDrawer(){

	}

	/**
	 * Draws a sphere to represent a sensor value
	 * @param	x	position x of quantity
	 * @param	y	position y of quantity
	 * @param	z	position z of quantity
	 * @param	a	measured amplitude of quantity
	 */
	public void drawSphere(float x, float y, float z, float a){
		Appearance ap = new Appearance();
		//		RenderingAttributes ra = new RenderingAttributes();
		//		ra.setAlphaTestValue(0.5f);
		ap.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NICEST,transparency));

		ColoringAttributes color = new ColoringAttributes(new Color3f(a/255,0.0f,(1-(a/255))), 0);
		ap.setColoringAttributes(color);

		Sphere sphere = new Sphere(0.2f,0,100,ap);
		TransformGroup transformGrp = new TransformGroup();
		Transform3D transform = new Transform3D();

		transform.setTranslation(new Vector3f(x, y, z));
		transformGrp.setTransform(transform);

		transformGrp.addChild(sphere);
		this.addChild(transformGrp);
	}

	/*
	 * renders all sensor values in argument
	 */
	public void drawSensorValue(SensorValues sensorValues){

		ArrayList<SensorValue> values = sensorValues.getValuesList();
		
		SensorValue s;
		Iterator it = values.iterator();
		
		while(it.hasNext()){
			 s = (SensorValue) it.next();
				drawSphere(s.getX(),s.getY(),s.getZ(),s.getValue());
		}
	}
}
