package model;

public class Point3Dim {

	public double x, y, z;
	
	public Point3Dim(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String toString(){
		return new String("X: "+x+" Y: "+y+" Z: "+z);
	}
}
