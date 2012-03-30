package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import view.GUI;

/**
 * 
 * Listens to the menu bar only, eg. file, edit, view... *
 */
public class MenuBarListener extends Observable implements ActionListener {

	final JFileChooser fc = new JFileChooser();

	public MenuBarListener() {
	}

	public MenuBarListener(Observer obs) {
		addObserver(obs);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
//		System.out.println(source.getParent().get);
		if(source.getParent().equals("View")){
			System.out.println("tja");
		}
		
		if (source.getActionCommand().equals("import")) {
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				setChanged();
				notifyObservers(file);
				GUI.printToStatus("Opened file: " + file.toString());
				System.out.println("should've been notified");
				// This is where a real application would open the file.
				// log.append("Opening: " + file.getName() + "." + newline);
			} else {
				System.out.println("No file selected");
			}
		}else if(source.getActionCommand().equals("axesVis")){
			JCheckBoxMenuItem checkBox = (JCheckBoxMenuItem)source;
			setChanged();
			notifyObservers(checkBox);
		}else if(source.getActionCommand().equals("gridVis")){
			JCheckBoxMenuItem checkBox = (JCheckBoxMenuItem)source;
			setChanged();
			notifyObservers(checkBox);
		}
		

	}
}
