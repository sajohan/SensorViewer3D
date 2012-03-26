package view;

import java.util.Random;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.RenderingAttributes;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

/**
 * Title:Grid and axes
 * Description: Draws grid and axes
 * 
 * @author sajohan
 *
 */

public class Grid extends BranchGroup {
	//Axes
	private Shape3D xAxis;
	private Shape3D yAxis;
	private Shape3D zAxis;
	
	
	public Grid() {
		
		initAxes();
	}

	private void initAxes() {
		Appearance redApp = new Appearance();
    	Appearance blueApp = new Appearance();
    	Appearance greenApp = new Appearance();
    	
    	redApp.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
    	blueApp.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
    	greenApp.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
    	
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
        xAxis = new Shape3D(pla, redApp);
        xAxis.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        this.addChild(xAxis);
        
        //Y-axis
        plaPts[0] = new Point3f(0.0f, 1000.0f, 0.0f);
        plaPts[1] = new Point3f(0.0f, -1000.0f, 0.0f);
        pla = new LineArray(2, LineArray.COORDINATES);
        pla.setCoordinates(0, plaPts);
        yAxis = new Shape3D(pla, blueApp);
        this.addChild(yAxis);
        
        //Z-axis
        plaPts[0] = new Point3f(0.0f, 0.0f, 1000.0f);
        plaPts[1] = new Point3f(0.0f,  0.0f, -1000.0f);
        pla = new LineArray(2, LineArray.COORDINATES);
        pla.setCoordinates(0, plaPts);
        zAxis = new Shape3D(pla, greenApp);
        this.addChild(zAxis);
		
	}
	/**
	 * Set visibility of axes lines
	 * @param visible Visibility of the axes
	 */
	public void axesVisibility(boolean visible){
		RenderingAttributes renderAtt = new RenderingAttributes();
		renderAtt.setVisible(visible);
		
		xAxis.getAppearance().setRenderingAttributes(renderAtt);
		yAxis.getAppearance().setRenderingAttributes(renderAtt);
		zAxis.getAppearance().setRenderingAttributes(renderAtt);
		
	}
	
	
}
