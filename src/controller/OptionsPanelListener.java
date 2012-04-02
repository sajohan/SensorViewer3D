package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

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
	 * Notifies listeners on button presses (currently Main.java)
	 */
	public void actionPerformed(ActionEvent e) {
		setChanged();
		notifyObservers(e.getSource());	
	}


}
