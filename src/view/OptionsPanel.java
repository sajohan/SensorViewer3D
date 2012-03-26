package view;

import java.awt.Component;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import model.Constants;

import static model.Constants.*;

/**
 * The left-hand toolbar.
 * 
 * @author Simon
 * 
 */
public class OptionsPanel extends JPanel{

	private JPopupMenu cameraPopupMenu;
	private JButton handButton;
	private JButton sensorButton;
	private JButton selectionButton;
	private JButton cameraButton;

	private ImageIcon handIcon;
	private ImageIcon addSensorIcon;
	private ImageIcon selectionIcon;
	private ImageIcon cameraIcon;

	private JSlider scaleslider;
	private JSlider brigthslider;

	public OptionsPanel(EventListener eventListener) {
		super.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		// Read icons
		handIcon = new ImageIcon(Constants.handIconURI);
		addSensorIcon = new ImageIcon(Constants.addSensorIconURI);
		selectionIcon = new ImageIcon(Constants.selectionIconURI);
		cameraIcon = new ImageIcon(Constants.cameraIconURI);

		// Create buttons and menues
		handButton = createButton(handIcon, Constants.handtooltip,
				Constants.handbutton);
		sensorButton = createButton(addSensorIcon, Constants.addsensortooltip,
				Constants.addsensorbutton);
		selectionButton = createButton(selectionIcon,
				Constants.selectiontooltip, Constants.selectionbutton);
		cameraButton = createButton(cameraIcon, Constants.cameratooltip,
				Constants.camerabutton);
		// sensorButton = createSensorCreationButton();
		// selectionButton = createSelectionButton();
		// cameraButton = createCameraButton();
		cameraPopupMenu = createCameraMenu();

		// Create slider for scaling
		JLabel sliderLabel = new JLabel("Scale", JLabel.CENTER);
		scaleslider = createSlider(JSlider.HORIZONTAL, SCALE_MIN, SCALE_MAX, SCALE_INIT, Constants.scaleslider);
		
		// Create slider for brightness
		JLabel brigthLabel = new JLabel("Brightness", JLabel.CENTER);
		brigthslider = createSlider(JSlider.HORIZONTAL, SCALE_MIN, SCALE_MAX, SCALE_INIT, Constants.brightslider);

		// Add action listeners
		handButton.addActionListener((ActionListener)eventListener);
		sensorButton.addActionListener((ActionListener)eventListener);
		selectionButton.addActionListener((ActionListener)eventListener);
		cameraButton.addActionListener((ActionListener)eventListener);
		cameraButton.addMouseListener(new PopupListener());
		scaleslider.addChangeListener((ChangeListener)eventListener);

		// Add to toolbar
		super.add(handButton);
		super.add(sensorButton);
		super.add(selectionButton);
		super.add(cameraButton);
		super.add(sliderLabel);
		super.add(scaleslider);
		super.add(brigthLabel);
		super.add(brigthslider);

	}

	/**
	 * Creates a JButton with an icon
	 * 
	 * @param the
	 *            icon for the button
	 * @param the
	 *            tooltip for the button as a string
	 * @param the
	 *            action command string
	 * @return JButton
	 */
	private JButton createButton(ImageIcon ico, String tooltip,
			String actioncommand) {
		JButton button = new JButton(ico);
		button.setToolTipText(tooltip);
		button.setActionCommand(actioncommand);
		return button;
	}

	/**
	 * Creates the popup menu that displays the camera options
	 * 
	 * @return PopupMenu
	 */
	private JPopupMenu createCameraMenu() {
		JButton tempItem;
		JPopupMenu cameraMenu = new JPopupMenu("cameramenu");
		tempItem = new JButton("Free View");
		cameraMenu.add(tempItem);
		tempItem = new JButton("Tether");
		cameraMenu.add(tempItem);
		tempItem = new JButton("Lock X");
		cameraMenu.add(tempItem);
		tempItem = new JButton("Lock -X");
		cameraMenu.add(tempItem);
		tempItem = new JButton("Lock Y");
		cameraMenu.add(tempItem);
		tempItem = new JButton("Lock -Y");
		cameraMenu.add(tempItem);
		tempItem = new JButton("Lock Z");
		cameraMenu.add(tempItem);
		tempItem = new JButton("Lock -Z");
		cameraMenu.add(tempItem);

		return cameraMenu;
	}

	public JSlider createSlider(int facing, int min, int max, int init, String name) {
		JSlider slider = new JSlider(facing, min, max, init);
		// These should probably be constants..
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setName(name);
		return slider;
	}
/*
	/**
	 * Listeners for the buttons
	 *
	public void actionPerformed(ActionEvent e) {

		if (Constants.handbutton.equals(e.getActionCommand())) {
			System.out.println("Handtool Enabled");
			// TODO
		}
		if (Constants.addsensorbutton.equals(e.getActionCommand())) {
			System.out.println("Add Sensortool Enabled");
			// TODO
		}
		if (Constants.selectionbutton.equals(e.getActionCommand())) {
			System.out.println("Selector Tool Enabled");
			// TODO
		}
		if (Constants.camerabutton.equals(e.getActionCommand())) {
			System.out.println("Cameras are on you....");
			// TODO
		}
	}
*/

	/**
	 * Listener for the popup menu
	 * 
	 */
	class PopupListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {

			cameraPopupMenu.show(e.getComponent(), e.getX(), e.getY());

		}
	}
	/*
	class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {

		    JSlider source = (JSlider)e.getSource();
		    if(source.getName().equals(Constants.brightslider)){
			    if (!source.getValueIsAdjusting()) {
			    	
			    	int newscale = (int)source.getValue();
			    	//Float f = Float.parseFloat(newscale);
			    	
			    	// set scale make call here
			    	System.out.println("Brigthness set to " + newscale + "");
			    	
			    }
		    }
		    if(source.getName().equals(Constants.scaleslider)){
			    if (!source.getValueIsAdjusting()) {
			    	
					int newscale = (int)source.getValue();
					// set scale make call here
					System.out.println("Scale set to " + newscale + "");
					Robot robot = null;
					try {
						robot = new Robot();
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("Robot object was not initialized");
					}
					
					/*
					 * moves the mousepointer to center of 3D frame position, scrolls
					 *
//					int RenderFrameCenterY = GUI.getGraphicsPane().getLocationOnScreen().y + (GUI.getGraphicsPane().getHeight() / 2);
//					int RenderFrameCenterX = GUI.getGraphicsPane().getLocationOnScreen().x + (GUI.getGraphicsPane().getWidth() / 2);
//					robot.mouseMove(RenderFrameCenterX, RenderFrameCenterY);            
//					robot.mousePress(InputEvent.BUTTON1_MASK);
//		            robot.mouseRelease(InputEvent.BUTTON1_MASK);
//					robot.mouseWheel(newscale * SCALE_SPEED);
//					scaleslider.setValue(SCALE_INIT); //reset the slider to center value
			    	
			    }
		    }
		}

	}*/

}
