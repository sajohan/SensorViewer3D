package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import controller.EastPanelListener;

/**
 * East tree panel
 * 
 * @author dannic
 * 
 */

public class TreePanel extends JPanel{
	
	private	JTree tree;
	private	JScrollPane scrollPane;
	private DefaultMutableTreeNode rootNode;
	private DefaultTreeModel sensorModel;
	
	public TreePanel(EastPanelListener eastPanelListener){
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//  Parent to all nodes
		rootNode = new DefaultMutableTreeNode("Sensor values");
		
		sensorModel = new DefaultTreeModel(rootNode);
		sensorModel.addTreeModelListener(eastPanelListener);
		
//		Object hej = new Object();
//		addNode(hej);
//		
//		Object hej2 = new Object();
//		addNode(hej2);
	    // Set the hierarchy
	    tree = new JTree(sensorModel);

		// Set to no icons
	    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	    renderer.setOpenIcon(null);
	    renderer.setClosedIcon(null);
	    renderer.setLeafIcon(null);
	    tree.setCellRenderer(renderer);
	    
		// Add the tree to a scrolling pane
		scrollPane = new JScrollPane();
		scrollPane.getViewport().add( tree );
	    
		//Add the scrollpane to the JPanel
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
//		c.weightx = 1.0f;
	    this.add(scrollPane, c);
	    
	    // Remove sensor button
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
        JButton removeButton = new JButton("Remove");
        removeButton.setActionCommand("remove");
        removeButton.addActionListener(eastPanelListener);
        this.add(removeButton,c);

        //Hide sensor button
        c.gridx = 1;
		c.gridy = 1;
        JButton toggleVisButton = new JButton("Toggle Visibility");
        toggleVisButton.setActionCommand("toggleVis");
        toggleVisButton.addActionListener(eastPanelListener);
        this.add(toggleVisButton,c);
	}


	public void addNode(Object child){
		
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        
        sensorModel.insertNodeInto(childNode, rootNode, rootNode.getChildCount());
	}

	public void removeCurrentNodes(){
		
//		TreePath currentSelection = tree.getSelectionPath();
		TreePath[] currentSelection = tree.getSelectionPaths();
        if (currentSelection != null) {
        	for(TreePath path : currentSelection){
        		DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
        		(path.getLastPathComponent());
        		MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
        		if (parent != null) {
        			sensorModel.removeNodeFromParent(currentNode);
        		}
        	}
            
        } 
	}
}
