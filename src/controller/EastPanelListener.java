package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

import view.TreePanel;

public class EastPanelListener extends Observable implements ActionListener, TreeModelListener {

	private TreePanel treePanel;
	
	public EastPanelListener(Observer obs) {
		addObserver(obs);
	}
	
	public void setTreePanel(TreePanel treePanel) {
		this.treePanel = treePanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//Do treepanel things
		if(e.getSource() instanceof JButton){
			if(e.getActionCommand().equals("remove")){
				System.out.println("Removing sensor");
				//TODO remove node
				return;
			}
			
		}
		
		setChanged();
		notifyObservers(e.getSource());	
	}


	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		
	}


	@Override
	public void treeNodesInserted(TreeModelEvent e) {
		
	}


	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		// TODO Notify observer that node has been removed
		
	}


	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		
	}

	
	
}
