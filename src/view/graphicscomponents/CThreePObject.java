package view.graphicscomponents;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Sphere;


/**
 * TransformGroup that contains an object and three points, for aligning with other objects of the same type.
 * Name short form of "Three Points and an Object", also added C in the front so you can name your variables C3PO.
 * @author Simon
 *
 */
public class CThreePObject extends TransformGroup{

	private TransformGroup pointGroup1;
	private TransformGroup pointGroup2;
	private TransformGroup pointGroup3;
	private Node object;
	
	private BranchGroup anchor1;
	private BranchGroup anchor2;
	private BranchGroup anchor3;

	private Transform3D transformer;
	
	private TransformGroup innerRotationGroup;
	private TransformGroup outerRotationGroup;
	private TransformGroup axisRotationGroup;
	private TransformGroup scaleGroup;
	private TransformGroup moveGroup;
	
	private BranchGroup mainGroup;
	//stores the estimated center of the object ( the mean value of ref1,2,3 )
	private Point3d objectCenter;
	
	
	
	/**
	 * Creates an empty CThreePObject.
	 * @param BranchGroup mainGroup The main branchgroup containing the item.
	 */
	public CThreePObject(BranchGroup mainGroup){
		transformer = new Transform3D();
		
		anchor1 = new BranchGroup();
		anchor2 = new BranchGroup();
		anchor3 = new BranchGroup();
		
		this.mainGroup = mainGroup;
		
		innerRotationGroup= new TransformGroup();
		outerRotationGroup = new TransformGroup();
		axisRotationGroup = new TransformGroup();
		scaleGroup = new TransformGroup();
		moveGroup = new TransformGroup();
		
		transformer = new Transform3D();
		//object will be spawned in origo
		objectCenter = new Point3d(0,0,0);


	}
	/**
	 * Creates a point-less instance of a CThreePObject.
	 * @param BranchGroup mainGroup The main branchgroup containing the item.
	 * @param TransformGroup object The visible object.
	 */
	public CThreePObject(BranchGroup mainGroup, TransformGroup object){
		transformer = new Transform3D();
		
		anchor1 = new BranchGroup();
		anchor2 = new BranchGroup();
		anchor3 = new BranchGroup();
		
		this.mainGroup = mainGroup;
		
		innerRotationGroup= new TransformGroup();
		outerRotationGroup = new TransformGroup();
		axisRotationGroup = new TransformGroup();
		scaleGroup = new TransformGroup();
		moveGroup = new TransformGroup();
		
		transformer = new Transform3D();
		
		setObject(object);


	}
	
	
	/**
	 * Creates an object-less instance of a CThreePObject
	 * @param BranchGroup mainGroup The main branchgroup containing the item.
	 * @param Vector3d point1 The position of the first alignment-point
	 * @param Vector3d point2 The position of the second alignment-point
	 * @param Vector3d point3 The position of the third alignment-point
	 */
	public CThreePObject(BranchGroup mainGroup, Vector3d point1, Vector3d point2, Vector3d point3){
		transformer = new Transform3D();
		
		anchor1 = new BranchGroup();
		anchor2 = new BranchGroup();
		anchor3 = new BranchGroup();
		
		this.mainGroup = mainGroup;
		
		innerRotationGroup= new TransformGroup();
		outerRotationGroup = new TransformGroup();
		axisRotationGroup = new TransformGroup();
		scaleGroup = new TransformGroup();
		moveGroup = new TransformGroup();
		
		transformer = new Transform3D();
		
		setPoint1(point1);
		setPoint2(point2);
		setPoint3(point3);


	}
	
