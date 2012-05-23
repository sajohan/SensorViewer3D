package view.guicomponents;

import javax.media.j3d.BranchGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * BorderLayoutDemo.java is a 1.4 application that requires no other files.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import view.graphicscomponents.GraphicsPane;


import com.sun.j3d.exp.swing.JCanvas3D;

import controller.listeners.EastPanelListener;
import controller.listeners.MenuBarListener;
import controller.listeners.OptionsPanelListener;
import controller.listeners.StatusPanelListener;
import core.model.SensorValues;

/**
 * The main GUI Class. Holds the structure of all the other panels and the graphics window.
 * @author simoniv, dannic, sajohan, chrfra
 *
 */
public class GUI {
	public boolean RIGHT_TO_LEFT = false;
	private JFrame frame = new JFrame("SensorViewer3D");
	private MenuBarListener menubarListener;
	private OptionsPanelListener optionspanelListener;
	private GraphicsPane graphicsPane;
	//initialized in constructor, observer is currently Main.java
	private Observer menuObserver; 
	
	private StatusPanelListener statuspanelListener;
	private static StatusPanel statuspanel;
	
	private EastPanel eastPanel;
	
	private ProgressBarPopup progresspopup;
	
	private SensorValues values;

	
	/**
	 * Initiates the gui with the relevant observer and values
	 * @param Observer obs The class that is to observe the GUI
	 * @param SensorValues The sensor Values
	 */
	public GUI(Observer obs, SensorValues values) {
		menuObserver = obs;
		this.values = values;
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				createAndShowGUI();
			}
		});
	}
	
	
	/**
	 * Initiates all the panels
	 * @param Container contentPane The container to be populated
	 */
	public void addComponentsToPane(Container contentPane) {
		contentPane.setLayout(new BorderLayout(0, 0));

		if (!(contentPane.getLayout() instanceof BorderLayout)) {
			contentPane.add(new JLabel("Container doesn't use BorderLayout!"));
			return;
		}

		optionspanelListener = new OptionsPanelListener(menuObserver);
		OptionsPanel op = new OptionsPanel(optionspanelListener);

		contentPane.add(op, BorderLayout.WEST);

		graphicsPane = new GraphicsPane(frame);
		contentPane.add(graphicsPane, BorderLayout.CENTER);
		menubarListener = new MenuBarListener(menuObserver);
		MenuBar menu = new MenuBar(menubarListener);
		
		// Add status panel
		statuspanelListener = new StatusPanelListener(menuObserver);
		statuspanel = new StatusPanel(statuspanelListener);
		contentPane.add(statuspanel,BorderLayout.SOUTH);
		
		progresspopup = new ProgressBarPopup();
		
		// Add east panel
		eastPanel = new EastPanel(menuObserver, values, graphicsPane.getSensorValuesDrawer());
		contentPane.add(eastPanel,BorderLayout.EAST);
		
		
		frame.setJMenuBar(menu);


	}

	/**
	 * A handle to the graphics window that updates it with a new model.
	 * @param BranchGroup newModel The graphical model that is to be updated
	 */
	public void loadNewGraphicsWindow(BranchGroup newModel) {
		Container contentPane = frame.getContentPane();
		graphicsPane.setObject(newModel);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * Gets the graphics pane
	 * @return GraphicsPane the graphics pane
	 */
	public GraphicsPane getGraphicsPane() {
		return graphicsPane;
	}

	/**
	 * Creates and shows the GUI
	 */
	private void createAndShowGUI() {

		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}

		frame.setPreferredSize(new Dimension(900, 900));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Icons/appicon.png"));

		// Set up the content pane and add swing components to it
		addComponentsToPane(frame.getContentPane());

		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Static method used to print statustext to statuslabel in south panel 
	 * 
	 */
	public static void printToStatus(String text){
		JLabel label = statuspanel.getStatusLabel();
		label.setForeground(Color.BLACK);
		label.setText(text);
	}
	/**
	 * Static method used to print statustext to statuslabel in south panel 
	 * 
	 */
	public static void printErrorToStatus(String text){
		JLabel label = statuspanel.getStatusLabel();
		label.setForeground(Color.RED);
		label.setText(text);
	}
	
	/**
	 * Show progress popup window
	 * 
	 */
	public void showProgress(){
		this.progresspopup.getDialog();
	}
	
	/**
	 * Hide progress popup window
	 * 
	 */
	public void hideProgress(){
		this.progresspopup.removeDialog();
	}


	public EastPanel getEastPanel() {
		return eastPanel;
	}
}
