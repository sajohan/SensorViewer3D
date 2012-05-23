package view.graphicscomponents;

import java.util.Random;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.RenderingAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

/**
 * Title:Grid and axes 
 * Description: Draws grid and axes
 * 
 * @author sajohan
 * 
 */

public class Grid extends BranchGroup {
	
	/*Axes constants*/
	public static final float axesLineWidth = 2.0f;
	public static final boolean axesDefaultVis = true;
	public static final float axesOpacity = 0.3f;
	
	/*Grid Constants*/
	public static final float gridLineWidth = 2.0f;
	public static final float gridLinesDistance = 1f; //The distance between the gridlines
	public static final int nrGridLines = 50; // Needs to be an even number
	public static final boolean gridDefaultVis = true;
	public static final float gridOpacity = 0.9f; //1.0 is invisible, 0.0 is completely visible
	
	
	// Axes
	private Shape3D xAxis;
	private Shape3D yAxis;
	private Shape3D zAxis;
	
	// Grid
	private Shape3D xzGrid;
	private Shape3D yzGrid;
	private Shape3D xyGrid;
	
	BoundingSphere bounds = new BoundingSphere(new Point3d(0.0f, 0.0f, 0.0f), 10);
	

	public Grid() {

		initAxes();
		initGrid();
		this.setBounds(bounds);
	}
	
	/**
	 * Initiates the axes with appearance attributes, adds them as children to this branchgroup 
	 */
	private void initAxes() {
		Appearance redApp = new Appearance();
		Appearance blueApp = new Appearance();
		Appearance greenApp = new Appearance();

		redApp.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
		blueApp.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
		greenApp.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
		
		//Set transparency of lines
		TransparencyAttributes transparency = new TransparencyAttributes();
		transparency.setTransparency(axesOpacity);
		redApp.setTransparencyAttributes(transparency);
		blueApp.setTransparencyAttributes(transparency);
		greenApp.setTransparencyAttributes(transparency);
		

		// Make the pattern dashed
		LineAttributes dashLa = new LineAttributes();
		dashLa.setLineWidth(axesLineWidth);
		dashLa.setLinePattern(LineAttributes.PATTERN_DASH);
		dashLa.setLineAntialiasingEnable(true);
		redApp.setLineAttributes(dashLa);
		blueApp.setLineAttributes(dashLa);
		greenApp.setLineAttributes(dashLa);

		// Set Color
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

		// X-axis
		Point3f[] plaPts = new Point3f[2];
		plaPts[0] = new Point3f(-1000.0f, 0.0f, 0.0f);
		plaPts[1] = new Point3f(1000.0f, 0.0f, 0.0f);
		LineArray pla = new LineArray(2, LineArray.COORDINATES);
		pla.setCoordinates(0, plaPts);
		xAxis = new Shape3D(pla, redApp);
		xAxis.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
		this.addChild(xAxis);

		// Y-axis
		plaPts[0] = new Point3f(0.0f, 1000.0f, 0.0f);
		plaPts[1] = new Point3f(0.0f, -1000.0f, 0.0f);
		pla = new LineArray(2, LineArray.COORDINATES);
		pla.setCoordinates(0, plaPts);
		yAxis = new Shape3D(pla, blueApp);
		this.addChild(yAxis);

		// Z-axis
		plaPts[0] = new Point3f(0.0f, 0.0f, 1000.0f);
		plaPts[1] = new Point3f(0.0f, 0.0f, -1000.0f);
		pla = new LineArray(2, LineArray.COORDINATES);
		pla.setCoordinates(0, plaPts);
		zAxis = new Shape3D(pla, greenApp);
		//Set visibility to default
		axesVisibility(axesDefaultVis);
		zAxis.setBounds(bounds);
		this.addChild(zAxis);

	}
	
	/**
	 * Initiates the grid with appearance attributes, adds them as children to this branchgroup 
	 */
	public void initGrid(){
		Appearance app = new Appearance();
		app.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
		app.setCapability(TransparencyAttributes.ALLOW_BLEND_FUNCTION_WRITE);
		
		TransparencyAttributes transparency = new TransparencyAttributes();
		transparency.setTransparency(gridOpacity);
		app.setTransparencyAttributes(transparency);
		
		// Make the pattern dashed
		LineAttributes dashLa = new LineAttributes();
		dashLa.setLineWidth(gridLineWidth);
		dashLa.setLinePattern(LineAttributes.PATTERN_SOLID);
		dashLa.setLineAntialiasingEnable(true);
		app.setLineAttributes(dashLa);
		
		// Set Color
		Color3f color = new Color3f(1.0f, 1.0f, 1.0f);
		ColoringAttributes ca = new ColoringAttributes(color, ColoringAttributes.SHADE_FLAT);
		app.setColoringAttributes(ca);
		

		//XZ-grid
		xzGrid = getXZGridShape(app);
		xzGrid.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
		//Set visibility to default
		gridVisibility(gridDefaultVis);
		this.addChild(xzGrid);
		
		
		//Commented because it kind of looks like crap.
		
		//YZ-grid
//		yzGrid = getYZGridShape(app);
//		yzGrid.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
//		//Set visibility to default
//		gridVisibility(gridDefaultVis);
//		this.addChild(yzGrid);
		
	}
	


