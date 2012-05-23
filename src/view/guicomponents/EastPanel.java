package view.guicomponents;

import java.awt.GridLayout;
import java.util.Observer;

import javax.swing.JPanel;

import view.graphicscomponents.SensorValuesDrawer;


import controller.listeners.EastPanelListener;
import core.model.SensorValues;

/**
 * The root panel that holds all the east panels
 * @author sajohan, dannic
 *
 */
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
	
	/**
	 * Shows or hides the calibration value
	 * @param boolean b 
	 */
	public void showCalibration(boolean b){
		if (b == true) {
			this.add(calibPanel);
			this.updateUI();
		}else{
			this.remove(calibPanel);
			this.updateUI();
		}
	}
	
	/**
	 * Fetches the calibration panel
	 * @return CalibPanel calibPanel The Calibration panel belonging to the east panel
	 */
	public CalibPanel getCalibPanel() {
		return calibPanel;
	}
	
	/**
	 * Fetches the tree panel
	 * @return TreePanel treePanel The treepanel belonging to the east panel
	 */
	public TreePanel getTreePanel() {
		return treePanel;
	}
}
