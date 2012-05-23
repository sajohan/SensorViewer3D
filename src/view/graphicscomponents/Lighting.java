package view.graphicscomponents;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

/**
 * Handles lighting
 * 
 * @author dannic
 * 
 */

public class Lighting extends BranchGroup {

	private DirectionalLight lighty;
	private DirectionalLight lightyy;
	private DirectionalLight lightx;
	private DirectionalLight lightxx;
	private DirectionalLight lightz;
	private DirectionalLight lightzz;
	private Color3f lightColor;

	/**
	 * Creates lights, adds them to this branchgroup
	 */
	public Lighting() {
		lightColor = new Color3f(0.5f, 0.5f, 0.5f);
		createLights();
		// Allow write access
		lighty.setCapability(DirectionalLight.ALLOW_COLOR_WRITE);
		lightyy.setCapability(DirectionalLight.ALLOW_COLOR_WRITE);
		lightx.setCapability(DirectionalLight.ALLOW_COLOR_WRITE);
		lightxx.setCapability(DirectionalLight.ALLOW_COLOR_WRITE);
		lightz.setCapability(DirectionalLight.ALLOW_COLOR_WRITE);
		lightzz.setCapability(DirectionalLight.ALLOW_COLOR_WRITE);
	}
	/**
	 * Sets the brightness of the light
	 * @param brightness desired brightness of lights
	 */
	public void setBrightness(float brightness) {

		// Get Brigthness
		lightColor = new Color3f(brightness, brightness, brightness);

		// Get all children

		lighty.setColor(lightColor);
		lightyy.setColor(lightColor);
		lightx.setColor(lightColor);
		lightxx.setColor(lightColor);
		lightz.setColor(lightColor);
		lightzz.setColor(lightColor);
	}

	/**
	 * Adds light to the scene Directional lights shining in 6 directions
	 * 
	 * 
	 */
	public void createLights() {

		lightColor = new Color3f(0.5f, 0.5f, 0.5f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				1000.0);

		// Light 1. Shining y -> -y
		Vector3f lightDir = new Vector3f(0.0f, -1.0f, 0.0f);
		lighty = new DirectionalLight(lightColor, lightDir);
		lighty.setInfluencingBounds(bounds);
		this.addChild(lighty);

		// Light 2. Shining -y -> y
		lightDir = new Vector3f(0.0f, 1.0f, 0.0f);
		lightyy = new DirectionalLight(lightColor, lightDir);
		lightyy.setInfluencingBounds(bounds);
		this.addChild(lightyy);

		// Light 3. Shining -x -> x
		lightDir = new Vector3f(1.0f, 0.0f, 0.0f);
		lightx = new DirectionalLight(lightColor, lightDir);
		lightx.setInfluencingBounds(bounds);
		this.addChild(lightx);

		// Light 4. Shining x -> -x
		lightDir = new Vector3f(-1.0f, 0.0f, 0.0f);
		lightxx = new DirectionalLight(lightColor, lightDir);
		lightxx.setInfluencingBounds(bounds);
		this.addChild(lightxx);

		// Light 5. Shining -z -> z
		lightDir = new Vector3f(0.0f, 0.0f, 1.0f);
		lightz = new DirectionalLight(lightColor, lightDir);
		lightz.setInfluencingBounds(bounds);
		this.addChild(lightz);

		// Light 6. Shining z -> -z
		lightDir = new Vector3f(0.0f, 0.0f, -1.0f);
		lightzz = new DirectionalLight(lightColor, lightDir);
		lightzz.setInfluencingBounds(bounds);
		this.addChild(lightzz);
	}

}
