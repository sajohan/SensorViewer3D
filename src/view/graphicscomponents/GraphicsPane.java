package view.graphicscomponents;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;



import core.model.Point3Dim;
import core.model.SensorValue;
import core.model.SensorValues;
import core.modelloader.ObjectLoader;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import static core.model.Constants.*;

import testGroups.CThreePointsMockCloud;
import testGroups.CThreePointsMockObject;
import view.picking.Picker;


/**
 * The JPanel containing the graphics window
 * @author Nyx, sajohan, dannic, chrfra
 */
public class GraphicsPane extends JPanel {

	private JFrame frame;
	private Transform3D view_tf3d;
	private Transform3D z_axisVector;
	private TransformGroup view_tg;
	private SimpleUniverse univ;
	private ObjectLoader objLoader;
	private Lighting lights;
	private Grid grid;
	private SensorValuesDrawer sensorValuesDrawer;
	private BranchGroup group;
	private final Canvas3D canvas;
	private OrbitBehavior orbit;
	private CThreePObject object;
	private CThreePObject cloud;
	Vector3d controlVec = new Vector3d(0.0f, -1.0f, 5.0f);

	/**
	 * Sets up the graphicspane and the objects in it
	 */
	public GraphicsPane(JFrame frame) {
		this.frame = frame;
		objLoader = new ObjectLoader(null, null);

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


		/*
		 * Create the objects that hold the virtual and physical 
		 * reference points used in aligninment.
		 */
		//This is the object to be moved by the moveTo
		object = new CThreePObject(group);

		//Object containing measurement data
		cloud = new CThreePObject(group);

		setUpLightAndGrid();

		//create the object that displays the measured quantities in 3d
		sensorValuesDrawer = new SensorValuesDrawer();
		//		sensorValuesDrawer.drawSphere(0, 0, 0, 1);
		group.addChild(sensorValuesDrawer);

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
		
		//Add a cloud
		group.addChild(cloud);


		univ.addBranchGraph(group);

		// Make camera moveable (OrbitBehavior)
		orbit = new OrbitBehavior(canvas,
				OrbitBehavior.REVERSE_ALL);

		//enables better zooming
		orbit.setProportionalZoom(true); 

		//disallows zooming in too close, such as zooming in past origo
		orbit.setCapability(OrbitBehavior.STOP_ZOOM);
		orbit.setMinRadius(0.5);

		orbit.setSchedulingBounds(bounds);
		
		vp.setViewPlatformBehavior(orbit);
		//set up and make frame visible
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);

		enableResize(canvas,500);
	}
	
	/**
	 * Aligns the object with the cloud using arguments
	 * @param	 	virtualRefPoints	reference points on virtual object
	 * @param	 	physicalRefPoints	reference points on physical object
	 */
	public void align(Point3Dim[] virtualRefPoints , Point3Dim[] physicalRefPoints){
		
		//detach group to mutate it's children ( mutate it's children? :S )
		group.detach();
		
		//Set the reference points provided in argument from core to the cloud and object
		object.setPoint1(new Vector3d(virtualRefPoints[0].x,virtualRefPoints[0].y,virtualRefPoints[0].z));
		object.setPoint2(new Vector3d(virtualRefPoints[1].x,virtualRefPoints[1].y,virtualRefPoints[1].z));
		object.setPoint3(new Vector3d(virtualRefPoints[2].x,virtualRefPoints[2].y,virtualRefPoints[2].z));

		cloud.setPoint1(new Vector3d(physicalRefPoints[0].x,physicalRefPoints[0].y,physicalRefPoints[0].z));
		cloud.setPoint2(new Vector3d(physicalRefPoints[1].x,physicalRefPoints[1].y,physicalRefPoints[1].z));
		cloud.setPoint3(new Vector3d(physicalRefPoints[2].x,physicalRefPoints[2].y,physicalRefPoints[2].z));
		
		//perform align (move object to cloud and align )
		object.moveTo(cloud);
		
		//add viewing platform as the object's child, 
		//vp will now rotate around the center of the object
		ViewingPlatform vp = univ.getViewingPlatform();
		vp.detach();
		object.addChild(vp);
		
		//move the grid to center of the object's new position
		grid.detach();
		object.addChild(grid);
		
		//reattach group to universe after finished mutating it
		univ.addBranchGraph(group);
				
		//set up freecam
		setPerspectivePolicy();
		orbit.setRotateEnable(true);
		
	}

	/**
	 * Removes old branchgroup, adds "newModel" to it instead.
	 * @param newModel	the object to replace the current objects in the branchgroup
	 */
	public void setObject(BranchGroup newModel) {
		System.out.println("Update achieved");

		//detatch branchgroup from its parent, replace with new branchgroup
		group.detach();
		group.removeChild(object);

		// univ.getLocale().removeBranchGraph(group);
//		group = newModel;
//		group.setCapability(BranchGroup.ALLOW_DETACH);
//		group.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
//		group.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
//		group.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		object.setObject(newModel);

		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				10000.0);

		//Set picker on the new group
		Picker picker = new Picker(canvas, group, bounds);
		group.addChild(object);

