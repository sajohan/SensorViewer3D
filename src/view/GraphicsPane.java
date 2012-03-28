package view;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import com.sun.j3d.exp.swing.JCanvas3D;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import controller.MenuBarListener;

import core.modelloader.ObjectLoader;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.PointLight;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 * Move the camera with wasd. Zoom in and out with q and e. Z and x rotates, but
 * breaks coordinates etc...
 * 
 * @author Nyx
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
	Vector3d controlVec = new Vector3d(0.0f, -1.0f, 5.0f);

	public GraphicsPane(JFrame frame) {
		this.frame = frame;
		objLoader = new ObjectLoader();

		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		final Canvas3D canvas = new Canvas3D(config);

		canvas.setSize(new Dimension(400, 400));

		univ = new SimpleUniverse(canvas);
		//create branchgroup, add light and grid to it
		group = new BranchGroup();
		setUpLightAndGrid();

		//set up view to nominal viewing transform
		ViewingPlatform vp = univ.getViewingPlatform();
		vp.setNominalViewingTransform();
		
		// Set clipdistance
		canvas.getView().setBackClipDistance(1000);
		canvas.getView().setFrontClipDistance(0.1);

		// Make camera moveable (OrbitBehavior)
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				10000.0);
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
	/*
	 * Removes old branchgroup, adds "newModel" to it instead.
	 * @param BranchGroup newModel	the object to replace the current objects in the branchgroup
	 */
	public void setObject(BranchGroup newModel) {
		System.out.println("Update achieved");
		
		//detatch branchgroup from its parent, replace with new branchgroup
		group.detach();
		group = newModel;
		
		//detatch light and grid
		lights.detach();
		grid.detach();
		//re-setup light and grid
		setUpLightAndGrid();
		

//		univ.getViewingPlatform().setNominalViewingTransform();
//		
//        view_tg = univ.getViewingPlatform().getMultiTransformGroup().getTransformGroup(0);
//        view_tf3d = new Transform3D();
//        view_tg.getTransform(view_tf3d);
//		
//		view_tf3d.lookAt(new Point3d(0d,0d,-10d),new Point3d(0d,0d,0d),new Vector3d(0,1,0));
//        view_tf3d.invert();
	}

	/*
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
	
	/*
	 * Sets up the light and x,y,z origo axes in the universe.
	 */
	public void setUpLightAndGrid(){
		// Create lights
		lights = new Lighting();
		// Create grid
		grid = new Grid();

		group.setCapability(BranchGroup.ALLOW_DETACH);
		group.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		group.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		group.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		group.addChild(lights);
		group.addChild(grid);
		
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
}
