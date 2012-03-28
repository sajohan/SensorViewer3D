package view;

import static model.Constants.BRIGHT_INIT;
import static model.Constants.BRIGHT_MAX;
import static model.Constants.BRIGHT_MIN;
import static model.Constants.SCALE_INIT;
import static model.Constants.SCALE_MAX;
import static model.Constants.SCALE_MIN;

import java.awt.FlowLayout;
import java.util.EventListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import model.Constants;

/**
 * South status panel
 * 
 * @author dannic
 * 
 */

public class StatusPanel extends JPanel{
	
	private JSlider scaleslider;
	private JSlider brightSlider;
	
	public StatusPanel(EventListener eventListener) {
		super.setLayout(new FlowLayout());
		
		// Create slider for scaling
		JLabel scaleLabel = new JLabel("Scale", JLabel.CENTER);
		scaleslider = createSlider(JSlider.HORIZONTAL, SCALE_MIN, SCALE_MAX,
				SCALE_INIT, Constants.scaleslider);

		// Create slider for brightness
		JLabel brigthLabel = new JLabel("Brightness", JLabel.CENTER);
		brightSlider = createSlider(JSlider.HORIZONTAL, BRIGHT_MIN, BRIGHT_MAX,
				BRIGHT_INIT, Constants.brightslider);
		
		scaleslider.addChangeListener((ChangeListener) eventListener);
		brightSlider.addChangeListener((ChangeListener) eventListener);
		
		super.add(scaleLabel);
		super.add(scaleslider);
		super.add(brigthLabel);
		super.add(brightSlider);
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


}