//		lights.detach();
//		grid.detach();
//
//		group.addChild(lights);
//		group.addChild(grid);
		univ.addBranchGraph(group);

		//		univ.getViewingPlatform().setNominalViewingTransform();
		//		
		//        view_tg = univ.getViewingPlatform().getMultiTransformGroup().getTransformGroup(0);
		//        view_tf3d = new Transform3D();
		//        view_tg.getTransform(view_tf3d);
		//		
		//		view_tf3d.lookAt(new Point3d(0d,0d,10d),new Point3d(0d,0d,0d),new Vector3d(0,1,0));
		//        view_tf3d.invert();
//		lockOnAxle(CAM_LOCK_X, false);
	}

	/**
	 * Creates a thread that resizes the canvas to the size of the JPanel (this)
	 * every "delay" ms.
	 * @param canvas	the canvas to be resized.
	 * @param delay	how long the thread will sleep between resizing.
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
	 * Sets the view to parallel, that is, things far away looks as close as things up close.
	 */
	public void setParallelPolicy(){
		canvas.getView().setProjectionPolicy(View.PARALLEL_PROJECTION);
	}

	/**
	 * Sets the view to perspective, that is, things far away looks like they're far away.
	 */
	public void setPerspectivePolicy(){
		canvas.getView().setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
	}

	/**
	 * Places camera on the axle provided as argument.
	 * @param axle	from Model.Constants the axle(x,y or z) to place the camera
	 */
	public void lockOnAxle(int axle, boolean reversed) {
		//get transformgroup number one (the camera), put it in view_tf3d variable
		view_tg = univ.getViewingPlatform().getMultiTransformGroup().getTransformGroup(0);
		view_tf3d = new Transform3D();
		object.getTransform(view_tf3d);
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
		case CAM_LOCK_X: view_tf3d.lookAt(new Point3d(camDistance,0d,0d),new Point3d(0,0,0),new Vector3d(0,1,0)); break;
		case CAM_LOCK_Y: view_tf3d.lookAt(new Point3d(0d,camDistance,0.1d),new Point3d(0,0,0),new Vector3d(0,1,0)); break;
		case CAM_LOCK_Z: view_tf3d.lookAt(new Point3d(0d,0d,camDistance),new Point3d(0,0,0),new Vector3d(0,1,0)); break;
		}

		//Note: Transform3D.lookAt() requires .invert() call after each use
		view_tf3d.invert(); 
		view_tg.setTransform(view_tf3d); //Perform the camera move
		setParallelPolicy();
		System.out.println("FOV: "+ canvas.getView().getFieldOfView());
		canvas.getView().setFieldOfView(0.29);
		//        try {
		//			wait(599);
		//		} catch (InterruptedException e) {
		//			e.printStackTrace();
		//		}
		System.out.println("FOV after: "+canvas.getView().getFieldOfView());
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
	
	/**
	 * makes the call to draw the graphical representation of argument sensorvalue object to the graphicsPane
	 */
	public void updateSensorValue(SensorValues s){
		group.detach();
		sensorValuesDrawer.drawSensorValue(s);
		univ.addBranchGraph(group);
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
	public OrbitBehavior getOrbit() {
		return orbit;
	}
	
	public SensorValuesDrawer getSensorValuesDrawer() {
		return sensorValuesDrawer;
	}
}
