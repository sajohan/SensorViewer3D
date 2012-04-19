package core;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import model.SensorValue;

import com.sun.j3d.utils.geometry.Sphere;
	
public class PickerMarker extends BranchGroup{
	
	final static float SPHERE_SIZE = 0.1f;
	public PickerMarker(){
		
		this.setCapability(ALLOW_DETACH);
		this.setPickable(false);
		
	}

	/**
	 * Draws a sphere where picker pointed
	 * @param	x	position x of quantity
	 * @param	y	position y of quantity
	 * @param	z	position z of quantity
	 */
	public void drawSphere(float x, float y, float z){
		
		Sphere sphere = new Sphere(SPHERE_SIZE);
		TransformGroup transformGrp = new TransformGroup();
		Transform3D transform = new Transform3D();
		
		transform.setTranslation(new Vector3f(x, y, z));
		transformGrp.setTransform(transform);
		
		transformGrp.addChild(sphere);
		this.addChild(transformGrp);
	}
}
