package view.picking;

import java.math.BigDecimal;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Node;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import view.graphicscomponents.SensorValuesDrawer;
import view.guicomponents.GUI;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.picking.PickIntersection;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.picking.behaviors.PickMouseBehavior;

import core.model.Point3Dim;

/**
 * Title: Picker for mouse
 * Description: Calculates the point in 3D-space where the user clicks. 
 * Looks at all the objects in the given branchgroup.
 * 
 * @author sajohan
 * @version 1.0
 *
 */
public class Picker extends PickMouseBehavior {
	
	/**
	 * Constructor for Picker
	 * @param canvas
	 * @param group
	 * @param bounds
	 */
	
	BranchGroup group;
	PickerMarker pickerMarker;
	
	private static Point3Dim lastPickPos;
	
	private static double lastPickAngleToY;
	
	public Picker(Canvas3D canvas, BranchGroup group, Bounds bounds) {
		super(canvas, group, bounds);
		 setSchedulingBounds(bounds);
		 
		 this.group = group;

         pickCanvas.setMode(PickTool.GEOMETRY_INTERSECT_INFO);
         // allows PickIntersection objects to be returned
	}

	@Override
	public void updateScene(int xpos, int ypos) {
		pickCanvas.setShapeLocation(xpos, ypos);
        // register mouse pointer location on the screen (canvas)

        Point3d eyePos = pickCanvas.getStartPosition();
        // get the viewer's eye location

        PickResult pickResult = null;
        pickResult = pickCanvas.pickClosest();
        // get the intersected shape closest to the viewer

        if (pickResult != null) {
            PickIntersection pi = pickResult.getClosestIntersection(eyePos);
           /*Picking Position*/
            Point3d intercept = pi.getPointCoordinatesVW();
            
            double x = roundValue(intercept.x);
            double y = roundValue(intercept.y);
            double z = roundValue(intercept.z);
            
            lastPickPos = new Point3Dim(x, y, z);
            
            GUI.printToStatus("You pointed at X: " + x + " Y:" + y + " Z: " + z);
            
            /*Pick Normal and find the angle to Y*/
            Vector3f normal = pi.getPointNormal();
            Vector3f yAxis = new Vector3f(0.0f, -1.0f,0.0f);
            double angleToY = Math.toDegrees(normal.angle(yAxis));
            lastPickAngleToY = angleToY;
            
//            normal.x = roundValue(normal.x);
//            normal.y = roundValue(normal.y);
//            normal.z = roundValue(normal.z);
//            lastPickNormal = normal;
            
            /*Pick marking*/
            if(pickerMarker != null){
            	pickerMarker.detach();
            }

            // Add a sphere to where we clicked
            pickerMarker = new PickerMarker();
            pickerMarker.drawSphere((float)x, (float)y, (float)z);
    		group.addChild(pickerMarker);
    		
            // extract the intersection pt in scene coords space
            // use the intersection pt in some way...
        }
	}
	
	/**
	 * rounds argument value up using two decimal places
	 * @param toRound value to round
	 * @return Rounded value
	 */
	public double roundValue(double toRound){
		// Round with 2 decimalplaces
        int decimalPlaces = 2;
        BigDecimal big = new BigDecimal(toRound);
        big = big.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        return big.doubleValue();
	}
	
	/**
	 * rounds argument value up using four decimal places
	 * @param toRound value to round
	 * @return Rounded value
	 */
	public float roundValue(float toRound){
		// Round with 4 decimalplaces
        int decimalPlaces = 4;
        BigDecimal big = new BigDecimal(toRound);
        big = big.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        return big.floatValue();
	}
	
	/**
	 * returns the point that was last picked
	 * @return The coordinates of the last pick
	 */
	public static Point3Dim getLastPickPos() {
		return lastPickPos;
	}
	
	/**
	 * 
	 * @return the angle to the y axis in relation to the normal
	 */
	public static double getLastPickAngleToY(){
		return lastPickAngleToY;
	}
}
