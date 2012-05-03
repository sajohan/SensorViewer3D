package model;

import javax.vecmath.Point3d;

public class SensorValue{
	private float x,y,z,a;
	
	/**
	 * @param	float x	x-coordinate of value
	 * @param	float y	y-coordinate of value
	 * @param	float z	z-coordinate of value
	 * @param	float a	amplitude of value
	 */
	private SensorType type;
	
	public SensorValue(float x,float y,float z,float a, SensorType type){
		this.setX(x);
		this.setY(y);
		this.z = z;
		this.setValue(a);
		this.type = type;
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
	@Override
	public String toString() {
		return "Sensor: X: "+x+" Y: "+y+" Z: "+z;
	}
	
}
