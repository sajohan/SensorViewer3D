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

public class StatusPanelListener extends Observable implements ActionListener,
		ChangeListener {

	public StatusPanelListener() {
	}

	public StatusPanelListener(Observer obs) {
		addObserver(obs);
	}


	@Override
	public void stateChanged(ChangeEvent e) {

		JSlider source = (JSlider) e.getSource();
		setChanged();
		notifyObservers(source);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
