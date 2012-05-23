package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Title: StatusPanelListener
 * Description: Listens to clicks on the status panel, is observed by Main.java
 * 
 * @author dannic
 * 
 */
public class StatusPanelListener extends Observable implements ActionListener,
		ChangeListener {

	/**
	 * StatusPanel Constructor
	 */
	public StatusPanelListener() {
	}

	/**
	 * StatusPanel Constructor
	 * @param Observer
	 */
	public StatusPanelListener(Observer obs) {
		addObserver(obs);
	}

	/**
	 * Notifies Main on change in the sliders
	 * @param ChangeEvent
	 */
	@Override
	public void stateChanged(ChangeEvent e) {

		JSlider source = (JSlider) e.getSource();
		setChanged();
		notifyObservers(source);
	}

	/**
	 * Notifies Main on buttonclicks etc.
	 * NOT used for statuspanel yet.
	 * @param ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
