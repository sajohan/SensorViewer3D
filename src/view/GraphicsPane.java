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
 * Move the camera with wasd. Zoom in and out with q and e. Z and x rotates, but breaks coordinates etc...
 * @author Nyx
 */
public class GraphicsPane extends JPanel implements Observer{

		private JFrame frame;
    	private Transform3D view_tf3d;
    	private TransformGroup view_tg;
    	private SimpleUniverse univ;
    	private ObjectLoader objLoader;
    	BranchGroup group;
    	Vector3d controlVec = new Vector3d(0.0f, -1.0f, 5.0f);
    	
        public GraphicsPane(JFrame frame) {
        	this.frame = frame;
        	objLoader = new ObjectLoader();
        	
        	GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
            final Canvas3D canvas = new Canvas3D(config); 

            canvas.setSize(new Dimension(400,400)); 
        	
            univ = new SimpleUniverse(canvas);
            group = new BranchGroup();
            //Add lights
            addLights();
            //Draw lines on axes
            drawAxes();
            
            ViewingPlatform vp = univ.getViewingPlatform();
            vp.setNominalViewingTransform();
            //Set clipdistance
            canvas.getView().setBackClipDistance(1000);
            canvas.getView().setFrontClipDistance(0.1);

            

            univ.addBranchGraph(group);
            
            //Old camera
//            OrbitAboutVWOrigin originCam = new OrbitAboutVWOrigin(vp);
//            
//            Camera cam = new Camera(vp);
//            canvas.addMouseMotionListener(cam);
//
//            canvas.addMouseMotionListener(originCam);
            
            //Make camera moveable
            BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 10000.0);
            OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
            orbit.setSchedulingBounds(bounds);
            vp.setViewPlatformBehavior(orbit);
            
            
            frame.add(canvas);
//            frame.addKeyListener(this);
            frame.pack();
            frame.setVisible(true);
            
            //Thread resizes java3D window every 500msec
            Runnable r1 = new Runnable() {
            	  public void run() {
            	    try {
            	      while (true) {
            	        canvas.setSize(getWidth(), getHeight());
            	        Thread.sleep(500L);
            	      }
            	    } catch (InterruptedException iex) {}
            	  }
            	};
            	Thread thr1 = new Thread(r1);
            	thr1.start();
            
        }
        
        /**
         * Draws a dashed line where the axes are
         *
         */
        public void drawAxes(){
        	Appearance redApp = new Appearance();
        	Appearance blueApp = new Appearance();
        	Appearance greenApp = new Appearance();
        	
            //Make the pattern dashed
            LineAttributes dashLa = new LineAttributes();
            dashLa.setLineWidth(1.0f);
            dashLa.setLinePattern(LineAttributes.PATTERN_DASH);
            dashLa.setLineAntialiasingEnable(true);
            redApp.setLineAttributes(dashLa);
            blueApp.setLineAttributes(dashLa);
            greenApp.setLineAttributes(dashLa);
            
            //Set Color
            Color3f color = new Color3f(1.0f, 0.0f, 0.0f);
            ColoringAttributes ca = new ColoringAttributes(color,
                    ColoringAttributes.SHADE_FLAT);
            redApp.setColoringAttributes(ca);
            
            color = new Color3f(0.0f, 1.0f, 0.0f);
            ca = new ColoringAttributes(color, ColoringAttributes.SHADE_FLAT);
            blueApp.setColoringAttributes(ca);
            
            color = new Color3f(0.0f, 0.0f, 1.0f);
            ca = new ColoringAttributes(color, ColoringAttributes.SHADE_FLAT);
            greenApp.setColoringAttributes(ca);
            
            //X-axis
            Point3f[] plaPts = new Point3f[2];
            plaPts[0] = new Point3f(-1000.0f, 0.0f, 0.0f);
            plaPts[1] = new Point3f(1000.0f, 0.0f, 0.0f);
            LineArray pla = new LineArray(2, LineArray.COORDINATES);
            pla.setCoordinates(0, plaPts);
            Shape3D plShape = new Shape3D(pla, redApp);
            group.addChild(plShape);
            
            //Y-axis
            plaPts[0] = new Point3f(0.0f, 1000.0f, 0.0f);
            plaPts[1] = new Point3f(0.0f, -1000.0f, 0.0f);
            pla = new LineArray(2, LineArray.COORDINATES);
            pla.setCoordinates(0, plaPts);
            plShape = new Shape3D(pla, blueApp);
            group.addChild(plShape);
            
            //Z-axis
            plaPts[0] = new Point3f(0.0f, 0.0f, 1000.0f);
            plaPts[1] = new Point3f(0.0f,  0.0f, -1000.0f);
            pla = new LineArray(2, LineArray.COORDINATES);
            pla.setCoordinates(0, plaPts);
            plShape = new Shape3D(pla, greenApp);
            group.addChild(plShape);
        }
        
        
        /**
         * Adds light to the scene
         * Directional lights shining in 6 directions
         * 
         * 
         */
        public void addLights(){
            Color3f lightColor = new Color3f(0.5f, 0.5f, 0.5f);
            BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);
            
            
            //Light 1. Shining y -> -y
            Vector3f lightDir = new Vector3f(0.0f, -1.0f, 0.0f);
            DirectionalLight light = new DirectionalLight(lightColor, lightDir);
            light.setInfluencingBounds(bounds);
            group.addChild(light);
        	
            //Light 2. Shining -y -> y
            lightDir = new Vector3f(0.0f, 1.0f, 0.0f);
            light = new DirectionalLight(lightColor, lightDir);
            light.setInfluencingBounds(bounds);
            group.addChild(light);
            
            //Light 3. Shining -x -> x
            lightDir = new Vector3f(1.0f, 0.0f, 0.0f);
            light = new DirectionalLight(lightColor, lightDir);
            light.setInfluencingBounds(bounds);
            group.addChild(light);
            
            //Light 4. Shining x -> -x
            lightDir = new Vector3f(-1.0f, 0.0f, 0.0f);
            light = new DirectionalLight(lightColor, lightDir);
            light.setInfluencingBounds(bounds);
            group.addChild(light);
            
            //Light 5. Shining -z -> z
            lightDir = new Vector3f(0.0f, 0.0f, 1.0f);
            light = new DirectionalLight(lightColor, lightDir);
            light.setInfluencingBounds(bounds);
            group.addChild(light);
            
            //Light 6. Shining z -> -z
            lightDir = new Vector3f(0.0f, 0.0f, -1.0f);
            light = new DirectionalLight(lightColor, lightDir);
            light.setInfluencingBounds(bounds);
            group.addChild(light);
        }

		@Override
		public void update(Observable obs, Object obj) {
			System.out.println("Update achieved");
			if(obs instanceof MenuBarListener ){
				BranchGroup tempGroup = objLoader.getObject((File)obj);
				univ.addBranchGraph(tempGroup);
				univ.getViewingPlatform().setNominalViewingTransform();
			}
			
		}

		
    }