package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.MenuBarListener;

public class MenuBar extends JMenuBar implements ActionListener{
	
	final JFileChooser fc = new JFileChooser();
	JMenu file;
	ActionListener al;
	
	public MenuBar(ActionListener al){
		
		this.al = al;
		file = createFile();
		
		super.add(file);
		super.add(createEdit());
		super.add(createView());
		super.add(createPreferences());
		super.add(createHelp());
		

		
		
		
		
	}
	public void setActionListener(ActionListener al){
		file.addActionListener(al);
	}
	
	public JMenu createFile(){
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem tempItem = new JMenuItem("Import...");
		tempItem.setActionCommand("import");
		tempItem.addActionListener(al);
		fileMenu.add(tempItem);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem)(e.getSource());
		if(source.getActionCommand().equals("import")){
	        int returnVal = fc.showOpenDialog(MenuBar.this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            //This is where a real application would open the file.
	           // log.append("Opening: " + file.getName() + "." + newline);
	        } else {
	            System.out.println("No file selected");
	        }
		}
		
	}

}
