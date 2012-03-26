package view;

import javax.media.j3d.BranchGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * BorderLayoutDemo.java is a 1.4 application that requires no other files.
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sun.j3d.exp.swing.JCanvas3D;

import controller.MenuBarListener;

public class GUI {
    public boolean RIGHT_TO_LEFT = false;
    private static JFrame frame = new JFrame("SensorViewer3D");
    private static MenuBarListener mb; 
    private GraphicsPane graphicsPane;
    private Observer menuObserver;
    //private MenuBar menu = new MenuBar();
    public void addComponentsToPane(Container contentPane) {
    	contentPane.setLayout(new BorderLayout(0,0));

    	if (!(contentPane.getLayout() instanceof BorderLayout)) {
            contentPane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }

        //JButton jbnSampleButtons = new JButton("Button 1 (PAGE_START)");
        OptionsPanel op = new OptionsPanel();
        contentPane.add(op, BorderLayout.WEST);

        graphicsPane = new GraphicsPane(frame);
        contentPane.add(graphicsPane, BorderLayout.CENTER);
        mb = new MenuBarListener(menuObserver);
    	MenuBar menu = new MenuBar(mb);
    	frame.setJMenuBar(menu);
        
//      JCanvas3D j = new JCanvas3D();
        //jbnSampleButtons = new JButton("Button 2 (CENTER)");
        
    }
    
    
    public void loadNewGraphicsWindow(BranchGroup newModel){
    	Container contentPane = frame.getContentPane();
    	contentPane.remove(graphicsPane);
    	System.out.println("Pane removed");
        graphicsPane = new GraphicsPane(frame);
        graphicsPane.setObject(newModel);
        contentPane.add(graphicsPane, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        
    }


    private void createAndShowGUI() {
 
    	    try {
    	            // Set System L&F
    	        UIManager.setLookAndFeel(
    	            UIManager.getSystemLookAndFeelClassName());
    	    } 
    	    catch (UnsupportedLookAndFeelException e) {
    	       // handle exception
    	    }
    	    catch (ClassNotFoundException e) {
    	       // handle exception
    	    }
    	    catch (InstantiationException e) {
    	       // handle exception
    	    }
    	    catch (IllegalAccessException e) {
    	       // handle exception
    	    }

    	frame.setPreferredSize(new Dimension(900,900));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Set up the content pane and add swing components to it
        addComponentsToPane(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    public GUI(Observer obs) {
    	menuObserver = obs;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                createAndShowGUI();
            }
        });
        
    }
}