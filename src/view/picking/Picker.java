package view.picking;

import java.math.BigDecimal;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Node;
import javax.vecmath.Point3d;

import view.guicomponents.GUI;
import view.guicomponents.SensorValuesDrawer;

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
	
	private static Point3Dim lastPick;
	
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
            // get the closest intersect to the eyePos point
            Point3d intercept = pi.getPointCoordinatesVW();
            
            double x = roundValue(intercept.x);
            double y = roundValue(intercept.y);
            double z = roundValue(intercept.z);
            
            lastPick = new Point3Dim(x, y, z);
            
            GUI.printToStatus("You pointed at X: " + x + " Y:" + y + " Z: " + z);
            
            if(pickerMarker != null){
            	pickerMarker.detach();
            }

            // Add a sphere to where we clicked
            pickerMarker = new PickerMarker();
            pickerMarker.drawSphere((float)x, (float)y, (float)z);
    		group.addChild(pickerMarker);
            
            //System.out.println("You pointed at: " + intercept);
            // extract the intersection pt in scene coords space
            // use the intersection pt in some way...
        }
	}
	public double roundValue(double toRound){
		// Round with 2 decimalplaces
        int decimalPlaces = 2;
        BigDecimal big = new BigDecimal(toRound);
        big = big.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        return big.doubleValue();
	}
	
	public static Point3Dim getLastPick() {
		return lastPick;
	}
}
