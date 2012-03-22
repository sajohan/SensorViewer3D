package view;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.sun.j3d.exp.swing.JCanvas3D;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;


/**
 * Move the camera with wasd. Zoom in and out with q and e. Z and x rotates, but breaks coordinates etc...
 * @author Nyx
 */
public class GraphicsPane extends JCanvas3D{

		private JFrame frame;
    	private Transform3D view_tf3d;
    	private TransformGroup view_tg;
    	Vector3d controlVec = new Vector3d(0.0f, -1.0f, 5.0f);
    	
        public GraphicsPane(JFrame frame) {
        	this.frame = frame;
        	
        	GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
            Canvas3D canvas = new Canvas3D(config); 

            canvas.setSize(new Dimension(400,400)); 

        	
        	
            SimpleUniverse univ = new SimpleUniverse(canvas);
            BranchGroup group = new BranchGroup();
            ColorCube cube = new ColorCube(0.5f);
            group.addChild(cube);
            Color3f light = new Color3f(1.8f, 0.1f, 0.1f);
            BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
            
            Vector3f lightDir = new Vector3f(4.0f, -7.0f, -12.0f);
            DirectionalLight actualLight = new DirectionalLight(light, lightDir);
            actualLight.setInfluencingBounds(bounds);
            group.addChild(actualLight);
            univ.getViewingPlatform().setNominalViewingTransform();
            ViewingPlatform vp = univ.getViewingPlatform();

            univ.addBranchGraph(group);
            
            OrbitAboutVWOrigin originCam = new OrbitAboutVWOrigin(vp);
            
            Camera cam = new Camera(vp);
            canvas.addKeyListener(cam);
            canvas.addMouseMotionListener(cam);

//            canvas.addMouseMotionListener(originCam);
            
            frame.add(canvas);
//            frame.addKeyListener(this);
            frame.pack();
            frame.setVisible(true);
            
        }

		
    }