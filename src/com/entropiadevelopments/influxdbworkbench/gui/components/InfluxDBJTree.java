package com.entropiadevelopments.influxdbworkbench.gui.components;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JTree;

import javax.swing.UIManager;
import javax.swing.plaf.IconUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBTreeElement;
import com.entropiadevelopments.influxdbworkbench.gui.common.GuiToolkit;

public class InfluxDBJTree extends JTree {

	private static final long serialVersionUID = 7918352429476456926L;

	public InfluxDBJTree(DefaultMutableTreeNode defaultMutableTreeNode) {

		super(defaultMutableTreeNode);

		UIManager.put("Tree.collapsedIcon", new IconUIResource(new NodeIcon('+')));
		UIManager.put("Tree.expandedIcon", new IconUIResource(new NodeIcon('-')));
		UIManager.put("Tree.selectedIcon", new IconUIResource(new NodeIcon('+')));

		InfluxDBStudioCellRenderer influxCellRenderer = new InfluxDBStudioCellRenderer();

		setShowsRootHandles(true);
		setCellRenderer(influxCellRenderer);
		setRootVisible(false);

	}

	class NodeIcon implements Icon {

		private static final int SIZE = 9;
		private char type;

		public NodeIcon(char type) {
			this.type = type;
		}

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			g.setColor(UIManager.getColor("Tree.background"));
			g.fillRect(x, y, SIZE - 1, SIZE - 1);

			g.setColor(UIManager.getColor("Tree.hash").darker());
			g.drawRect(x, y, SIZE - 1, SIZE - 1);

			g.setColor(UIManager.getColor("Tree.foreground"));
			g.drawLine(x + 2, y + SIZE / 2, x + SIZE - 3, y + SIZE / 2);
			if (type == '+') {
				g.drawLine(x + SIZE / 2, y + 2, x + SIZE / 2, y + SIZE - 3);
			}
		}

		public int getIconWidth() {
			return SIZE;
		}

		public int getIconHeight() {
			return SIZE;
		}
	}

	class InfluxDBStudioCellRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = -3748105400883784507L;
		private final Icon serverIcon;
		private final Icon databaseIcon;
		private final Icon measuramentIconIcon;

		public InfluxDBStudioCellRenderer() {
			serverIcon = GuiToolkit.getIcon("Connection.png");
			databaseIcon = GuiToolkit.getIcon("Database.png");
			measuramentIconIcon = GuiToolkit.getIcon("Measurement.png");
		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			if (value != null) {
				if (value.getClass().equals(InfluxDBTreeNode.class)) {
					InfluxDBTreeNode node = (InfluxDBTreeNode) value;
					InfluxDBTreeElement treeElelemt = (InfluxDBTreeElement) node.getUserObject();
					switch (treeElelemt.getNodeType()) {
					case InfluxDBTreeElement.SERVER:
						setIcon(serverIcon);
						break;
					case InfluxDBTreeElement.DATABASE:
						setIcon(databaseIcon);
						break;
					case InfluxDBTreeElement.MEASURAMENT:
						setIcon(measuramentIconIcon);
						break;
					}
				}
			}
			return this;
		}
	}
}