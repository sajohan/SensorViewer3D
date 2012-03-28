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

	public void setObject(BranchGroup newModel) {
		System.out.println("Update achieved");

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

		univ.getViewingPlatform().setNominalViewingTransform();
//		
//        view_tg = univ.getViewingPlatform().getMultiTransformGroup().getTransformGroup(0);
//        view_tf3d = new Transform3D();
//        view_tg.getTransform(view_tf3d);
//		
//		view_tf3d.lookAt(new Point3d(0d,0d,10d),new Point3d(0d,0d,0d),new Vector3d(0,1,0));
//        view_tf3d.invert();
	}

	/**
	 * Creates a thread that resizes graphicsPane every "delay" ms.
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
	 * Sets up the light in the universe
	 */
	public void setUpLightAndGrid(){

		// Create lights
		lights = new Lighting();
		// Create grid
		grid = new Grid();

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
