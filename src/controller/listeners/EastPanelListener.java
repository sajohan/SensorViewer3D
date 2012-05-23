package controller.listeners;

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

import core.model.SensorValue;
import core.model.SensorValues;


import view.graphicscomponents.SensorValuesDrawer;
import view.guicomponents.TreePanel;

/**
 * Title: East Panel Listener
 * Description: Listens on input, eg. button clicks etc. and also listens for actions from the tree structure
 * 
 * @author sajohan, dannic
 * 
 */
public class EastPanelListener extends Observable implements ActionListener, TreeModelListener, TreeSelectionListener {

	private TreePanel treePanel;
	
	private SensorValues values;
	
	private SensorValuesDrawer valDrawer;
	
	/**
	 * EastPanelListener constructor
	 */
	public EastPanelListener(Observer obs, SensorValues values, SensorValuesDrawer sensorValuesDrawer) {
		addObserver(obs);
		this.values = values;
		this.valDrawer = sensorValuesDrawer;
	}
	
	public void setTreePanel(TreePanel treePanel) {
		this.treePanel = treePanel;
	}
	
	/**
	 * Receives events and calls methods depending on the event
	 * @param ActionEvent
	 */
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
				DefaultMutableTreeNode[] nodes = treePanel.getSelectedNodes();
				for(DefaultMutableTreeNode node : nodes){
					valDrawer.toggleVisibility((SensorValue)node.getUserObject());
				}
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

	/**
	 * Removes a list of nodes from the tree structure
	 */
	public void removeSensorValues(DefaultMutableTreeNode[] nodes){
		
		for(DefaultMutableTreeNode node : nodes){
			values.removeValue((SensorValue)node.getUserObject());
		}
		
	}
	
	/**
	 * Method is called when there is a deletion detected in the tree structure
	 */
	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		
		Object[] objects = e.getChildren();
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)objects[0];
		values.removeValue((SensorValue)node.getUserObject());
	}


	/**
	 * Method is called when there is a change detected in the tree structure
	 */
	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		System.out.println("Treestructure modified");
	}

	/**
	 * Method is called when there is node clicked in the tree structure
	 */
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
