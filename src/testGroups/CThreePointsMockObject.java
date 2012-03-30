package testGroups;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Sphere;

import view.graphicscomponents.*;



/**
 * Mock object for testing that CThreePObject.moveTo() works.
 * @author Simon
 *
 */
public class CThreePointsMockObject extends CThreePObject{

	public CThreePointsMockObject(BranchGroup mainGroup) {
		super(mainGroup);
		Transform3D locTrans = new Transform3D();

		ColorCube ccube = new ColorCube();
		TransformGroup tgroup = new TransformGroup();
		tgroup.addChild(ccube);
		super.setObject(tgroup);
		Vector3d point1 = new Vector3d(2.4, -13, 8);
		super.setPoint1(point1);
		Vector3d point2 = new Vector3d(-3, 6, 24);
		super.setPoint2(point2);
		Vector3d point3 = new Vector3d(12, 7, 3);
		super.setPoint3(point3);
		
		Sphere sphere1 = new Sphere(0.2f);
		
		Appearance ap = sphere1.getAppearance();
		 Material mat = ap.getMaterial();
		 mat.setDiffuseColor(new Color3f(0,0,1));
		 // set appearance back
		 sphere1.setAppearance(ap);

		Shape3D sh3D=sphere1.getShape();
		 ap = sh3D.getAppearance();
		 mat = ap.getMaterial();
		 mat.setDiffuseColor(new Color3f(0,0,1));
		 // set appearance back
		 sh3D.setAppearance(ap);
		 
		 
		TransformGroup sphereGroup1x = new TransformGroup();
		locTrans.setTranslation(point1);
		sphereGroup1x.setTransform(locTrans);
		sphereGroup1x.addChild(sphere1);
		this.addChild(sphereGroup1x);
		
		Sphere sphere2 = new Sphere(0.2f);
		ap = sphere2.getAppearance();
		 mat = ap.getMaterial();
		 mat.setDiffuseColor(new Color3f(0,1,0));
		 // set appearance back
		 sphere2.setAppearance(ap);

		sh3D=sphere2.getShape();
		 ap = sh3D.getAppearance();
		 mat = ap.getMaterial();
		 mat.setDiffuseColor(new Color3f(0,1,0));
		 // set appearance back
		 sh3D.setAppearance(ap);
		 
		TransformGroup sphereGroup2x = new TransformGroup();
		locTrans.setTranslation(point2);
		sphereGroup2x.setTransform(locTrans);
		sphereGroup2x.addChild(sphere2);
		this.addChild(sphereGroup2x);
		
		Sphere sphere3 = new Sphere(0.2f);
		TransformGroup sphereGroup3x = new TransformGroup();
		locTrans.setTranslation(point3);
		sphereGroup3x.setTransform(locTrans);
		sphereGroup3x.addChild(sphere3);
		this.addChild(sphereGroup3x);
		

		
		
		
	}
	
	

}
