package core;

import core.robotarm.RobotHandler;
import view.GUI;
import view.GraphicsPane;
import model.Point3Dim;

public class Calibrator {

	private boolean needsCalib = true;
	
	private RobotHandler robot;
	private GUI gui;
	
	private Point3Dim[] swCalibPoints = new Point3Dim[3];
	private Point3Dim[] hwCalibPoints = new Point3Dim[3];
	
	
	public Calibrator(RobotHandler robot, GUI gui) {
		this.robot = robot;
		this.gui = gui;
	}
	
	
	public void doCalib(){
		
		for(Point3Dim p : hwCalibPoints){
			if(p == null){
				GUI.printErrorToStatus("You haven't set all the hardware calibration points");
				return;
			}
		}
		for(Point3Dim p : swCalibPoints){
			if(p == null){
				GUI.printErrorToStatus("You haven't set all the software calibration points");
				return;
			}
		}
		
		System.out.println("Before calibration");
		System.out.println(toString());
		GraphicsPane gp = gui.getGraphicsPane();
		gp.align(swCalibPoints, hwCalibPoints);
		
		System.out.println("Calibration done");
		
		needsCalib = false;
	}
	
	public Point3Dim[] getHwCalibPoints() {
		return hwCalibPoints;
	}
	
	public Point3Dim[] getSwCalibPoints() {
		return swCalibPoints;
	}
	
	public void setHwCalibPoint(Point3Dim point, int index) {
		hwCalibPoints[index] = point;
	}
	
	public void setSwCalibPoint(Point3Dim point, int index) {
		swCalibPoints[index] = point;
	}
	/**
	 * @return boolean Is the program ready to be calibrated?
	 */
	
	public boolean isCalibratable(){
		for(Point3Dim p : hwCalibPoints){
			if(p == null){
				return false;
			}
		}
		for(Point3Dim p : swCalibPoints){
			if(p == null){
				return false;
			}
		}
		return true;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("SW Calibration Points \n");
		for(Point3Dim p : swCalibPoints){
			s.append(p.toString());
			s.append("\n");
		}
		s.append("HW Calibration Points \n");
		for(Point3Dim p : hwCalibPoints){
			s.append(p.toString());
			s.append("\n");
		}
		return s.toString();
	}
}