	/**
	 * Creates an instance of the CThreePObject.
	 * @param BranchGroup mainGroup The main branchgroup containing the item.
	 * @param TransformGroup object The visible object.
	 * @param Vector3d point1 The position of the first alignment-point
	 * @param Vector3d point2 The position of the second alignment-point
	 * @param Vector3d point3 The position of the third alignment-point
	 */
	public CThreePObject(BranchGroup mainGroup,TransformGroup object, Vector3d point1, Vector3d point2, Vector3d point3){
		transformer = new Transform3D();
		
		anchor1 = new BranchGroup();
		anchor2 = new BranchGroup();
		anchor3 = new BranchGroup();
		
		this.mainGroup = mainGroup;
		
		innerRotationGroup= new TransformGroup();
		outerRotationGroup = new TransformGroup();
		axisRotationGroup = new TransformGroup();
		scaleGroup = new TransformGroup();
		moveGroup = new TransformGroup();
		
		transformer = new Transform3D();
		
		setObject(object);
		setPoint1(point1);
		setPoint2(point2);
		setPoint3(point3);

	}
	
	/**
	 * Sets the position of the first alignment-point
	 * @param Vector3d point1 The position of the first alignment-point
	 */
	public void setPoint1(Vector3d point1){
		if(pointGroup1 == null){
			pointGroup1 = new TransformGroup();
			this.addChild(pointGroup1);
			pointGroup1.addChild(anchor1);
		}
		transformer.setTranslation(point1);
		pointGroup1.setTransform(transformer);
	}
	
	
	/**
	 * Sets the position of the second alignment-point
	 * @param Vector3d point2 The position of the second alignment-point
	 */
	public void setPoint2(Vector3d point2){
		if(pointGroup2 == null){
			pointGroup2 = new TransformGroup();
			this.addChild(pointGroup2);
			pointGroup2.addChild(anchor2);
		}
		transformer.setTranslation(point2);
		pointGroup2.setTransform(transformer);
	}
	
	/**
	 * Sets the position of the third alignment-point
	 * @param Vector3d point3 The position of the third alignment-point
	 */
	public void setPoint3(Vector3d point3){
		if(pointGroup3 == null){
			pointGroup3 = new TransformGroup();
			this.addChild(pointGroup3);
			pointGroup3.addChild(anchor3);
		}
		transformer.setTranslation(point3);
		pointGroup3.setTransform(transformer);
	}
	
	
	
	/**
	 * Sets the visible object. NOTE: This will also remove the old object!
	 * @param TransformGroup object The visible object.
	 */
	public void setObject(Node object){
		if(object == null){
			this.object = object;
			this.addChild(this.object);
		}
		else{
			this.removeChild(this.object);
			this.object = object;
			this.addChild(this.object);
		}
	}
	
	
	
	
	
	/**
	 * Gets the vector containing the position of the first alignment-point
	 * @return Vector3d
	 */
	public Vector3d getPosOfPoint1(){
		Vector3d retVec = new Vector3d();
		Transform3D t3d = new Transform3D();
		anchor1.getLocalToVworld(t3d);
		t3d.get(retVec);
		return retVec;
	}
	
	
	/**
	 * Gets the vector containing the position of the second alignment-point
	 * @return Vector3d
	 */
	public Vector3d getPosOfPoint2(){
		Vector3d retVec = new Vector3d();
		Transform3D t3d = new Transform3D();
		anchor2.getLocalToVworld(t3d);
		t3d.get(retVec);
		return retVec;
	}
	
	
	/**
	 * Gets the vector containing the position of the third alignment-point
	 * @return Vector3d
	 */
	public Vector3d getPosOfPoint3(){
		Vector3d retVec = new Vector3d();
		Transform3D t3d = new Transform3D();
		anchor3.getLocalToVworld(t3d);
		t3d.get(retVec);
		return retVec;
	}
	
