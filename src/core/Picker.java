package core;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.picking.PickIntersection;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.picking.behaviors.PickMouseBehavior;

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
	public Picker(Canvas3D canvas, BranchGroup group, Bounds bounds) {
		super(canvas, group, bounds);
		 setSchedulingBounds(bounds);

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
            System.out.println("You pointed at: " + intercept);
            // extract the intersection pt in scene coords space
            // use the intersection pt in some way...
        }
	}

}
