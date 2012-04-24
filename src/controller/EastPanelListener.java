package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class EastPanelListener extends Observable implements ActionListener {

	public EastPanelListener(Observer obs) {
		addObserver(obs);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		setChanged();
		notifyObservers(e.getSource());	
	}

	
	
}
