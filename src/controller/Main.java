package controller;


import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.media.j3d.BranchGroup;

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
			
		}
		
	}


}
