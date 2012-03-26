package controller;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.media.j3d.BranchGroup;
import javax.swing.JSlider;

import model.Constants;

import core.modelloader.ObjectLoader;

import view.GUI;

public class Main implements Observer{
	
	ObjectLoader objLoader;
	GUI gui;

	/**
	 * @param args
	 */
	public static void main(String[] args) {


                new Main();

	}
	
	
	public Main(){
		gui = new GUI(this);
		objLoader = new ObjectLoader();
		//MenuBarListener menuBarListener = new MenuBarListener(this);
	}

	@Override
	public void update(Observable obs, Object obj) {
		if(obs instanceof MenuBarListener ){
			BranchGroup tempGroup = objLoader.getObject((File)obj);
			gui.loadNewGraphicsWindow(tempGroup);
			
		} else if(obs instanceof OptionsPanelListener){
			if(obj instanceof JSlider){
				JSlider source = (JSlider)obj;
			    if(source.getName().equals(Constants.brightslider)){
				    if (!source.getValueIsAdjusting()) {
				    	
				    	int newscale = (int)source.getValue();
				    	//Float f = Float.parseFloat(newscale);
				    	
				    	// set scale make call here
				    	System.out.println("Brigthness set to " + newscale + "");
				    	
				    }
			    }
			    if(source.getName().equals(Constants.scaleslider)){
				    if (!source.getValueIsAdjusting()) {
				    	
						int newscale = (int)source.getValue();
						// set scale make call here
						System.out.println("Scale set to " + newscale + "");
						Robot robot = null;
						try {
							robot = new Robot();
						} catch (AWTException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							System.out.println("Robot object was not initialized");
						}
						
						/*
						 * moves the mousepointer to center of 3D frame position, scrolls
						 */
							int RenderFrameCenterY = gui.getGraphicsPane().getLocationOnScreen().y + (gui.getGraphicsPane().getHeight() / 2);
							int RenderFrameCenterX = gui.getGraphicsPane().getLocationOnScreen().x + (gui.getGraphicsPane().getWidth() / 2);
							robot.mouseMove(RenderFrameCenterX, RenderFrameCenterY);            
							robot.mousePress(InputEvent.BUTTON1_MASK);
				            robot.mouseRelease(InputEvent.BUTTON1_MASK);
							robot.mouseWheel(newscale * Constants.SCALE_SPEED);
							source.setValue(Constants.SCALE_INIT); //reset the slider to center value
				    	
				    }
			    }
			}
		}
		
	}


}
