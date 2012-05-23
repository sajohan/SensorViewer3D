package view.guicomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;

import view.graphicscomponents.Grid;

/**
 * Class holding the top menubar of the program
 * 
 * @author simoniv, dannic, sajohan, chrfra
 * 
 */
public class MenuBar extends JMenuBar implements ActionListener {

	final JFileChooser fc = new JFileChooser();
	JMenu file;
	ActionListener al;

	/**
	 * Creates the menubar with the requested listener
	 * 
	 * @param al
	 *            The ActionListener
	 */
	public MenuBar(ActionListener al) {

		this.al = al;
		file = createFile();

		super.add(file);
		//super.add(createEdit());
		super.add(createView());
		super.add(createPreferences());
		super.add(createHelp());

	}

	public void setActionListener(ActionListener al) {
		file.addActionListener(al);
	}

	/**
	 * Creates the File menu options
	 * 
	 * @return JMenu The File menu
	 */
	public JMenu createFile() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		// Import
		JMenuItem importItem = new JMenuItem("Import...");
		importItem.setActionCommand("import");
		importItem.addActionListener(al);
		fileMenu.add(importItem);

		// Divider
		fileMenu.add(new JSeparator());

		// Exit
		JMenuItem exitItem = new JMenuItem("Exit Program");
		exitItem.setActionCommand("exit");
		exitItem.addActionListener(al);
		fileMenu.add(exitItem);

		return fileMenu;

	}

	/**
	 * Creates the Edit menu options
	 * 
	 * @return JMenu The Edit menu
	 */
	public JMenu createEdit() {
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		return editMenu;

	}

	/**
	 * Creates the View menu options
	 * 
	 * @return JMenu The View menu
	 */
	public JMenu createView() {
		JMenu viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);

		// Axes visibility checkbox
		JCheckBoxMenuItem axesVisibility = new JCheckBoxMenuItem(
				"Axes Visibility");
		axesVisibility.setModel(new JToggleButton.ToggleButtonModel());
		axesVisibility.setState(Grid.axesDefaultVis);
		axesVisibility.setActionCommand("axesVis");
		axesVisibility.addActionListener(al);
		viewMenu.add(axesVisibility);

		JCheckBoxMenuItem gridVisibility = new JCheckBoxMenuItem(
				"Grid Visibility");
		gridVisibility.setModel(new JToggleButton.ToggleButtonModel());
		gridVisibility.setState(Grid.gridDefaultVis);
		gridVisibility.setActionCommand("gridVis");
		gridVisibility.addActionListener(al);
		viewMenu.add(gridVisibility);

		return viewMenu;

	}

	/**
	 * Creates the Preferences menu options
	 * 
	 * @return JMenu The Preferences menu
	 */
	public JMenu createPreferences() {
		JMenu preferencesMenu = new JMenu("Preferences");
		preferencesMenu.setMnemonic(KeyEvent.VK_P);

		JMenuItem importItem = new JMenuItem("Set Com Port");
		importItem.setActionCommand("setComPort");
		importItem.addActionListener(al);
		preferencesMenu.add(importItem);

		JMenuItem calibItem = new JMenuItem("Perform Calibration");
		calibItem.setActionCommand("doCalib");
		calibItem.addActionListener(al);
		preferencesMenu.add(calibItem);

		return preferencesMenu;

	}

	/**
	 * Creates the Help menu options
	 * 
	 * @return JMenu The Help menu
	 */
	public JMenu createHelp() {
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.setActionCommand("about");
		aboutItem.addActionListener(al);
		helpMenu.add(aboutItem);

		return helpMenu;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		if (source.getActionCommand().equals("import")) {
			int returnVal = fc.showOpenDialog(MenuBar.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
			} else {
				System.out.println("No file selected");
			}
		}
	}

}
