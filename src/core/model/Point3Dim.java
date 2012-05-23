package core.model;

/**
 * Title: 3D Point
 * Description: A point in 3D-space
 * 
 * @author sajohan
 *
 */
public class Point3Dim {

	public double x, y, z;
	
	/**
	 * 
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param z the z-coordinate
	 */
	public Point3Dim(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String toString(){
		return new String("X: "+x+" Y: "+y+" Z: "+z);
	}
}
