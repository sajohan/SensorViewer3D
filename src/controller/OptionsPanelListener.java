package controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Constants;

public class OptionsPanelListener extends Observable implements ActionListener, ChangeListener  {
	
	public OptionsPanelListener(){}
	
	public OptionsPanelListener(Observer obs){
		addObserver(obs);
	}
	
	/**
	 * Listeners for the buttons
	 */
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




	@Override
	public void stateChanged(ChangeEvent e) {

	    JSlider source = (JSlider)e.getSource();
	    setChanged();
	    notifyObservers(source);
	    /*
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
		    	*
		    }
	    }*/
	}

	






}
