package view.guicomponents;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.RenderingAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;


import com.sun.j3d.utils.geometry.Sphere;

import core.model.SensorValue;

public class SensorRepresentation extends Sphere {

	private Color4f color = new Color4f(0.0f, 1.0f, 0.0f, 0.5f);
	
	//Is it visible?
	private boolean visible;
	
	//Is it selected?
	private boolean selected;
	
	//Default appearance handle
	private Appearance defaultAp;
	
	//Appearance when selected
	private Appearance selectedAp;
	
	//Sensor values
	private SensorValue sensVal;
	
	
	public SensorRepresentation(float radius, int divisions, Appearance ap, SensorValue sensVal) {
		super(radius, 0, divisions, ap);
		defaultAp = ap;
		
//		this.setCapability(ColoringAttributes.);
		this.setCapability(ColoringAttributes.ALLOW_COLOR_READ);
		defaultAp.getColoringAttributes().setCapability(ColoringAttributes.ALLOW_COLOR_WRITE);
		defaultAp.getColoringAttributes().setCapability(ColoringAttributes.ALLOW_COLOR_READ);
		defaultAp.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
		
		this.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
		
		
//		Color3f tempColor = new Color3f();
//		defaultAp.getColoringAttributes().getColor(tempColor);
//		tempColor.setY(0.5f);
//		selectedAp = defaultAp;
//		selectedAp.getColoringAttributes().setColor(tempColor);
		
		visible = true;
		selected = false;
		this.sensVal = sensVal;
		
	}
	
	/**
	 * Sets visibility of sphere
	 * @param isVisible Sets the visibility to this
	 */
	public void setVisible(boolean isVisible){
		//Checks if the current state differs from the desired state
		
		RenderingAttributes renderAtt = new RenderingAttributes();
		renderAtt.setVisible(isVisible);
		visible = isVisible;

		this.getAppearance().setRenderingAttributes(renderAtt);

	}
	
	/**
	 * Sets selectedness of sphere :3
	 * @param isVisible Sets the selectedness to this
	 */
	public void setSelected(boolean isSelected){
		//Checks if the current state differs from the desired state
		if(selected == !isSelected){
			if(isSelected){
				Color3f tempColor = new Color3f();
				defaultAp.getColoringAttributes().getColor(tempColor);
				tempColor.setY(0.5f);
				defaultAp.getColoringAttributes().setColor(tempColor);
			} else {
				Color3f tempColor = new Color3f();
				defaultAp.getColoringAttributes().getColor(tempColor);
				tempColor.setY(0.0f);
				defaultAp.getColoringAttributes().setColor(tempColor);
			}
		}
		selected = isSelected;
	}
	
	/**
	 * For changing the default appearance (and the selected appearance)
	 * @param ap The desired Appearance
	 */
	public void setDefaultAppearance(Appearance ap){
		super.setAppearance(ap);
		defaultAp = ap;
		
		Color3f tempColor = new Color3f();
		defaultAp.getColoringAttributes().getColor(tempColor);
		tempColor.y = 0.5f;
		selectedAp = defaultAp;
		selectedAp.getColoringAttributes().setColor(tempColor);
		
	}

	public SensorValue getSensorValue() {
		return sensVal;
	}

	public void setSensorValue(SensorValue sensVal) {
		this.sensVal = sensVal;
	}
	
	public boolean isVisible() {
		return visible;
	}
}