	/**
	 * Calculates the distortion between this object and the parameter object. The lower the better.
	 * Does not count scale errors, for obvious reasons. Also possible for errors to take each other out.
	 * @param alignmentObject
	 * @return int 	A value representing how skewed the points are.
	 */
	public double computeDistortion(CThreePObject alignmentObject){
		
		//Variables coming from this object
		Point3d thisPoint1 = vectorToPoint(this.getPosOfPoint1());
		Point3d thisPoint2 = vectorToPoint(this.getPosOfPoint2());
		Point3d thisPoint3 = vectorToPoint(this.getPosOfPoint3());
		double thisDist1and2 = thisPoint1.distance(thisPoint2);
		double thisDist1and3 = thisPoint1.distance(thisPoint3);
		double thisDist2and3 = thisPoint2.distance(thisPoint3);
		double thisRatio1 = thisDist1and2 / thisDist1and3;
		double thisRatio2 = thisDist1and2 / thisDist2and3;
		double thisRatio3 = thisDist1and3 / thisDist2and3;
		
		//Variables coming from the alignment object
		Point3d alignPoint1 = vectorToPoint(alignmentObject.getPosOfPoint1());
		Point3d alignPoint2 = vectorToPoint(alignmentObject.getPosOfPoint2());
		Point3d alignPoint3 = vectorToPoint(alignmentObject.getPosOfPoint3());
		double alignDist1and2 = alignPoint1.distance(alignPoint2);
		double alignDist1and3 = alignPoint1.distance(alignPoint3);
		double alignDist2and3 = alignPoint2.distance(alignPoint3);
		double alignRatio1 = alignDist1and2 / alignDist1and3;
		double alignRatio2 = alignDist1and2 / alignDist2and3;
		double alignRatio3 = alignDist1and3 / alignDist2and3;
		
		//Difference in ratios between this object and the alignment object
		double difference1 = thisRatio1 - alignRatio1;
		double difference2 = thisRatio2 - alignRatio2;
		double difference3 = thisRatio3 - alignRatio3;
		
		//Average difference
		double avgDifference = (difference1 + difference2 + difference3) / 3;
		
		//Rounding
		avgDifference = avgDifference*100;
		avgDifference = Math.round(avgDifference);
		avgDifference = avgDifference/100;
		
		return avgDifference;
	}
	
	
	
