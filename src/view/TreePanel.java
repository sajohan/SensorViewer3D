package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

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
	
	public TreePanel(EastPanelListener eastPanelListener){
		

		
		// Temporary content of tree
	    Object[] hierarchy =
	        { "javax.swing",
	          "javax.swing.border",
	          "javax.swing.colorchooser",
	          "javax.swing.event",
	          "javax.swing.filechooser",
	          new Object[] { "javax.swing.plaf",
	                         "javax.swing.plaf.basic",
	                         "javax.swing.plaf.metal",
	                         "javax.swing.plaf.multi" },
	          "javax.swing.table",
	          new Object[] { "javax.swing.text",
	                         new Object[] { "javax.swing.text.html",
	                                        "javax.swing.text.html.parser" },
	                         "javax.swing.text.rtf" },
	          "javax.swing.tree",
	          "javax.swing.undo" };
	    DefaultMutableTreeNode root = processHierarchy(hierarchy);
	    
	    // Set the hierarchy
	    tree = new JTree(root);

		// Set to no icons
	    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	    renderer.setOpenIcon(null);
	    renderer.setClosedIcon(null);
	    renderer.setLeafIcon(null);
	    tree.setCellRenderer(renderer);
	    
		// Add the tree to a scrolling pane
		scrollPane = new JScrollPane();
		scrollPane.getViewport().add( tree );
	    
	    this.add(scrollPane);
		
	}

	  /** Small routine that will make node out of the first entry
	   *  in the array, then make nodes out of subsequent entries
	   *  and make them child nodes of the first one. The process is
	   *  repeated recursively for entries that are arrays.
	   */
	    
	  public DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
	    DefaultMutableTreeNode node =
	      new DefaultMutableTreeNode(hierarchy[0]);
	    DefaultMutableTreeNode child;
	    for(int i=1; i<hierarchy.length; i++) {
	      Object nodeSpecifier = hierarchy[i];
	      if (nodeSpecifier instanceof Object[])  // Ie node with children
	        child = processHierarchy((Object[])nodeSpecifier);
	      else
	        child = new DefaultMutableTreeNode(nodeSpecifier); // Ie Leaf
	      node.add(child);
	    }
	    return(node);
	  }

}
