package model;

import javax.vecmath.Point3d;

public class SensorValue{
	private float x,y,z,a;
	private boolean visible = false; 
	
	public SensorValue(float x,float y,float z,float a){
		this.setX(x);
		this.setY(y);
		this.z = z;
		this.setValue(a);
	}
	
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getY() {
		return y;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getX() {
		return x;
	}
	public Point3d getpoint3D() {
		return new Point3d(x,y,z);
	}
	public void setValue(float a) {
		this.a = a;
	}
	public float getValue() {
		return a;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}
	
}
