package view;

import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * BorderLayoutDemo.java is a 1.4 application that requires no other files.
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    public static boolean RIGHT_TO_LEFT = false;

    public static void addComponentsToPane(Container contentPane) {
//    	Use BorderLayout. Default empty constructor with no horizontal and vertical
//    	gaps
    	contentPane.setLayout(new BorderLayout(5,5));
        if (!(contentPane.getLayout() instanceof BorderLayout)) {
            contentPane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }

        if (RIGHT_TO_LEFT) {
            contentPane.setComponentOrientation(
                java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        }

        JButton jbnSampleButtons = new JButton("Button 1 (PAGE_START)");
        OptionsPanel op = new OptionsPanel();
        contentPane.add(new OptionsPanel(), BorderLayout.PAGE_START);


    }

    private static void createAndShowGUI() {
 
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


    	
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("BorderLayout Source Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane and add swing components to it
        addComponentsToPane(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                createAndShowGUI();
            }
        });
        
    }
}