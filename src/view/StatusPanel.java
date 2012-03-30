package view;

import static model.Constants.BRIGHT_INIT;
import static model.Constants.BRIGHT_MAX;
import static model.Constants.BRIGHT_MIN;
import static model.Constants.SCALE_INIT;
import static model.Constants.SCALE_MAX;
import static model.Constants.SCALE_MIN;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.EventListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SpringLayout;
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
	private JLabel status;
	private static JProgressBar progress;
	
	public StatusPanel(EventListener eventListener) {
		
		// 1 row 4 columns
		super.setLayout(new GridLayout(1,4));
		
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
		
		status = new JLabel("No object loaded..", JLabel.CENTER);
		
		
		// Add progressbar
		progress = new JProgressBar();
		progress.setPreferredSize( new Dimension( 300, 20 ) );
//		progress.setIndeterminate(true);
		progress.setMinimum( 0 );
		progress.setMaximum( 20 );
		progress.setValue( 0 );
		progress.setBounds( 20, 35, 260, 20 );
		progress.setVisible(false);
		
		
		super.add(scaleLabel);
		super.add(scaleslider);
		//super.add(new JLabel("                       "));
		super.add(brigthLabel);
		super.add(brightSlider);
		super.add(status);
		super.add(progress);
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
	
	/*
	 * Update progressbar, set to 100 to hide when task is done.
	 */
	public static void setProgress(int value){
		
		if(value > 99){
			progress.setVisible(false);
		}
		else if(value>=0){
			progress.setVisible(true);
		}
		progress.setValue(value);
		Rectangle progressRect = progress.getBounds();
		progressRect.x = 0;
		progressRect.y = 0;
		progress.paintImmediately( progressRect );
		
	}
}