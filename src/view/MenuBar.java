package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar implements ActionListener{
	
	final JFileChooser fc = new JFileChooser();
	
	public MenuBar(){
		JMenu file = createFile();
		file.addActionListener(this);
		
		super.add(file);
		super.add(createEdit());
		super.add(createView());
		super.add(createPreferences());
		super.add(createHelp());
		

		
		
		
		
	}
	
	public JMenu createFile(){
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem tempItem = new JMenuItem("Import...");
		tempItem.setActionCommand("import");
		tempItem.addActionListener(this);
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
