package view;

import javax.vecmath.Color4f;

import com.sun.j3d.utils.geometry.Sphere;

public class SensorRepresentation extends Sphere {

	private Color4f color = new Color4f(0.0f, 1.0f, 0.0f, 0.5f);
	
	private float size = 1;
	
	private float rangeMinVal;
	private float rangeMaxVal;
	
	
	public SensorRepresentation(float min, float max) {
		this.rangeMinVal = min;
		this.rangeMaxVal = max;
	}
	
}
