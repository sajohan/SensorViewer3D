package view.guicomponents;

import static core.model.Constants.BRIGHT_INIT;
import static core.model.Constants.BRIGHT_MAX;
import static core.model.Constants.BRIGHT_MIN;
import static core.model.Constants.SCALE_INIT;
import static core.model.Constants.SCALE_MAX;
import static core.model.Constants.SCALE_MIN;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.EventListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import core.model.Constants;

/**
 * South status panel
 * 
 * @author dannic
 * 
 */

public class StatusPanel extends JPanel{

	private JSlider scaleslider;
	private JSlider brightSlider;
	private JLabel status;
	
	public StatusPanel(EventListener eventListener) {
		
		super.setLayout(new GridBagLayout());
		
		// Create slider for scaling
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		
		JLabel scaleLabel = new JLabel("Scale", JLabel.CENTER);
		super.add(scaleLabel, c);
		
		c.gridx = 1;
		c.gridy = 0;
		scaleslider = createSlider(JSlider.HORIZONTAL, SCALE_MIN, SCALE_MAX,
				SCALE_INIT, Constants.scaleslider);
		super.add(scaleslider, c);
		
		// Create slider for brightness
		JLabel brigthLabel = new JLabel("Brightness", JLabel.CENTER);
		c.gridx=2;
		c.gridy=0;
		super.add(brigthLabel,c);
		
		brightSlider = createSlider(JSlider.HORIZONTAL, BRIGHT_MIN, BRIGHT_MAX,
				BRIGHT_INIT, Constants.brightslider);
		c.gridx=3;
		c.gridy=0;
		super.add(brightSlider,c);
		
		scaleslider.addChangeListener((ChangeListener) eventListener);
		brightSlider.addChangeListener((ChangeListener) eventListener);
		
		
		//Statusmessage
		status = new JLabel("No object loaded..", JLabel.CENTER);
		status.setHorizontalAlignment(JLabel.RIGHT);
		c.insets = new Insets(0, 0, 0, 10);
		c.anchor = GridBagConstraints.EAST;
		c.weightx = 0.8;
		c.gridx = 4;
		c.gridy = 0;
		super.add(status,c);
	}
	
	public JSlider createSlider(int facing, int min, int max, int init,
			String name) {
		JSlider slider = new JSlider(facing, min, max, init);
		// These should probably be constants..
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setName(name);
		return slider;
	}
	
	public JLabel getStatusLabel() {
		return status;
	}

	public void setStatusLabel(JLabel status) {
		this.status = status;
	}

}