	/**
	 * Moves the object in alignment with the parameter-object.
	 * NOTE: This operation mutates the object it's called on (not the parameter object) so
	 * that transformations will probably be unreliable. Another effect of this is that moveTo should NOT be called
	 * on the same object twice.
	 * @param CThreePObject alignmentObject The object that will be aligned to.
	 */
	public void moveTo(CThreePObject alignmentObject){
		if(this.getParent() != null){
			if(this.getParent() instanceof BranchGroup){
				mainGroup.removeChild(this);
			} else {
				mainGroup.removeChild(axisRotationGroup);
				((TransformGroup)this.getParent()).removeChild(this);
			}
		}
		
		innerRotationGroup= new TransformGroup();
		outerRotationGroup = new TransformGroup();
		axisRotationGroup = new TransformGroup();
		scaleGroup = new TransformGroup();
		moveGroup = new TransformGroup();
		
		//Create hierarchy of TransformGroups, to take care of different rotations
		scaleGroup.addChild(this);
		moveGroup.addChild(scaleGroup);
		innerRotationGroup.addChild(moveGroup);
		outerRotationGroup.addChild(innerRotationGroup);
		axisRotationGroup.addChild(outerRotationGroup);

		rescale(alignmentObject);
		placeInOrigo();
		innerAlign();
		outerAlign();
		objectAlign(alignmentObject);
		
		//estimate the center of the object by finding the mean of ref1,2,3
		float x = (float) ( getPosOfPoint1().x + getPosOfPoint2().x + getPosOfPoint3().x )/3;
		float y = (float) ( getPosOfPoint1().y + getPosOfPoint2().y + getPosOfPoint3().y )/3;
		float z = (float) ( getPosOfPoint1().z + getPosOfPoint2().z + getPosOfPoint3().z )/3;
		objectCenter = new Point3d(x,y,z);
		
		
		mainGroup.addChild(axisRotationGroup);
		
		
	}
	/**
	 * Rescales the object based on the difference of the distance between the objects two first points
	 * @param alignmentObject
	 */
	private void rescale(CThreePObject alignmentObject){
		Vector3d thisVec1 = this.getPosOfPoint1();
		Vector3d thisVec2 = this.getPosOfPoint2();
		Vector3d alignVec1 = alignmentObject.getPosOfPoint1();
		Vector3d alignVec2 = alignmentObject.getPosOfPoint2();
		Point3d thisPoint1 = vectorToPoint(thisVec1);
		Point3d thisPoint2 = vectorToPoint(thisVec2);
		Point3d alignPoint1 = vectorToPoint(alignVec1);
		Point3d alignPoint2 = vectorToPoint(alignVec2);
		double thisDist = thisPoint1.distance(thisPoint2);
		double alignDist = alignPoint1.distance(alignPoint2);
		Transform3D scaleTrans = new Transform3D();
		Transform3D ourTrans = new Transform3D();
		scaleGroup.getTransform(ourTrans);
		System.out.println("Scaled by: " + alignDist/thisDist);
		scaleTrans.setScale(alignDist/thisDist);
		ourTrans.mul(scaleTrans);
		scaleGroup.setTransform(ourTrans);
		
		
		//DEBUG FOLLOWS!
		thisVec1 = this.getPosOfPoint1();
		thisVec2 = this.getPosOfPoint2();
		alignVec1 = alignmentObject.getPosOfPoint1();
		alignVec2 = alignmentObject.getPosOfPoint2();
		thisPoint1 = vectorToPoint(thisVec1);
		thisPoint2 = vectorToPoint(thisVec2);
		alignPoint1 = vectorToPoint(alignVec1);
		alignPoint2 = vectorToPoint(alignVec2);
		System.out.println("Distance between virtual and actual point after rescale: " + thisPoint1.distance(thisPoint2) + ", " + alignPoint1.distance(alignPoint2));
		
	}
	
