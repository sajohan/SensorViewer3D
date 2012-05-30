package controller;

import static core.model.Constants.*;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.vecmath.Vector3f;

import controller.listeners.EastPanelListener;
import controller.listeners.MenuBarListener;
import controller.listeners.OptionsPanelListener;
import controller.listeners.StatusPanelListener;

import core.model.Constants;
import core.model.Point3Dim;
import core.model.SensorType;
import core.model.SensorValue;
import core.model.SensorValues;
import core.modelloader.ObjectLoader;
import core.robotarm.Calibrator;
import core.robotarm.RobotHandler;
import view.graphicscomponents.GraphicsPane;
import view.graphicscomponents.Lighting;
import view.guicomponents.GUI;
import view.picking.Picker;

/**
 * Title: The main class
 * Description: Observes user input, eg. button clicks, sliders etc.
 * 
 * @author sajohan, dannic, chrfra, simoniv
 * 
 */
public class Main implements Observer {

	private GUI gui;
	private GraphicsPane graphicsPane;
	private SensorValues values;
	private RobotHandler robotHandler;
	private String comPort;
	private Calibrator calibrator;


	private static String PORT_NAMES[] = { 
		"/dev/tty.usbserial-A9007UX1", // Mac OS X
		"/dev/ttyUSB0", // Linux
		"COM4", // Windows
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new Main();

	}

	/**
	 * Main constructor
	 */
	public Main() {
		values = new SensorValues(this);
		robotHandler = new RobotHandler(values, calibrator);
		
		gui = new GUI(this, values);
		System.out.println("OS: " + System.getProperty("os.name"));
		
		calibrator = new Calibrator(robotHandler, gui);
	}
	
