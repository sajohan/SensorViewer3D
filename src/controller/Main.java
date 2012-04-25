package controller;

import static model.Constants.*;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;

import model.Point3Dim;
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

import model.Constants;
import model.SensorValue;
import model.SensorValues;
import core.Calibrator;
import core.Picker;
import core.modelloader.ObjectLoader;
import core.robotarm.RobotHandler;
import view.GUI;
import view.GraphicsPane;
import view.Lighting;
/**
 * Observes user input, eg. button clicks, sliders etc.
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

	public Main() {
		gui = new GUI(this);
		System.out.println("OS: " + System.getProperty("os.name"));
		
		values = new SensorValues(this);
		robotHandler = new RobotHandler(values);
		
		calibrator = new Calibrator(robotHandler, gui);
		
		//remove
		for(int i = 0; i<3;i++){
//			PORT_NAMES[2] = chop(PORT_NAMES[i]);
			System.out.println("portnames: "+ PORT_NAMES[i]);
		}
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
		/*
		 * Called upon updating values in SensorValues
		 */
		else if (obs instanceof SensorValues){
			// draws the sensorvalue
			// if receiving values before graphicsPane is initialized, discard
			if (graphicsPane != null) 
				graphicsPane.updateSensorValue((SensorValue)obj);
		}else if ( obs instanceof EastPanelListener){
			eastPanelUpdater(obs, obj);
			
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
                    if(os.equalsIgnoreCase("Windows")){
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

				//testing 
//				values = new SensorValues(this);
//				robotHandler = new RobotHandler(values);
				Point3Dim point1 = new Point3Dim(1,1,1);
				Point3Dim point2 = new Point3Dim(-1, 5, 0);
				Point3Dim[] points = {point1, point2};

				robotHandler.readSensorGroup(points);

				values.addValueToList(new SensorValue(4,4,0,50));

			}
			else if(source.getActionCommand().equals(Constants.handbutton)){
				graphicsPane.getOrbit().setRotateEnable(true); //enable mouse moveable camera
			}
		}
	}
	
	private void eastPanelUpdater(Observable obs, Object obj){
		if (obj instanceof JButton) {
			JButton source = (JButton) obj;
			Point3Dim point;
			
			if(source.getActionCommand().equals("swPos1")){
				point = Picker.getLastPick();
				if(point != null){
					source.setText("Position X: " + point.x);
					calibrator.setSwCalibPoint(point, 0);					
				}
			}
			else if(source.getActionCommand().equals("swPos2")){
				point = Picker.getLastPick();
				if(point != null){
					source.setText("Position X: " + point.x);
					calibrator.setSwCalibPoint(point, 1);
				}
			}else if (source.getActionCommand().equals("swPos3")) {
				point = Picker.getLastPick();
				if(point != null){
					source.setText("Position X: " + point.x);
					calibrator.setSwCalibPoint(point, 2);
				}
			}else if (source.getActionCommand().equals("hwPos1")) {
				point = robotHandler.getRobotPos();
				if(point != null){
					source.setText("Position X: " + point.x);
					calibrator.setHwCalibPoint(point, 0);
				}
				
			}else if (source.getActionCommand().equals("hwPos2")) {
				point = robotHandler.getRobotPos();
				source.setText("Position X: " + point.x);
				calibrator.setHwCalibPoint(point, 1);
				
			}else if (source.getActionCommand().equals("hwPos3")) {
				point = robotHandler.getRobotPos();
				source.setText("Position X: " + point.x);
				calibrator.setHwCalibPoint(point, 2);
				
			}else if (source.getActionCommand().equals("calibrate")){
				calibrator.doCalib();
				gui.getEastPanel().showCalibration(false);
				
			}
			
		}
	}


}
