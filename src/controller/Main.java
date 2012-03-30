package controller;

import static model.Constants.*;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JSlider;
import model.Constants;
import core.modelloader.ObjectLoader;
import view.GUI;
import view.GraphicsPane;
import view.Lighting;
/**
 * Observes user input, eg. button clicks, sliders etc.
 */
public class Main implements Observer {

	ObjectLoader objLoader;
	GUI gui;
	GraphicsPane graphicsPane;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new Main();

	}

	public Main() {
		gui = new GUI(this);
		objLoader = new ObjectLoader(null, null);
		// get reference to graphicsPane to move camera on button presses (observavbles)
		graphicsPane = gui.getGraphicsPane(); 
		// MenuBarListener menuBarListener = new MenuBarListener(this);
	}

	@Override
	public void update(Observable obs, Object obj) {
		/*
		 * Events from the menubar
		 */
		if (obs instanceof MenuBarListener) {
			menuBarUpdater(obs, obj);

		} 
		/*
		 * events from the status panel
		 */
		else if (obs instanceof StatusPanelListener) {
			statusPanelUpdater(obs, obj);
		}
		/*
		 * event from the options panel
		 */
		else if(obs instanceof OptionsPanelListener){
			optionsPanelUpdater(obs, obj);
		}

	}
	private void menuBarUpdater(Observable obs, Object obj){
		if(obj instanceof File){
			
			ObjectLoader ldr = new ObjectLoader((File) obj, gui);
			gui.showProgress();
			ldr.execute();
//			BranchGroup tempGroup = objLoader.getObject((File) obj);
//			gui.loadNewGraphicsWindow(tempGroup);
		//Is it a checkbox
		}else if(obj instanceof JCheckBoxMenuItem){
			JCheckBoxMenuItem chkBox = (JCheckBoxMenuItem)obj;
			//Toggle visibility
			if(chkBox.getActionCommand().equals("axesVis")){
				gui.getGraphicsPane().getGrid().axesVisibility(chkBox.getState());
			}else if(chkBox.getActionCommand().equals("gridVis")){
				gui.getGraphicsPane().getGrid().gridVisibility(chkBox.getState());
			}
		}
	}
	private void statusPanelUpdater(Observable obs, Object obj){
		if (obj instanceof JSlider) {
			JSlider source = (JSlider) obj;
			if (source.getName().equals(Constants.brightslider)) {
				if (!source.getValueIsAdjusting()) {

					int brigthness = (int) source.getValue();
					// Make float value.. should be value between 0.1 and
					// 1.0
					Float b = new Float(brigthness);
					b = b / 10;
					GraphicsPane gp = gui.getGraphicsPane();
					Lighting lights = gp.getLights();
					// Set brigthness
					lights.setBrightness(b);
					
					GUI.printToStatus("Brigthness set to " + b);
					//System.out.println("Brigthness set to " + b + "");

				}
			}
			if (source.getName().equals(Constants.scaleslider)) {
				if (!source.getValueIsAdjusting()) {

					int newscale = (int) source.getValue();
					// set scale make call here
					System.out.println("Scale set to " + newscale + "");
					Robot robot = null;
					try {
						robot = new Robot();
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out
								.println("Robot object was not initialized");
					}

					/*
					 * moves the mousepointer to center of 3D frame
					 * position, scrolls
					 */
					int RenderFrameCenterY = gui.getGraphicsPane()
							.getLocationOnScreen().y
							+ (gui.getGraphicsPane().getHeight() / 2);
					int RenderFrameCenterX = gui.getGraphicsPane()
							.getLocationOnScreen().x
							+ (gui.getGraphicsPane().getWidth() / 2);
					robot.mouseMove(RenderFrameCenterX, RenderFrameCenterY);
					robot.mousePress(InputEvent.BUTTON1_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
					robot.mouseWheel(newscale * Constants.SCALE_SPEED);
					source.setValue(Constants.SCALE_INIT); // reset the
															// slider to
															// center value
					gui.printToStatus("Zoom set to " + newscale);

				}
			}
		}
	}
	private void optionsPanelUpdater(Observable obs, Object obj){
		if (obj instanceof JButton) {
			System.out.println("IT'S A JBUTTON");
			JButton source = (JButton) obj;
			//determine which button caused the event
			if(source.getActionCommand().equals(Constants.cameraXLock)){
				System.out.println("HAKJSAHD");
//					graphicsPane.lockOnAxle(CAM_LOCK_X, false);
			}
			else if(source.getActionCommand().equals(Constants.cameraXRLock)){
				graphicsPane.lockOnAxle(CAM_LOCK_X, true);
			}
			else if(source.getActionCommand().equals(Constants.cameraYLock)){
				graphicsPane.lockOnAxle(CAM_LOCK_Y, false);
			}
			else if(source.getActionCommand().equals(Constants.cameraYRLock)){
				graphicsPane.lockOnAxle(CAM_LOCK_Y, true);
			}
			else if(source.getActionCommand().equals(Constants.cameraZLock)){
				graphicsPane.lockOnAxle(CAM_LOCK_Z, false);
			}
			else if(source.getActionCommand().equals(Constants.cameraYRLock)){
				graphicsPane.lockOnAxle(CAM_LOCK_Z, true);
			}
		}
	}
	

}
