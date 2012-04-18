package view;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import model.SensorValue;

import com.sun.j3d.utils.geometry.Sphere;

public class SensorValuesDrawer extends BranchGroup{

	
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
		
		Sphere sphere = new Sphere(a);
		TransformGroup transformGrp = new TransformGroup();
		Transform3D transform = new Transform3D();
		
		transform.setTranslation(new Vector3f(x, y, z));
		transformGrp.setTransform(transform);
		
		transformGrp.addChild(sphere);
		this.addChild(transformGrp);
	}
	
	
	public void drawSensorValue(SensorValue s){
		if(s.isVisible())
			drawSphere(s.getX(),s.getY(),s.getZ(),s.getValue());

	}
}
