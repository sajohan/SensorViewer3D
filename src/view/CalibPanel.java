package view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.EastPanelListener;

public class CalibPanel extends JPanel {

	private JLabel p1;
	private JLabel p2;
	private JLabel p3;
	
	private JLabel headerText;
	
	private JButton getSWPos1;
	private JButton getSWPos2;
	private JButton getSWPos3;
	
	private JButton getHWPos1;
	private JButton getHWPos2;
	private JButton getHWPos3;
	
	private JButton calibButton;
	
	public CalibPanel(EastPanelListener eastPanelListener) {
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints headerLayout = new GridBagConstraints();
		GridBagConstraints calibLayout = new GridBagConstraints();
		
		p1 = new JLabel("Point 1:");	
		p2 = new JLabel("Point 2:");
		p3 = new JLabel("Point 3:");
		
		headerText = new JLabel("Calibration");
		headerText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		headerText.setHorizontalAlignment(SwingConstants.CENTER);
		
		getSWPos1 = new JButton("Set Pos");
		getSWPos1.setActionCommand("swPos1");
		getSWPos1.setToolTipText("Set calibration point 1 in software");
		getSWPos2 = new JButton("Set Pos");
		getSWPos2.setActionCommand("swPos2");
		getSWPos2.setToolTipText("Set calibration point 2 in software");
		getSWPos3 = new JButton("Set Pos");
		getSWPos3.setActionCommand("swPos3");
		getSWPos3.setToolTipText("Set calibration point 3 in software");
		
		
		getHWPos1 = new JButton("Get pos from robot");
		getHWPos1.setActionCommand("hwPos1");
		getHWPos1.setToolTipText("Get calibration point 1 from hardware");
		getHWPos2 = new JButton("Get pos from robot");
		getHWPos2.setActionCommand("hwPos2");
		getHWPos2.setToolTipText("Get calibration point 2 from hardware");
		getHWPos3 = new JButton("Get pos from robot");
		getHWPos3.setActionCommand("hwPos3");
		getHWPos3.setToolTipText("Get calibration point 3 from hardware");
		
		calibButton = new JButton("Calibrate");
		calibButton.setActionCommand("calibrate");
		calibButton.setEnabled(false);
		
		//ROW 1
		headerLayout.fill = GridBagConstraints.HORIZONTAL;
		headerLayout.gridx = 0;
		headerLayout.gridy = 0;
		headerLayout.gridwidth = 3;
		headerLayout.weightx = 0.0;
		headerLayout.insets = new Insets(20, 5, 20, 5);
		this.add(headerText, headerLayout);	
		//ROW 2
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		this.add(p1, c);
		c.gridx = 1;
		c.gridy = 1;
		this.add(getSWPos1,c);
		c.gridx = 2;
		c.gridy = 1;
		this.add(getHWPos1,c);
		//ROW 3
		c.gridx = 0;
		c.gridy = 2;
		this.add(p2,c);
		c.gridx = 1;
		c.gridy = 2;
		this.add(getSWPos2,c);
		c.gridx = 2;
		c.gridy = 2;
		this.add(getHWPos2,c);
		//ROW 4
		c.gridx = 0;
		c.gridy = 3;
		this.add(p3,c);
		c.gridx = 1;
		c.gridy = 3;
		this.add(getSWPos3,c);
		c.gridx = 2;
		c.gridy = 3;
		this.add(getHWPos3,c);
		//ROW 5
		calibLayout.fill = GridBagConstraints.HORIZONTAL;
		calibLayout.ipady = 10;
		calibLayout.insets = new Insets(20, 5, 5, 5);
		calibLayout.gridx = 0;
		calibLayout.gridy = 4;
		calibLayout.gridwidth = 3;
		calibLayout.weightx = 0.0;
		this.add(calibButton, calibLayout);
		
		
		//Add listeners
		getSWPos1.addActionListener(eastPanelListener);
		getSWPos2.addActionListener(eastPanelListener);
		getSWPos3.addActionListener(eastPanelListener);
		
		getHWPos1.addActionListener(eastPanelListener);
		getHWPos2.addActionListener(eastPanelListener);
		getHWPos3.addActionListener(eastPanelListener);

		calibButton.addActionListener(eastPanelListener);
	}
	
	public void enableCalib(boolean b){
		calibButton.setEnabled(b);
	}
	
}
