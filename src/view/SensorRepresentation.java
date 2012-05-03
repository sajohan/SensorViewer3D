package view;

import javax.media.j3d.Appearance;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;

import model.SensorValue;

import com.sun.j3d.utils.geometry.Sphere;

public class SensorRepresentation extends Sphere {

	private Color4f color = new Color4f(0.0f, 1.0f, 0.0f, 0.5f);
	
	//Is it visible?
	private boolean visible;
	
	//Is it selected?
	private boolean selected;
	
	//Completely trancelucent apperance
	private Appearance invisibilityAp;
	
	//Default appearance handle
	private Appearance defaultAp;
	
	//Appearance when selected
	private Appearance selectedAp;
	
	//Sensor values
	private SensorValue sensVal;
	
	
	public SensorRepresentation(float radius, int divisions, Appearance ap, SensorValue sensVal) {
		super(radius, 0, divisions, ap);
		defaultAp = ap;
		
		invisibilityAp = new Appearance();
		
		invisibilityAp.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.FASTEST,1));
		
		Color3f tempColor = new Color3f();
		defaultAp.getColoringAttributes().getColor(tempColor);
		tempColor.y = 0.5f;
		selectedAp = defaultAp;
		selectedAp.getColoringAttributes().setColor(tempColor);
		
		visible = true;
		this.sensVal = sensVal;
		
	}
	
	/**
	 * Sets visibility of sphere
	 * @param isVisible Sets the visibility to this
	 */
	public void setVisible(boolean isVisible){
		//Checks if the current state differs from the desired state
		if(visible == !isVisible){
			if(isVisible){
				this.setAppearance(defaultAp);
			} else {
				this.setAppearance(invisibilityAp);
			}
		}
		return;
	}
	
	/**
	 * Sets selectedness of sphere :3
	 * @param isVisible Sets the selectedness to this
	 */
	public void setSelected(boolean isSelected){
		//Checks if the current state differs from the desired state
		if(selected == !isSelected){
			if(isSelected){
				this.setAppearance(defaultAp);
			} else {
				this.setAppearance(selectedAp);
			}
		}
		return;
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
	
}
