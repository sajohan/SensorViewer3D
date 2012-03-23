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

public class OptionsPanel extends JPanel implements ActionListener{
	
	private JPopupMenu cameraPopupMenu = createCameraMenu();
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
		
		handIcon = new ImageIcon(Constants.handIconURI);
		addSensorIcon = new ImageIcon(Constants.addSensorIconURI);
		selectionIcon = new ImageIcon(Constants.selectionIconURI);
		cameraIcon = new ImageIcon(Constants.cameraIconURI);
		
		
		handButton = createHandButton();
		sensorButton = createSensorCreationButton();
		selectionButton = createSelectionButton();
		cameraButton = createCameraButton();


		
		
		handButton.addActionListener(this);
		sensorButton.addActionListener(this);
		selectionButton.addActionListener(this);
		cameraButton.addActionListener(this);
		cameraButton.addMouseListener(new PopupListener());
		
		
		
		
		
		
		super.add(handButton);
		super.add(sensorButton);
		super.add(selectionButton);
		super.add(cameraButton);
		//super.add(pMen);

	}
	
	
	
	
	private JButton createHandButton(){
		JButton handButton = new JButton(handIcon);
		handButton.setSize(new Dimension(100,100));
		return handButton;
	}
	
	
	
	private JButton createSensorCreationButton(){
		JButton sensorButton = new JButton(addSensorIcon);
		return sensorButton;
	
	
	}
	
	private JButton createSelectionButton(){
		JButton selectionButton = new JButton(selectionIcon);
		return selectionButton;
	
	}
	private JButton createCameraButton(){
		JButton cameraButton = new JButton(cameraIcon);
		
		return cameraButton;
	
	}
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
	
    public void actionPerformed(ActionEvent e) {

    	if ("handtool".equals(e.getActionCommand())) {
        	System.out.println("Handtool Enabled");
        	//TODO
        }
    	if ("addsensortool".equals(e.getActionCommand())) {
        	System.out.println("Add Sensortool Enabled");
        	//TODO
        }
    	if ("selectiontool".equals(e.getActionCommand())) {
        	System.out.println("Selector Tool Enabled");
        	//TODO
        }
        if ("cameratool".equals(e.getActionCommand())) {
        	System.out.println("Cameras are on you....");
        	//TODO
        }
    }
    
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
