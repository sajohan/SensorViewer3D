package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;


/**
 * The left-hand toolbar.
 * @author Simon
 *
 */
public class OptionsPanel extends JPanel implements ActionListener{
	
	private JPopupMenu cameraPopupMenu;
	private JButton handButton;
	private JButton sensorButton;
	private JButton selectionButton;
	private JButton cameraButton;
	
	private ImageIcon handIcon;
	private ImageIcon addSensorIcon;
	private ImageIcon selectionIcon;
	private ImageIcon cameraIcon;
	
	
	
	
	
	
	public OptionsPanel(){
		super.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//Read icons
		handIcon = new ImageIcon(Constants.handIconURI);
		addSensorIcon = new ImageIcon(Constants.addSensorIconURI);
		selectionIcon = new ImageIcon(Constants.selectionIconURI);
		cameraIcon = new ImageIcon(Constants.cameraIconURI);
		
		//Create buttons and menues
		handButton = createButton(handIcon, Constants.handtooltip, Constants.handbutton);
		sensorButton = createButton(addSensorIcon, Constants.addsensortooltip, Constants.addsensorbutton);
		selectionButton = createButton(selectionIcon, Constants.selectiontooltip, Constants.selectionbutton);
		cameraButton = createButton(cameraIcon, Constants.cameratooltip, Constants.camerabutton);
//		sensorButton = createSensorCreationButton();
//		selectionButton = createSelectionButton();
//		cameraButton = createCameraButton();
		cameraPopupMenu = createCameraMenu();

	
		//Add action listeners
		handButton.addActionListener(this);
		sensorButton.addActionListener(this);
		selectionButton.addActionListener(this);
		cameraButton.addActionListener(this);
		cameraButton.addMouseListener(new PopupListener());
		

		
		
		//Add to toolbar
		super.add(handButton);
		super.add(sensorButton);
		super.add(selectionButton);
		super.add(cameraButton);

	}
	
	
	
	/**
	 * Creates a JButton with an icon
	 * @param the icon for the button
	 * @param the tooltip for the button as a string
	 * @param the action command string
	 * @return JButton
	 */
	private JButton createButton(ImageIcon ico, String tooltip, String actioncommand){
		JButton button = new JButton(ico);
		button.setToolTipText(tooltip);
		button.setActionCommand(actioncommand);
		return button;
	}
	

	
	
	/**
	 * Creates the popup menu that displays the camera options
	 * @return PopupMenu
	 */
	private JPopupMenu createCameraMenu(){
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
	 * Listeners for the buttons
	 */
    public void actionPerformed(ActionEvent e) {

    	if (Constants.handbutton.equals(e.getActionCommand())) {
        	System.out.println("Handtool Enabled");
        	//TODO
        }
    	if (Constants.addsensorbutton.equals(e.getActionCommand())) {
        	System.out.println("Add Sensortool Enabled");
        	//TODO
        }
    	if (Constants.selectionbutton.equals(e.getActionCommand())) {
        	System.out.println("Selector Tool Enabled");
        	//TODO
        }
        if (Constants.camerabutton.equals(e.getActionCommand())) {
        	System.out.println("Cameras are on you....");
        	//TODO
        }
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
            
                cameraPopupMenu.show(e.getComponent(),
                           e.getX(), e.getY());
            
        }
    }
    
    
	

}
