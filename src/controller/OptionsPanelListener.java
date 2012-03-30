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
/**
 * 
 * Listens to clicks on the options panel, is observed by Main.java
 *
 */
public class OptionsPanelListener extends Observable implements ActionListener {

	public OptionsPanelListener() {
	}

	public OptionsPanelListener(Observer obs) {
		addObserver(obs);
	}

	/**
	 * Listeners for the buttons
	 */
	public void actionPerformed(ActionEvent e) {
		notifyObservers();
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
		
		if (Constants.freeCam.equals(e.getActionCommand())) {
			System.out.println("FreeCam");
			// TODO
		}
		if (Constants.cameraXLock.equals(e.getActionCommand())) {
			System.out.println("cameraXLock");
			// TODO
		}
		if (Constants.cameraXRLock.equals(e.getActionCommand())) {
			System.out.println("cameraXRLock");
			// TODO
		}
		if (Constants.cameraYLock.equals(e.getActionCommand())) {
			System.out.println("cameraYLock");
			// TODO
		}
		if (Constants.cameraYRLock.equals(e.getActionCommand())) {
			System.out.println("cameraYRLock");
			// TODO
		}
		if (Constants.cameraZLock.equals(e.getActionCommand())) {
			System.out.println("cameraZLock");
			// TODO
		}
		if (Constants.cameraZRLock.equals(e.getActionCommand())) {
			System.out.println("cameraZRLock");
			// TODO
		}
	}


}
