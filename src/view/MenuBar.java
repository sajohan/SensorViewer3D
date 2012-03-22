package view;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar{
	
	
	public MenuBar(){
		super.add(createFile());
		super.add(createEdit());
		super.add(createView());
		super.add(createPreferences());
		super.add(createHelp());
		
		
		
	}
	
	public JMenu createFile(){
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		return fileMenu;

	}
	
	
	public JMenu createEdit(){
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		return editMenu;

	}
	
	
	public JMenu createView(){
		JMenu viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);
		return viewMenu;

	}
	
	
	public JMenu createPreferences(){
		JMenu preferencesMenu = new JMenu("Preferences");
		preferencesMenu.setMnemonic(KeyEvent.VK_P);
		return preferencesMenu;

	}
	
	
	public JMenu createHelp(){
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		return helpMenu;

	}

}
