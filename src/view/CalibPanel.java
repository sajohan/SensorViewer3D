package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.EastPanelListener;

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
	
	public CalibPanel(EastPanelListener eastPanelListener) {
		
		this.setLayout(new GridLayout(4,3));
		
		p1 = new JLabel("Calibration Point 1");
		p2 = new JLabel("Calibration Point 2");
		p3 = new JLabel("Calibration Point 3");
		
		getSWPos1 = new JButton("Set Pos");
		getSWPos1.setActionCommand("swPos1");
		getSWPos2 = new JButton("Set Pos");
		getSWPos2.setActionCommand("swPos2");
		getSWPos3 = new JButton("Set Pos");
		getSWPos3.setActionCommand("swPos3");
		
		
		getHWPos1 = new JButton("Get pos from robot");
		getHWPos1.setActionCommand("hwPos1");
		getHWPos2 = new JButton("Get pos from robot");
		getHWPos2.setActionCommand("hwPos2");
		getHWPos3 = new JButton("Get pos from robot");
		getHWPos3.setActionCommand("hwPos3");
		
		
		this.add(p1);
		this.add(getSWPos1);
		this.add(getHWPos1);
		this.add(p2);
		this.add(getSWPos2);
		this.add(getHWPos2);
		this.add(p3);
		this.add(getSWPos3);
		this.add(getHWPos3);
		
		//Add listeners
		getSWPos1.addActionListener(eastPanelListener);
		getSWPos2.addActionListener(eastPanelListener);
		getSWPos3.addActionListener(eastPanelListener);
		
		getHWPos1.addActionListener(eastPanelListener);
		getHWPos2.addActionListener(eastPanelListener);
		getHWPos3.addActionListener(eastPanelListener);

		//TODO Fix layout in calibpanel
		
	}
	
}