	/**
	 * Receives updates and calls methods depending on the update
	 */
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
		/*
		 * Called upon updating values in SensorValues
		 */
		else if (obs instanceof SensorValues){
			// draws the sensorvalue
			// if receiving values before graphicsPane is initialized, discard
			if (graphicsPane != null){
				graphicsPane.updateSensorValue((SensorValues)obs);
			}
		}
		/*
		 * East panel
		 */
		else if ( obs instanceof EastPanelListener){
			eastPanelUpdater(obs, obj);
			
		}

	}
	
	/**
	 * Receives updates from the menubar and calls methods depending on the update
	 */
	private void menuBarUpdater(Observable obs, Object obj){
		if(obj instanceof File){

			ObjectLoader ldr = new ObjectLoader((File) obj, gui);
			gui.showProgress();
			ldr.execute();
		}
		/*
		 * View dropdown
		 */ 
		else if(obj instanceof JCheckBoxMenuItem){
			JCheckBoxMenuItem chkBox = (JCheckBoxMenuItem)obj;
			//Toggle visibility
			if(chkBox.getActionCommand().equals("axesVis")){
				gui.getGraphicsPane().getGrid().axesVisibility(chkBox.getState());
			}else if(chkBox.getActionCommand().equals("gridVis")){
				gui.getGraphicsPane().getGrid().gridVisibility(chkBox.getState());
			}
		}
		/*
		 * Preferences dropdown, com-port
		 */
		else if(obj instanceof JMenuItem){
			JMenuItem chkBox = (JMenuItem)obj;
			if(chkBox.getActionCommand().equals("setComPort")){
				comPort = JOptionPane.showInputDialog("Input com port");
				if(!comPort.matches("\\d{1,2}")){
					JOptionPane.showMessageDialog(null, "Invalid portnumber, input port 0-99");
				}else{
					String os = System.getProperty("os.name");
                    if(os.startsWith("Windows")){
                        comPort = "COM"+comPort;
                    }
                    else if(os.equalsIgnoreCase("Linux")){
                    	comPort = "/dev/ttyUSB"+comPort;
                    }
                    robotHandler.connect(comPort);
				}				
			}else if(chkBox.getActionCommand().equals("doCalib")){
				gui.getEastPanel().showCalibration(true);
			}
		}
	}
	
	/**
	 * Receives updates from the statuspanel and calls methods depending on the update
	 */
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
			//scale slider, zooms in and out
			if (source.getName().equals(Constants.scaleslider)) {
				if (!source.getValueIsAdjusting()) {

					int newscale = (int) source.getValue();
					Robot robot = null;
					try {
						robot = new Robot();
					} catch (AWTException e1) {
						e1.printStackTrace();
						System.out
						.println("Robot object was not initialized");
					}

					/*
					 * moves the mousepointer to center of 3D frame
					 * position, scrolls
					 */
					Point p = MouseInfo.getPointerInfo().getLocation();
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
					// reset the slider to center value
					source.setValue(Constants.SCALE_INIT); 
					//move mousepointer back to slider
					robot.mouseMove(p.x, p.y);

				}
			}
		}
	}
	
	/**
	 *Is notified by OptionsPanelUpdater on button press.
	 *Updates view depending on what button was pressed.
	 */
	private void optionsPanelUpdater(Observable obs, Object obj){
		if (obj instanceof JButton) {
			JButton source = (JButton) obj;
			// get reference to graphicsPane to move camera on button presses (observavbles)
			graphicsPane = gui.getGraphicsPane();
			//disable mouse moveable camera, will re-enable if freeView was pressed.
			graphicsPane.getOrbit().setRotateEnable(false);
			//determine which button caused the event
			if(source.getActionCommand().equals(Constants.cameraXLock)){
				graphicsPane.lockOnAxle(CAM_LOCK_X, false);
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
			else if(source.getActionCommand().equals(Constants.cameraZRLock)){
				graphicsPane.lockOnAxle(CAM_LOCK_Z, true);
			}
			else if(source.getActionCommand().equals(Constants.freeCam)){
				graphicsPane.getOrbit().setRotateEnable(true); //enable mouse moveable camera
				graphicsPane.setPerspectivePolicy();
			}
			else if(source.getActionCommand().equals(Constants.addsensorbutton)){
				System.out.println("add sensor");
				
				/*
				 * send picker's last coordinates to com-port
				 */
				Point3Dim pickedPoint = Picker.getLastPickPos();
				Vector3f normal = Picker.getLastPickNormal();
				if(pickedPoint != null){
					SensorValue s = robotHandler.readSingleSensor((float)pickedPoint.x, (float)pickedPoint.y, (float)pickedPoint.z, normal.x, normal.y, normal.z);
					if(s != null){
						values.addValueToList(s);
						gui.getEastPanel().getTreePanel().addNode(s);
						System.out.println("value received from com-port: x: " + s.getX() + " y: " + s.getY() + " z: " + s.getZ());
					}
				}else{
					GUI.printErrorToStatus("No coordinate picked!");
				}
					
					

			}
			else if(source.getActionCommand().equals(Constants.handbutton)){
				graphicsPane.getOrbit().setRotateEnable(true); //enable mouse moveable camera
			}
		}
	}
	
	/**
	 * Receives updates from eastpanel and calls methods depending on the update
	 */
	private void eastPanelUpdater(Observable obs, Object obj){
		if (obj instanceof JButton) {
			JButton source = (JButton) obj;
			Point3Dim point;
			
			if(source.getActionCommand().equals("swPos1")){
				point = Picker.getLastPickPos();
				if(point != null){
					source.setText("Position X: " + point.x);
					calibrator.setSwCalibPoint(point, 0);
					gui.getEastPanel().getCalibPanel().enableCalib(calibrator.isCalibratable());
				}
			}
			else if(source.getActionCommand().equals("swPos2")){
				point = Picker.getLastPickPos();
				if(point != null){
					source.setText("Position X: " + point.x);
					calibrator.setSwCalibPoint(point, 1);
					gui.getEastPanel().getCalibPanel().enableCalib(calibrator.isCalibratable());
				}
			}else if (source.getActionCommand().equals("swPos3")) {
				point = Picker.getLastPickPos();
				if(point != null){
					source.setText("Position X: " + point.x);
					calibrator.setSwCalibPoint(point, 2);
					gui.getEastPanel().getCalibPanel().enableCalib(calibrator.isCalibratable());
				}
			}else if (source.getActionCommand().equals("hwPos1")) {
				point = robotHandler.getRobotPos();
				if(point != null){
					source.setText("Position X: " + point.x);
					calibrator.setHwCalibPoint(point, 0);
					gui.getEastPanel().getCalibPanel().enableCalib(calibrator.isCalibratable());
				}
				
			}else if (source.getActionCommand().equals("hwPos2")) {
				point = robotHandler.getRobotPos();
				if(point != null){
					source.setText("Position X: " + point.x);
					calibrator.setHwCalibPoint(point, 1);
					gui.getEastPanel().getCalibPanel().enableCalib(calibrator.isCalibratable());
				}
				
			}else if (source.getActionCommand().equals("hwPos3")) {
				point = robotHandler.getRobotPos();
				if(point != null){
					source.setText("Position X: " + point.x);
					calibrator.setHwCalibPoint(point, 2);
					gui.getEastPanel().getCalibPanel().enableCalib(calibrator.isCalibratable());
				}
				
			}else if (source.getActionCommand().equals("calibrate")){
				calibrator.doCalib();
				gui.getEastPanel().showCalibration(false);
				
			}
			
		}
	}


}
