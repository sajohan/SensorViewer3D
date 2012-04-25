package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import controller.EastPanelListener;

public class EastPanel extends JPanel {

	private TreePanel treePanel;
	private CalibPanel calibPanel;
	
	
	public EastPanel(EastPanelListener eastPanelListener) {
		
		treePanel = new TreePanel(eastPanelListener);
		calibPanel = new CalibPanel(eastPanelListener);
		
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
}
