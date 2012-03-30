package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Popup window with progress bar
 * 
 * @author dannic
 * 
 */

public class ProgressBarPopup
{
	private JProgressBar pb;
	private JDialog dialog;

    public void getDialog() {
    	
		pb = new JProgressBar(0, 100);
		pb.setPreferredSize(new Dimension(200, 40));
		pb.setIndeterminate(true);
		pb.setString("Working");
		pb.setStringPainted(true);
		pb.setValue(0);
		JPanel center_panel = new JPanel();
		center_panel.add(pb);
		
		dialog = new JDialog(new JFrame(), "Loading object ...");
		
		 // center on screen
		dialog.setLocationRelativeTo(null);
		
		// Cannot close the dialog!
		//dialog.setUndecorated(true);
		
		dialog.setResizable(false);
		dialog.getContentPane().add(center_panel, BorderLayout.CENTER);
		dialog.pack();
		dialog.setFocusable(true);
		dialog.setVisible(true);
		
    }

    public void removeDialog(){
    	//dialog.setFocusable(false);
    	dialog.dispose();
    }
}