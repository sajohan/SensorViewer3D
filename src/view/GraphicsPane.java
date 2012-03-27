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
	private Grid gridGroup;
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

		group = new BranchGroup();

		// Create lights
		lights = new Lighting();
		// Create grid
		gridGroup = new Grid();

		group.setCapability(BranchGroup.ALLOW_DETACH);
		group.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		group.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		group.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		group.addChild(lights);
		group.addChild(gridGroup);

		ViewingPlatform vp = univ.getViewingPlatform();
		vp.setNominalViewingTransform();
		// Set clipdistance
		canvas.getView().setBackClipDistance(1000);
		canvas.getView().setFrontClipDistance(0.1);

		univ.addBranchGraph(group);

		// Old camera
		// OrbitAboutVWOrigin originCam = new OrbitAboutVWOrigin(vp);
		//
		// Camera cam = new Camera(vp);
		// canvas.addMouseMotionListener(cam);
		//
		// canvas.addMouseMotionListener(originCam);

		// Make camera moveable
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				10000.0);
		OrbitBehavior orbit = new OrbitBehavior(canvas,
				OrbitBehavior.REVERSE_ALL);
		orbit.setSchedulingBounds(bounds);
		vp.setViewPlatformBehavior(orbit);

		frame.add(canvas);
		// frame.addKeyListener(this);
		frame.pack();
		frame.setVisible(true);

		// Thread resizes java3D window every 500msec
		Runnable r1 = new Runnable() {
			public void run() {
				try {
					while (true) {
						canvas.setSize(getWidth(), getHeight());
						Thread.sleep(500L);
					}
				} catch (InterruptedException iex) {
				}
			}
		};
		Thread thr1 = new Thread(r1);
		thr1.start();

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

		lights.detach();
		gridGroup.detach();

		group.addChild(lights);
		group.addChild(gridGroup);
		univ.addBranchGraph(group);

		univ.getViewingPlatform().setNominalViewingTransform();

	}

	public Lighting getLights() {
		return lights;
	}

	public void setLights(Lighting lights) {
		this.lights = lights;
	}

}