	/**
	 * Places the object in Origo
	 */
	private void placeInOrigo(){
		//Takes a zero-vector and subtract the vector of the first point.
		//Moving to this point with the entire TransformGroup will then result in point1 appearing at origo
		Transform3D tf3d = new Transform3D();
		Vector3d zeroVec = new Vector3d(0,0,0);
//		System.out.println(this.getPosOfPoint1());
		zeroVec.sub(this.getPosOfPoint1());
		tf3d.setTranslation(zeroVec);
//		System.out.println(zeroVec);
		moveGroup.setTransform(tf3d);
	}
	
	
	/**
	 * Aligns the X-coordinates wrt the Z axis and the center of the universe.
	 */
	private void innerAlign() {
		Vector3d point1Vec = this.getPosOfPoint1();
		Vector3d point2Vec = this.getPosOfPoint2();
//		System.out.println(point1Vec.toString() + "\n" + point2Vec.toString());
		
		// The rotationFactor SHOULD be enough to rotate the TG to where we want it.
		// However, since I'm not sure about directions etc we have the rest of this class
		// which basically is a trial-and-error rotation untill we're satisfied.
		double rotationFactor = Math.atan2(point2Vec.x , point2Vec.z);
		Transform3D rotationTransform = new Transform3D();
		Transform3D tpaacTransform = new Transform3D();
		double point1x = point1Vec.x;
		double point2x = point2Vec.x;
		double point1xOld = point1Vec.x;
		double point2xOld = point2Vec.x;
		boolean rotpositive;
		if(point2x > 0){
			rotpositive = true;
		} else {
			rotpositive = false;
		}
		while(true){
			
			if((point1x == point2x) || (Math.abs(point1x - point2x) <= model.Constants.ACCURACYVALUE)){
				break;
			}
			if(rotpositive){
				rotationTransform.rotY(rotationFactor);
			} else {
				rotationTransform.rotY(-rotationFactor);
			}
			innerRotationGroup.getTransform(tpaacTransform);
			tpaacTransform.mul(rotationTransform);
			innerRotationGroup.setTransform(tpaacTransform);
			
			point1Vec = this.getPosOfPoint1();
			point2Vec = this.getPosOfPoint2();
			point1x = point1Vec.x;
			point2x = point2Vec.x;
			
			//I've forgotten what the purpose of this is, but it works, usually.
			// Shouldn't use this any way, see the comment above the RotationFactor
			if(Math.abs(point1x - point2x) == Math.abs(point1xOld - point2xOld)){
				rotationFactor = rotationFactor / 2;
				point1xOld = point1x;
				point2xOld = point2x;
			} else if(point1x - point2x > point1xOld - point2xOld){
				rotationFactor = rotationFactor / 2;
				rotpositive = !rotpositive;
				point1xOld = point1x;
				point2xOld = point2x;
			}
//			System.err.println(point1Vec.toString() + "\n" + point2Vec.toString());

		}
//		System.out.println("Bomf!");
		
	}
	
	
	/**
	 * Aligns the Y-coordinates wrt the Z axis and the center of the universe.
	 */
	private void outerAlign() {
		Vector3d point1Vec = this.getPosOfPoint1();
		Vector3d point2Vec = this.getPosOfPoint2();
//		System.out.println(point1Vec.toString() + "\n" + point2Vec.toString());
		
		// The rotationFactor SHOULD be enough to rotate the TG to where we want it.
		// However, since I'm not sure about directions etc we have the rest of this class
		// which basically is a trial-and-error rotation untill we're satisfied.
		double rotationFactor = Math.atan2(point2Vec.y , point2Vec.z);
		Transform3D rotationTransform = new Transform3D();
		Transform3D tpaacTransform = new Transform3D();
		double point1x = point1Vec.y;
		double point2x = point2Vec.y;
		double point1xOld = point1Vec.y;
		double point2xOld = point2Vec.y;
		boolean rotpositive;
		if(point2x > 0){
			rotpositive = true;
		} else {
			rotpositive = false;
		}
		while(true){
			
			if((point1x == point2x) || (Math.abs(point1x - point2x) <= model.Constants.ACCURACYVALUE)){
				break;
			}
			if(rotpositive){
				rotationTransform.rotX(rotationFactor);
			} else {
				rotationTransform.rotX(-rotationFactor);
			}
			outerRotationGroup.getTransform(tpaacTransform);
			tpaacTransform.mul(rotationTransform);
			outerRotationGroup.setTransform(tpaacTransform);
			
			point1Vec = this.getPosOfPoint1();
			point2Vec = this.getPosOfPoint2();
			point1x = point1Vec.y;
			point2x = point2Vec.y;
			
			//I've forgotten what the purpose of this is, but it works, usually.
			// Shouldn't use this any way, see the comment above the RotationFactor
			if(Math.abs(point1x - point2x) == Math.abs(point1xOld - point2xOld)){
				rotationFactor = rotationFactor / 2;
				point1xOld = point1x;
				point2xOld = point2x;
			} else if(point2x > point2xOld){
				rotationFactor = rotationFactor / 2;
				rotpositive = !rotpositive;
				point1xOld = point1x;
				point2xOld = point2x;
			}
//			System.out.println(point1Vec.toString() + "\n" + point2Vec.toString());

		}
		
//		System.out.println("Bomf!");
		
		
		
	}
	
	
	/**
	 * Moves the object to the alignment-object, and rotates it into position. innerAlign() and outerAlign should be called
	 * before this one.
	 * @param CThreePObject alignmentObject The object that will be aligned to.
	 */
	private void objectAlign(CThreePObject alignmentObject){
		Vector3d aObjPoint1 = alignmentObject.getPosOfPoint1();
		Vector3d aObjPoint2 = alignmentObject.getPosOfPoint2();
		
		Point3d posPoint = new Point3d();
		Point3d lookPoint = new Point3d();
		
		posPoint.x = aObjPoint1.x;
		posPoint.y = aObjPoint1.y;
		posPoint.z = aObjPoint1.z;
		System.out.println(posPoint);

		
		lookPoint.x = aObjPoint2.x;
		lookPoint.y = aObjPoint2.y;
		lookPoint.z = aObjPoint2.z;
		System.out.println(lookPoint);
		
		//Positions the object at point1 of the other object, and looks at the other objects point 2.
		transformer.lookAt(posPoint, lookPoint, new Vector3d(0,1,0));
		transformer.invert();
		Transform3D rotTrans = new Transform3D();

		
		//Spins it 180� since the alignment classes are off by exactly this. It's a bit like using .invert after lookAt
		//Note to self: if we get a bug later on where it sometimes is mis-aligned by 180�, make the rotation later, after
		//checking if the second points align.
		rotTrans.rotY(Math.PI);
		transformer.mul(rotTrans);
		
		axisRotationGroup.setTransform(transformer);
		System.out.println(this.getPosOfPoint1() + ", " + this.getPosOfPoint2());
	
		
		Vector3d thisVec3 = this.getPosOfPoint3();
		Vector3d alignVec3 = alignmentObject.getPosOfPoint3();
		Point3d thisCompPoint = vectorToPoint(thisVec3);
		Point3d alignCompPoint = vectorToPoint(alignVec3);
		Point3d thisCompPointOld = thisCompPoint;
		Point3d alignCompPointOld = alignCompPoint;
		double rotFactor = 0.00001;
		rotTrans = new Transform3D();
		Transform3D tempTrans = new Transform3D();
		boolean rotpositive = true;
		boolean hasChosenRotationDirection = false;
		
		
		//Rotates slowly untill the two third points are aligned or as close to each other as possible.
		while(true){
			
			//Check if they are the same or very close.
			if(thisVec3.equals(alignVec3) || (thisCompPoint.distance(alignCompPoint) < model.Constants.ACCURACYVALUE)){
				return;
			}
			
			//Rotate one way or the other?
			if(rotpositive){
				rotTrans.rotZ(rotFactor);
			} else {
				rotTrans.rotZ(-rotFactor);
			}
			
			//Do the transform
			axisRotationGroup.getTransform(tempTrans);
			tempTrans.mul(rotTrans);
			axisRotationGroup.setTransform(tempTrans);
			
			thisVec3 = this.getPosOfPoint3();
			alignVec3 = alignmentObject.getPosOfPoint3();
			thisCompPoint = vectorToPoint(thisVec3);
			alignCompPoint = vectorToPoint(alignVec3);
			
			//Check if we've come as close as we can
			if(thisCompPoint.distance(alignCompPoint) > thisCompPointOld.distance(alignCompPointOld)){
				if(hasChosenRotationDirection){
					return;
				} else {
					hasChosenRotationDirection = !hasChosenRotationDirection;
					rotpositive = !rotpositive;
				}

				
			}
			
			thisCompPointOld = thisCompPoint;
			alignCompPointOld = alignCompPoint;
//			System.out.println(thisCompPoint.toString() + "\n" + alignCompPoint.toString());
//			break;

			
			
		}
		
		
		
		
		
		
		
		
		
	}
	
	
	/**
	 * Help-method that translates the coordinates of a vector into a point3d
	 * @param Vector3d vector The vector to be translated
	 * @return Point3d point The translated point
	 */
	private Point3d vectorToPoint(Vector3d vector){
		Point3d retval = new Point3d();
		retval.x = vector.x;
		retval.y = vector.y;
		retval.z = vector.z;
		return retval;
	}

	
	public Point3d getObjectCenter() {
		return objectCenter;
	}
	public void setObjectCenter(Point3d objectCenter) {
		this.objectCenter = objectCenter;
	}
	
	

}
