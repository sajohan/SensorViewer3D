package core.modelloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import view.GUI;
import view.GraphicsPane;
import view.StatusPanel;
import core.modelloader.StlFile;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * Title: Object Loader Description: A loader that loads objects from files.
 * Supported fileformats: .stl, .obj
 * Uses SwingWorker thread to read objects
 * 
 * @author sajohan, dannic
 * @version 1.0
 * 
 */

public class ObjectLoader extends SwingWorker{
	
	
	private File chosenfile = null;
	private BranchGroup branch = null;
	private GUI gui;
	
	public ObjectLoader(File file, GUI gui){
		this.chosenfile = file;
		this.gui = gui;
		
	}

	/**
	 * 
	 * @param file
	 *            The file to be loaded
	 * @return BranchGroup a BranchGroup with the object
	 */
	public BranchGroup getObject(File file) {
		// Init progressbar
		StatusPanel.setProgress(0);
		if (file.getName().endsWith(".stl") || file.getName().endsWith(".STL")) {
			branch = getSTLObject(file);
			System.out.println("Object group done");
		} else if (file.getName().endsWith(".obj")
				|| file.getName().endsWith(".OBJ")) {
			System.out.println("Object group done");
			branch = getWavefrontObjObject(file);
			System.out.println("hej");
		}
		return branch;
	}

	/**
	 * 
	 * @param file
	 *            File to read
	 * @return BranchGroup returns a BranchGroup containing the read obj-object
	 * 
	 */
	private BranchGroup getWavefrontObjObject(File file) {
		
		int prog = 5;
		StatusPanel.setProgress(prog);
		
		ObjectFile loader = new ObjectFile(ObjectFile.RESIZE);
		BranchGroup group = new BranchGroup();

		StatusPanel.setProgress(prog+20);
		Scene scene = null;
		try {
			scene = loader.load(file.toURI().toURL());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IncorrectFormatException e) {
			e.printStackTrace();
		} catch (ParsingErrorException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		StatusPanel.setProgress(prog+20);
		group.addChild(scene.getSceneGroup());
		
		// Done loading
		StatusPanel.setProgress(100);
		return group;
	}

	/**
	 * 
	 * @param file
	 *            File to read
	 * @return BranchGroup returns a BranchGroup containing the read stl-object
	 * 
	 */
	private BranchGroup getSTLObject(File file) {
		// Init filename
		chosenfile = file;

		// Create the root of the branch graph
		BranchGroup group = new BranchGroup();

		// Create a Transformgroup to scale all objects so they
		// appear in the scene.
		TransformGroup objScale = new TransformGroup();
		Transform3D t3d = new Transform3D();

		// TODO Get scale from user?
		float scale = 0.5f;
		t3d.setScale(scale);
		objScale.setTransform(t3d);
		group.addChild(objScale);

		// Enable modifying
		TransformGroup objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		objScale.addChild(objTrans);

		StlFile stlfile = new StlFile();
		Scene scene = null;
		try {
			// stlfile.load() need to be able to handle a File object instead of
			// a String to a classpath file.
			// scene = stlfile.load(chosenfile);
			scene = stlfile.load(file.toURI().toURL());
		} catch (FileNotFoundException e) {
			System.err.println(e);
			System.exit(1);
		} catch (ParsingErrorException e) {
			System.err.println(e);
			System.exit(1);
		} catch (IncorrectFormatException e) {
			System.err.println(e);
			System.exit(1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(1);
		}

		objTrans.addChild(scene.getSceneGroup());
		// group.addChild(objTrans);
		
		// Done loading
		StatusPanel.setProgress(100);
		return group;
	}

	@Override
	protected Object doInBackground() throws Exception {
		branch = getObject(chosenfile);
		return null;
	}

	@Override
	protected void done() {
		//JOptionPane.showMessageDialog(null, "Work is done", "Yep", 1);
		
		gui.loadNewGraphicsWindow(this.branch);
	}
}
