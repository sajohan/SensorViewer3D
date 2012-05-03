package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.SensorValue;
import model.SensorValues;

import view.SensorValuesDrawer;
import view.TreePanel;

public class EastPanelListener extends Observable implements ActionListener, TreeModelListener, TreeSelectionListener {

	private TreePanel treePanel;
	
	private SensorValues values;
	
	private SensorValuesDrawer valDrawer;
	
	public EastPanelListener(Observer obs, SensorValues values, SensorValuesDrawer sensorValuesDrawer) {
		addObserver(obs);
		this.values = values;
		this.valDrawer = sensorValuesDrawer;
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
		
		Object[] objects = e.getChildren();
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)objects[0];
		values.removeValue((SensorValue)node.getUserObject());
	}


	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		System.out.println("Treestructure modified");
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		System.out.println("Node clicked");
		
		TreePath[] selected = e.getPaths();
		for(TreePath path : selected){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
			if(e.isAddedPath(path)){
				valDrawer.selectSphere((SensorValue)node.getUserObject());				
			}else{
				System.out.println("deselect");
				valDrawer.deselectSphere((SensorValue)node.getUserObject());				
			}
			
		}
	}

	
	
}
