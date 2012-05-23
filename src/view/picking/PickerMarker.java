package view.picking;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;


import com.sun.j3d.utils.geometry.Sphere;

import core.model.SensorValue;

/**
 * Draws a sphere where picker pointed at when selected
 * @author chrfra
 *
 */
public class PickerMarker extends BranchGroup {

	final static float SPHERE_SIZE = 0.1f;

	public PickerMarker() {

		this.setCapability(ALLOW_DETACH);
		this.setPickable(false);

	}

	/**
	 * Draws a sphere at argument coordinate
	 * 
	 * @param x
	 *            position x of quantity
	 * @param y
	 *            position y of quantity
	 * @param z
	 *            position z of quantity
	 */
	public void drawSphere(float x, float y, float z) {

		Sphere sphere = new Sphere(SPHERE_SIZE);

		Appearance ap = new Appearance();
		Color3f col = new Color3f(1.0f, 0.0f, 0.0f);
		ColoringAttributes ca = new ColoringAttributes(col,
				ColoringAttributes.NICEST);

		Material material = new Material();
		material.setDiffuseColor(1.0f, 0.0f, 0.0f);
		material.setShininess(50.0f);
		ap.setMaterial(material);
		ap.setColoringAttributes(ca);
		
		sphere.setAppearance(ap);

		TransformGroup transformGrp = new TransformGroup();
		Transform3D transform = new Transform3D();

		transform.setTranslation(new Vector3f(x, y, z));
		transformGrp.setTransform(transform);

		transformGrp.addChild(sphere);
		this.addChild(transformGrp);
	}
}
