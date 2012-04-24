package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalibPanel extends JPanel {

	private JLabel p1;
	private JLabel p2;
	private JLabel p3;
	
	private JButton getSWPos1;
	private JButton getSWPos2;
	private JButton getSWPos3;
	
	private JButton getHWPos1;
	private JButton getHWPos2;
	private JButton getHWPos3;
	
	public CalibPanel() {
		
		this.setLayout(new GridLayout(4,3));
		
		p1 = new JLabel("Calibration Point 1");
		p2 = new JLabel("Calibration Point 2");
		p3 = new JLabel("Calibration Point 3");
		
		getSWPos1 = new JButton("Set Pos");
		getSWPos2 = new JButton("Set Pos");
		getSWPos3 = new JButton("Set Pos");
		
		
		getHWPos1 = new JButton("Get pos from robot");
		getHWPos2 = new JButton("Get pos from robot");
		getHWPos3 = new JButton("Get pos from robot");
		
		
		this.add(p1);
		this.add(getSWPos1);
		this.add(getHWPos1);
		this.add(p2);
		this.add(getSWPos2);
		this.add(getHWPos2);
		this.add(p3);
		this.add(getSWPos3);
		this.add(getHWPos3);
		
		//TODO Fix layout in calibpanel
		
	}
	
}
