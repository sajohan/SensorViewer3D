package core.modelloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import core.modelloader.StlFile;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * Title: Object Loader
 * Description: A loader that loads objects from files. Supported fileformats: .stl
 * 
 * @author sajohan, dannic
 * @version 1.0
 *
 */

public class ObjectLoader {

	private File chosenfile = null;
	
	/**
	 * 
	 * @param file The file to be loaded
	 * @return BranchGroup a BranchGroup with the object
	 */
	public BranchGroup getObject(File file){
		if(file.getName().endsWith(".stl") || file.getName().endsWith(".STL")){
			return getSTLObject(file);
		}
		return null;
	}
	
	/**
	 * 
	 * @param file File to read
	 * @return BranchGroup returns a BranchGroup containing the stl-object read
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
