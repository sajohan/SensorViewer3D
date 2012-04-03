package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import model.Sensor;

/**
 * East tree panel
 * 
 * @author dannic
 * 
 */

public class TreePanel extends JPanel implements TreeSelectionListener {

	private JTree tree;
	private JScrollPane scrollPane;
	private DefaultMutableTreeNode rootnode;
	DefaultMutableTreeNode sensors = null;
	DefaultMutableTreeNode sensor = null;
	JButton btn = null;

	public TreePanel() {

		this.setLayout(new GridLayout(2,1));
		JPanel scroll = new JPanel();
		scroll.setLayout(new GridLayout(1,1));

		// Set size to at least 400
		this.setSize(400, 0);

		rootnode = new DefaultMutableTreeNode("Root");
		createNodes(rootnode);

		tree = new JTree(rootnode);
		tree.setEditable(true);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);

		// Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		// Set to no icons
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setOpenIcon(null);
		renderer.setClosedIcon(null);
		renderer.setLeafIcon(null);
		tree.setCellRenderer(renderer);
		
		scrollPane = new JScrollPane();
		scrollPane.getViewport().add(tree);

		JButton btn = new JButton("Add sensor");

		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				addSensor();
				System.out.println("You clicked the button");
			}
		});

		scroll.add(scrollPane);
		
		JPanel buttonpanel = new JPanel();
		buttonpanel.setLayout(new FlowLayout());
		buttonpanel.add(btn);
		
		this.add(scroll);
		this.add(buttonpanel);

	}

	private void createNodes(DefaultMutableTreeNode top) {
		sensors = new DefaultMutableTreeNode("List of sensors: ");
		top.add(sensors);

		sensor = new DefaultMutableTreeNode(new Sensor("Temperature", 0, 100));
		sensors.add(sensor);

		sensor = new DefaultMutableTreeNode(
				new Sensor("Magnetic field", 10, 20));
		sensors.add(sensor);
	}

	public void addSensor() {
		String ans = null;
		ans = JOptionPane.showInputDialog(null, "Sensor name?");
		if (ans != null) {
			sensor = new DefaultMutableTreeNode(new Sensor(ans, 0, 0));
			sensors.add(sensor);
		}
	}

	public void valueChanged(TreeSelectionEvent e) {
		// Returns the last path element of the selection.
		// This method is useful only when the selection model allows a single
		// selection.
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		if (node == null)
			// Nothing is selected.
			return;

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			Sensor sensor = (Sensor) nodeInfo;
			GUI.printToStatus("You chose: " + sensor.toString());
		} else {
			GUI.printToStatus("Not a leaf");
		}
	}

}
