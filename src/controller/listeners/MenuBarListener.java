package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import view.guicomponents.GUI;
import view.guicomponents.ValueFileFilter;

/**
 * Title: MenuBarListener
 * Description: Listens to the menu bar only, eg. file, edit, view...
 * 
 * @author sajohan, dannic, simoniv
 * 
 */
public class MenuBarListener extends Observable implements ActionListener {

	private final JFileChooser fc;
	private ValueFileFilter valFilter;

	/**
	 * MenuBarListener constructor
	 */
	public MenuBarListener() {
		valFilter = new ValueFileFilter();
		fc = new JFileChooser();
		fc.setFileFilter(valFilter);
	}

	/**
	 * MenuBarListener constructor
	 * @param Observer
	 */
	public MenuBarListener(Observer obs) {
		addObserver(obs);
		valFilter = new ValueFileFilter();
		fc = new JFileChooser();
		fc.setFileFilter(valFilter);
	}

	/**
	 * Receives events and calls methods depending on the event
	 * @param ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());


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
		}else if(source.getActionCommand().equals("exit")){
			//TODO prompt user to save work?
			System.exit(0);

		}else if(source.getActionCommand().equals("axesVis")){
			JCheckBoxMenuItem checkBox = (JCheckBoxMenuItem)source;
			setChanged();
			notifyObservers(checkBox);
		}else if(source.getActionCommand().equals("gridVis")){
			JCheckBoxMenuItem checkBox = (JCheckBoxMenuItem)source;
			setChanged();
			notifyObservers(checkBox);
		}
		else if(source.getActionCommand().equals("setComPort")){
			JMenuItem checkBox = (JMenuItem)source;
			setChanged();
			notifyObservers(checkBox);
		}else if(source.getActionCommand().equals("doCalib")){
			JMenuItem calibButton = (JMenuItem)source;
			setChanged();
			notifyObservers(calibButton);
		}
		else if (source.getActionCommand().equals("about")) {
			JOptionPane
					.showMessageDialog(
							null,
							"Bachelors Work @ Chalmers University Of Technology spring semester 2012\nAuthors: \nJohan Sandstrom\nDaniel Nicklasson\nChristian Fransson\nSimon Ivarsson");
		}


	}
}
