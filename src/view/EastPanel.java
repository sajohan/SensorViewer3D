package view;

import java.awt.GridLayout;
import java.util.Observer;

import javax.swing.JPanel;

import model.SensorValues;

import controller.EastPanelListener;

public class EastPanel extends JPanel {

	private TreePanel treePanel;
	private CalibPanel calibPanel;
	
	
	public EastPanel(Observer obs, SensorValues values, SensorValuesDrawer sensorValuesDrawer) {
		
		
		EastPanelListener listener = new EastPanelListener(obs, values, sensorValuesDrawer);
		
		treePanel = new TreePanel(listener, values);
		calibPanel = new CalibPanel(listener);
		
		listener.setTreePanel(treePanel);
		
		
		// Set size to at least 400
		this.setSize(400, 0);
		
		this.setLayout(new GridLayout(2, 1));
		
		this.add(treePanel);
		
		//TODO Remove after debugging
		showCalibration(true);
	}
	
	public void showCalibration(boolean b){
		if (b == true) {
			this.add(calibPanel);
			this.updateUI();
		}else{
			this.remove(calibPanel);
			this.updateUI();
		}
	}
	
	public CalibPanel getCalibPanel() {
		return calibPanel;
	}
	
	public TreePanel getTreePanel() {
		return treePanel;
	}
}