	/**
	 * Set visibility of axes lines
	 * 
	 * @param visible Visibility of the axes
	 */
	public void axesVisibility(boolean visible) {
		RenderingAttributes renderAtt = new RenderingAttributes();
		renderAtt.setVisible(visible);

		xAxis.getAppearance().setRenderingAttributes(renderAtt);
		yAxis.getAppearance().setRenderingAttributes(renderAtt);
		zAxis.getAppearance().setRenderingAttributes(renderAtt);

	}
	
	/**
	 * Set visibility of grid lines
	 * 
	 * @param visible Visibility of the grid
	 */
	public void gridVisibility(boolean visible){
		RenderingAttributes renderAtt = new RenderingAttributes();
		renderAtt.setVisible(visible);

		xzGrid.getAppearance().setRenderingAttributes(renderAtt);
	}
	
	/**
	 * Creates the grid lines over the X and Z axes. (Y=0)
	 * 
	 * @param app The appearance of the gridlines
	 * @return Shape3D The shape that contain the grid
	 */
	
	private Shape3D getXZGridShape(Appearance app){
		Point3f[] plaPts = new Point3f[nrGridLines*4];
		float nrLinesDrawn = 0;
		int index = 0;
		for(float i = 0 ; nrLinesDrawn<nrGridLines*2 ; i+=gridLinesDistance ){
			//Negative x from Z -> -Z
			plaPts[index] = new Point3f(-i, 0.0f, nrGridLines*(gridLinesDistance/2));
			index++;
			plaPts[index] = new Point3f(-i, 0.0f, -nrGridLines*(gridLinesDistance/2));
			index++;
			nrLinesDrawn++;
			//Positive x from Z -> -Z
			plaPts[index] = new Point3f(i, 0.0f, nrGridLines*(gridLinesDistance/2));
			index++;
			plaPts[index] = new Point3f(i, 0.0f, -nrGridLines*(gridLinesDistance/2));
			index++;
			nrLinesDrawn++;
			
			//Negative z from X -> -X
			plaPts[index] = new Point3f(nrGridLines*(gridLinesDistance/2), 0.0f, -i);
			index++;
			plaPts[index] = new Point3f(-nrGridLines*(gridLinesDistance/2), 0.0f, -i);
			index++;
			nrLinesDrawn++;
			//Positive z from X -> -X
			plaPts[index] = new Point3f(nrGridLines*(gridLinesDistance/2), 0.0f, i);
			index++;
			plaPts[index] = new Point3f(-nrGridLines*(gridLinesDistance/2), 0.0f, i);
			index++;
			nrLinesDrawn++;
		}
		
		LineArray pla = new LineArray(nrGridLines*4, LineArray.COORDINATES);
		pla.setCoordinates(0, plaPts);
		return new Shape3D(pla, app);
	}
	
	/**
	 * Creates the grid lines over the Y and Z axes. (X=0)
	 * 
	 * @param app The appearance of the gridlines
	 * @return Shape3D The shape that contain the grid
	 */
	private Shape3D getYZGridShape(Appearance app){
		Point3f[] plaPts = new Point3f[nrGridLines*4];
		float nrLinesDrawn = 0;
		int index = 0;
		for(float i = gridLinesDistance ; nrLinesDrawn<nrGridLines*2 ; i+=gridLinesDistance ){
			//Negative y from Z -> -Z
			plaPts[index] = new Point3f(0.0f, -i, nrGridLines*(gridLinesDistance/2));
			index++;
			plaPts[index] = new Point3f(0.0f, -i, -nrGridLines*(gridLinesDistance/2));
			index++;
			nrLinesDrawn++;
			//Positive y from Z -> -Z
			plaPts[index] = new Point3f(0.0f, i, nrGridLines*(gridLinesDistance/2));
			index++;
			plaPts[index] = new Point3f(0.0f, i, -nrGridLines*(gridLinesDistance/2));
			index++;
			nrLinesDrawn++;
			
			//Negative z from X -> -X
			plaPts[index] = new Point3f(0.0f, nrGridLines*(gridLinesDistance/2), -i);
			index++;
			plaPts[index] = new Point3f(0.0f, -nrGridLines*(gridLinesDistance/2), -i);
			index++;
			nrLinesDrawn++;
			//Positive z from X -> -X
			plaPts[index] = new Point3f(0.0f, nrGridLines*(gridLinesDistance/2), i);
			index++;
			plaPts[index] = new Point3f(0.0f, -nrGridLines*(gridLinesDistance/2), i);
			index++;
			nrLinesDrawn++;
		}
		
		LineArray pla = new LineArray(nrGridLines*4, LineArray.COORDINATES);
		pla.setCoordinates(0, plaPts);
		return new Shape3D(pla, app);
	}

}
