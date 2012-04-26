package view;

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

import com.sun.j3d.exp.swing.JCanvas3D;

import controller.EastPanelListener;
import controller.MenuBarListener;
import controller.OptionsPanelListener;
import controller.StatusPanelListener;

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
	
	private EastPanelListener eastPanelListener;
	private EastPanel eastPanel;
	
	private ProgressBarPopup progresspopup;

	public GUI(Observer obs) {
		menuObserver = obs;

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				createAndShowGUI();
			}
		});
	}
	
	
	// private MenuBar menu = new MenuBar();
	public void addComponentsToPane(Container contentPane) {
		contentPane.setLayout(new BorderLayout(0, 0));

		if (!(contentPane.getLayout() instanceof BorderLayout)) {
			contentPane.add(new JLabel("Container doesn't use BorderLayout!"));
			return;
		}

		// JButton jbnSampleButtons = new JButton("Button 1 (PAGE_START)");
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
		
		// Add tree panel
		eastPanelListener = new EastPanelListener(menuObserver);
		eastPanel = new EastPanel(eastPanelListener);
		contentPane.add(eastPanel,BorderLayout.EAST);
		
		
		frame.setJMenuBar(menu);

		// JCanvas3D j = new JCanvas3D();
		// jbnSampleButtons = new JButton("Button 2 (CENTER)");

	}

	public void loadNewGraphicsWindow(BranchGroup newModel) {
		Container contentPane = frame.getContentPane();
		// graphicsPane.resetGraphics(frame);
		// contentPane.remove(graphicsPane);
		// graphicsPane = new GraphicsPane(frame)
		graphicsPane.setObject(newModel);
		// contentPane.add(graphicsPane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

	}

	public GraphicsPane getGraphicsPane() {
		return graphicsPane;
	}

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
	
	/*
	 * Show progress popup window
	 * 
	 */
	public void showProgress(){
		this.progresspopup.getDialog();
	}
	
	/*
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
