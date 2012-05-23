package core.robotarm;

import core.model.Point3Dim;
import view.graphicscomponents.GraphicsPane;
import view.guicomponents.GUI;

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
//		Point3Dim sw = swCalibPoints[index];
//		Point3Dim p = new Point3Dim(sw.x+10, sw.y+2, sw.z+5);
//		hwCalibPoints[index] = p;
		
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
