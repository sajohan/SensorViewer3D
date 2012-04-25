package core;

import core.robotarm.RobotHandler;
import view.GUI;
import model.Point3Dim;

public class Calibrator {

	private boolean needsCalib = true;
	
	private RobotHandler robot;
	
	private Point3Dim[] swCalibPoints = new Point3Dim[3];
	private Point3Dim[] hwCalibPoints = new Point3Dim[3];
	
	
	public Calibrator(RobotHandler robot) {
		this.robot = robot;
	}
	
	
	public void doCalib(){
		
		if (swCalibPoints.length != 3 || hwCalibPoints.length != 3) {
			GUI.printToStatus("You need to set all the calibration points");
		}else{
			
			
			//TODO Use C3P0 to calibrate
			System.out.println(toString());
			
		}
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
