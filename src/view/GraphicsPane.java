package view;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;



import core.Picker;
import core.modelloader.ObjectLoader;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import static model.Constants.*;

import testGroups.CThreePointsMockCloud;
import testGroups.CThreePointsMockObject;


/**
 * Move the camera with wasd. Zoom in and out with q and e. Z and x rotates, but
 * breaks coordinates etc...
 * 
 * @author Nyx, sajohan, dannic, chrfra
 */
public class GraphicsPane extends JPanel {

	private JFrame frame;
	private Transform3D view_tf3d;
	private TransformGroup view_tg;
	private SimpleUniverse univ;
	private ObjectLoader objLoader;
	private Lighting lights;
	private Grid grid;
	private BranchGroup group;
	private final Canvas3D canvas;
	Vector3d controlVec = new Vector3d(0.0f, -1.0f, 5.0f);

	public GraphicsPane(JFrame frame) {
		this.frame = frame;
		objLoader = new ObjectLoader();

		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		canvas = new Canvas3D(config);

		canvas.setSize(new Dimension(400, 400));

		univ = new SimpleUniverse(canvas);
		
		//Set up branchgroup
		group = new BranchGroup();
		group.setCapability(BranchGroup.ALLOW_DETACH);
		group.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		group.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		group.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
			
			
//			/**
//			 * Uncomment this to see moveTo() in the works.
//			 */
//			CThreePointsMockObject CThreePO = new CThreePointsMockObject(group);
//			CThreePointsMockCloud CThreePO2 = new CThreePointsMockCloud(group);
//			group.addChild(CThreePO);
//			group.addChild(CThreePO2);
//
//			Transform3D mockMove = new Transform3D();
//			Transform3D mockRotate = new Transform3D();
//			mockMove.setTranslation(new Vector3d(20, 19, 18));
//			mockRotate.rotX(3);
//			mockMove.mul(mockRotate);
//			mockRotate.rotY(2);
//			mockMove.mul(mockRotate);
//			mockRotate.rotZ(1.111);
//			mockMove.mul(mockRotate);
//			CThreePO2.setTransform(mockMove);
//
//			
//			CThreePO.moveTo(CThreePO2);

		setUpLightAndGrid();

		//set up view to nominal viewing transform
		ViewingPlatform vp = univ.getViewingPlatform();
		vp.setNominalViewingTransform();
		
		// Set clipdistance
		canvas.getView().setBackClipDistance(1000);
		canvas.getView().setFrontClipDistance(0.1);
		
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				10000.0);
		
		// Setup picking
		Picker picker = new Picker(canvas, group, bounds);
		group.addChild(picker);
		
		
		univ.addBranchGraph(group);

		// Make camera moveable (OrbitBehavior)
		OrbitBehavior orbit = new OrbitBehavior(canvas,
				OrbitBehavior.REVERSE_ALL);
		orbit.setSchedulingBounds(bounds);
		vp.setViewPlatformBehavior(orbit);

		//set up and make frame visible
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
		
		enableResize(canvas,500);
	}
	/**
	 * Removes old branchgroup, adds "newModel" to it instead.
	 * @param BranchGroup newModel	the object to replace the current objects in the branchgroup
	 */
	public void setObject(BranchGroup newModel) {
		System.out.println("Update achieved");

		//detatch branchgroup from its parent, replace with new branchgroup
		group.detach();

		
		// univ.getLocale().removeBranchGraph(group);
		group = newModel;
		group.setCapability(BranchGroup.ALLOW_DETACH);
		group.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		group.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		group.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				10000.0);
		
		//Set picker on the new group
		Picker picker = new Picker(canvas, group, bounds);
		group.addChild(picker);
		
		lights.detach();
		grid.detach();

		group.addChild(lights);
		group.addChild(grid);
		univ.addBranchGraph(group);

//		univ.getViewingPlatform().setNominalViewingTransform();
//		
//        view_tg = univ.getViewingPlatform().getMultiTransformGroup().getTransformGroup(0);
//        view_tf3d = new Transform3D();
//        view_tg.getTransform(view_tf3d);
//		
//		view_tf3d.lookAt(new Point3d(0d,0d,10d),new Point3d(0d,0d,0d),new Vector3d(0,1,0));
//        view_tf3d.invert();
		lockOnAxle(CAM_LOCK_X, false);
	}

	/**
	 * Creates a thread that resizes the canvas to the size of the JPanel (this)
	 * every "delay" ms.
	 * @param Canvas3D	canvas	the canvas to be resized.
	 * @param int		delay	how long the thread will sleep between resizing.
	 */
	public void enableResize(final Canvas3D canvas, final int delay) {
		Runnable r1 = new Runnable() {
			public void run() {
				try {
					while (true) {
						canvas.setSize(getWidth(), getHeight());
						Thread.sleep(delay);
					}
				} catch (InterruptedException iex) {
				}
			}
		};
		Thread thr1 = new Thread(r1);
		thr1.start();
	}
	
	/**
	 * Places camera on the axle provided as argument.
	 * @param int	axle	from Model.Constants the axle(x,y or z) to place the camera
	 */
	public void lockOnAxle(int axle, boolean reversed) {
		//get transformgroup number one (the camera), put it in view_tf3d variable
        view_tg = univ.getViewingPlatform().getMultiTransformGroup().getTransformGroup(0);
        view_tf3d = new Transform3D();
        view_tg.getTransform(view_tf3d);
        //get constant camera distance from origo,
        //reverse if argument "reversed" demands so
        double camDistance = CAM_DISTANCE;
        if(reversed)
        	camDistance = (-camDistance);
        
        //using the camera's transformgroup, place the camera at CAM_DISTANCE
        //from origo on the axis provided in the mathod argument
        //note to self: argument 1 to lookAt() (eye position) 
        //may never include z = 0 when placing camera on Y-axis
        switch(axle){
        case CAM_LOCK_X: view_tf3d.lookAt(new Point3d(camDistance,0d,0d),new Point3d(0d,0d,0d),new Vector3d(0,1,0)); break;
        case CAM_LOCK_Y: view_tf3d.lookAt(new Point3d(0d,camDistance,0.1d),new Point3d(0d,0d,0d),new Vector3d(0,1,0)); break;
        case CAM_LOCK_Z: view_tf3d.lookAt(new Point3d(0d,0d,camDistance),new Point3d(0d,0d,0d),new Vector3d(0,1,0)); break;
        }
		
        //Note: Transform3D.lookAt() requires .invert() call after each use
        view_tf3d.invert(); 
        view_tg.setTransform(view_tf3d); //Perform the camera move
	}
	/**
	 * Sets up the light and x,y,z origo axes in the universe.
	 */
	public void setUpLightAndGrid(){

		// Create lights
		lights = new Lighting();
		// Create grid
		grid = new Grid();
		grid.setPickable(false);

		group.addChild(lights);
		group.addChild(grid);
		
	}
	
	
	public Lighting getLights() {
		return lights;
	}

	public void setLights(Lighting lights) {
		this.lights = lights;
	}
	
	public Grid getGrid() {
		return grid;
	}
}
