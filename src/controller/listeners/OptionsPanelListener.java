package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import core.model.Constants;

/**
 * Title: OptionsPanelListener
 * Description: Listens to clicks on the options panel, is observed by Main.java
 * 
 * @author simoniv
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
	 * @param ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		setChanged();
		notifyObservers(e.getSource());	
	}


}
