package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

import model.SensorValue;
import model.SensorValues;

import view.TreePanel;

public class EastPanelListener extends Observable implements ActionListener, TreeModelListener {

	private TreePanel treePanel;
	
	private SensorValues values;
	
	public EastPanelListener(Observer obs, SensorValues values) {
		addObserver(obs);
		this.values = values;
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
//				removeSensorValues(treePanel.getSelectedNodes());
				treePanel.removeCurrentNodes();
				return;
			}else if (e.getActionCommand().equals("toggleVis")) {
				System.out.println("Toggling visibility");
				
			}
			
		}
		
	}


	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		
	}


	@Override
	public void treeNodesInserted(TreeModelEvent e) {
		
	}

	public void removeSensorValues(DefaultMutableTreeNode[] nodes){
		
		for(DefaultMutableTreeNode node : nodes){
			values.removeValue((SensorValue)node.getUserObject());
		}
		
	}
	
	

	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		
//		Object[] objects = e.getChildren();
//		
//		DefaultMutableTreeNode node = (DefaultMutableTreeNode)objects[0];
//		System.out.println((SensorValue)node.getUserObject());
//		values.removeValue((SensorValue)node.getUserObject());
//		System.out.println("Treenode removed");
	}


	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		System.out.println("Treestructure modified");
	}

	
	
}
