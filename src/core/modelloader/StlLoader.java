package core.modelloader;

import java.io.File;
import java.io.FileNotFoundException;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import core.modelloader.StlFile;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class StlLoader {

	private File chosenfile = null;
	
	/**
	 * @param File to read
	 * @return Returns a BranchGroup containing the stl-object read
	 * 
	 */
	public BranchGroup getSTLObject(File file) {
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
			// stlfile.load() need to be able to handle a File object instead of a String to a classpath file.
			scene = stlfile.load(chosenfile);
		} catch (FileNotFoundException e) {
			System.err.println(e);
			System.exit(1);
		} catch (ParsingErrorException e) {
			System.err.println(e);
			System.exit(1);
		} catch (IncorrectFormatException e) {
			System.err.println(e);
			System.exit(1);
		}

		objTrans.addChild(scene.getSceneGroup());

		return group;
	}

}
