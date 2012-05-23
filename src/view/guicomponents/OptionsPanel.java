package view.guicomponents;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import static core.model.Constants.*;

/**
 * The left-hand toolbar.
 * 
 * @author simoniv, chrfra, sajohan, dannic
 * 
 */
public class OptionsPanel extends JPanel {

	private JPopupMenu cameraPopupMenu;
	private JButton handButton;
	private JButton sensorButton;
	private JButton selectionButton;
	private JButton cameraButton;
	private JLabel cameraLabel;
	//camera mode buttons
	private JButton LockX;
	private JButton LockXR;
	private JButton freeView;
	private JButton LockY;
	private JButton LockYR;
	private JButton LockZ;
	private JButton LockZR;
	
	private ImageIcon handIcon;
	private ImageIcon addSensorIcon;
	private ImageIcon selectionIcon;
	private ImageIcon cameraIcon;

	/**
	 * Sets up the left-hand toolbar
	 * @param EventListener eventListener The listener responsible for handling the left toolbar
	 */
	public OptionsPanel(EventListener eventListener) {
//		super.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		super.setLayout(new GridLayout(19,1));
		// Fix overlapping on Canvas3D with this call
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);

		// Read icons
		handIcon = new ImageIcon(handIconURI);
		addSensorIcon = new ImageIcon(addSensorIconURI);
		selectionIcon = new ImageIcon(selectionIconURI);
		cameraIcon = new ImageIcon(cameraIconURI);

		// Create buttons and menus
		handButton = createButton(handIcon, handtooltip,
				handbutton);
		sensorButton = createButton(addSensorIcon, addsensortooltip,
				addsensorbutton);
		selectionButton = createButton(selectionIcon,
				selectiontooltip, selectionbutton);
		
		//padding between buttons and "camera:" label
		JLabel padding = new JLabel(" ");
		padding.setFont(new Font("arial",Font.BOLD,1));
		cameraLabel = new JLabel("Camera:");
		
		//Create camera control buttons
		freeView = createTextButton("Free    ","Free cam",freeCam);
		LockX = createTextButton("Lock X ","lock camera on x axis (Front)",cameraXLock);
		LockXR = createTextButton("Lock-X","lock camera on x axis (Back)",cameraXRLock);
		LockY = createTextButton("Lock Y ","lock camera on y axis (Front)",cameraYLock);
		LockYR = createTextButton("Lock-Y","lock camera on y axis (Back)",cameraYRLock);
		LockZ = createTextButton("Lock Z ","lock camera on z axis (Front)",cameraZLock);
		LockZR = createTextButton("Lock-Z","lock camera on z axis (Back)",cameraZRLock);

		// Add action listeners
		handButton.addActionListener((ActionListener) eventListener);
		sensorButton.addActionListener((ActionListener) eventListener);
		selectionButton.addActionListener((ActionListener) eventListener);
		freeView.addActionListener((ActionListener) eventListener);
		LockX.addActionListener((ActionListener) eventListener);
		LockXR.addActionListener((ActionListener) eventListener);
		LockY.addActionListener((ActionListener) eventListener);
		LockYR.addActionListener((ActionListener) eventListener);
		LockZ.addActionListener((ActionListener) eventListener);
		LockZR.addActionListener((ActionListener) eventListener);

		// Add all items to toolbar
		super.add(handButton);
		super.add(sensorButton);
		super.add(selectionButton);
		super.add(cameraLabel);
		super.add(freeView);
		super.add(LockX);
		super.add(LockXR);
		super.add(LockY);
		super.add(LockYR);
		super.add(LockZ);
		super.add(LockZR);

	}

	/**
	 * Creates a JButton with an icon
	 * 
	 * @param ico	the icon for the button
	 * @param the tooltip for the button as a string
	 * @param the action command string
	 * @return JButton button	the button created according to method arguments
	 */
    private JButton createButton(ImageIcon ico, String tooltip,
            String actioncommand) {

    JButton button = null;
    if(tooltip.equals(handtooltip)){
            button = new JButton("Hand tool");
    }
    else if(tooltip.equals(addsensortooltip)){
            button = new JButton("Add sensor");
    }
    else if(tooltip.equals(selectiontooltip)){
            button = new JButton("Selection Tool");
    }
    else{
            button = new JButton("x");
    }

    button.setToolTipText(tooltip);
    button.setActionCommand(actioncommand);
    return button;
}
	/**
	 * Creates a JButton with text
	 * 
	 * @param String text	the text for the button
	 * @param the tooltip for the button as a string
	 * @param the action command string
	 * @return JButton button	the button created according to method arguments
	 */
	private JButton createTextButton(String label, String tooltip,
			String actioncommand) {
		JButton button = new JButton(label);
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

}
