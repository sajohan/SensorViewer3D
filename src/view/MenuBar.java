package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;


public class MenuBar extends JMenuBar implements ActionListener {

	final JFileChooser fc = new JFileChooser();
	JMenu file;
	ActionListener al;

	public MenuBar(ActionListener al) {

		this.al = al;
		file = createFile();

		super.add(file);
		super.add(createEdit());
		super.add(createView());
		super.add(createPreferences());
		super.add(createHelp());

	}

	public void setActionListener(ActionListener al) {
		file.addActionListener(al);
	}

	public JMenu createFile() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		//Import
		JMenuItem importItem = new JMenuItem("Import...");
		importItem.setActionCommand("import");
		importItem.addActionListener(al);
		fileMenu.add(importItem);
		
		
		//Divider
		fileMenu.add(new JSeparator());
		
		//Exit
		JMenuItem exitItem = new JMenuItem("Exit Program");
		exitItem.setActionCommand("exit");
		exitItem.addActionListener(al);
		fileMenu.add(exitItem);
		
		
		return fileMenu;

	}

	public JMenu createEdit() {
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		return editMenu;

	}

	public JMenu createView() {
		JMenu viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);
		
		//Axes visibility checkbox
		JCheckBoxMenuItem axesVisibility = new JCheckBoxMenuItem("Axes Visibility");
		axesVisibility.setModel(new JToggleButton.ToggleButtonModel());
		axesVisibility.setState(Grid.axesDefaultVis);
		axesVisibility.setActionCommand("axesVis");
		axesVisibility.addActionListener(al);
		viewMenu.add(axesVisibility);
		
		
		JCheckBoxMenuItem gridVisibility = new JCheckBoxMenuItem("Grid Visibility");
		gridVisibility.setModel(new JToggleButton.ToggleButtonModel());
		gridVisibility.setState(Grid.gridDefaultVis);
		gridVisibility.setActionCommand("gridVis");
		gridVisibility.addActionListener(al);
		viewMenu.add(gridVisibility);
		
		return viewMenu;

	}

	public JMenu createPreferences() {
		JMenu preferencesMenu = new JMenu("Preferences");
		preferencesMenu.setMnemonic(KeyEvent.VK_P);
		
		JMenuItem importItem = new JMenuItem("Set Com Port");
		importItem.setActionCommand("setComPort");
		importItem.addActionListener(al);
		preferencesMenu.add(importItem);
		
		
		
		return preferencesMenu;

	}

	public JMenu createHelp() {
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		return helpMenu;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		if (source.getActionCommand().equals("import")) {
			int returnVal = fc.showOpenDialog(MenuBar.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// This is where a real application would open the file.
				// log.append("Opening: " + file.getName() + "." + newline);
			} else {
				System.out.println("No file selected");
			}
		}

	}

}
