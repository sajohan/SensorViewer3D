package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class EastPanel extends JPanel {

	private TreePanel treePanel = new TreePanel();
	private CalibPanel calibPanel = new CalibPanel();
	
	
	public EastPanel() {
		// Set size to at least 400
		this.setSize(400, 0);
		
		this.setLayout(new GridLayout(2, 1));
		
		this.add(treePanel);
		this.add(calibPanel);
		
	}
}
