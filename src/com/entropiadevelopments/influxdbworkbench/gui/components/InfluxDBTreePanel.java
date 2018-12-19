package com.entropiadevelopments.influxdbworkbench.gui.components;

import java.util.List;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.influxdb.InfluxDB;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBConnection;
import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBDataBase;
import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBMeasuramet;
import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBTreeElement;
import com.entropiadevelopments.influxdbworkbench.facility.InfluxDBFacility;
import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedJPanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels.NewQueryActionPanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.CreateDataBaseAction;
//import com.entropiadevelopments.influxdbworkbench.gui.components.actions.DiagnosticsAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.DisconnectAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.DropDatabaseAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.DropMeasuramentAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.DropSeriesAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.NewQueryAction;
//import com.entropiadevelopments.influxdbworkbench.gui.components.actions.RefreshAction;
//import com.entropiadevelopments.influxdbworkbench.gui.components.actions.RunBackFillAction;
//import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowContinuousQueriesAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowFieldKeysAction;
//import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowQueriesAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowRetentionPoliciesAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowSeriesAction;
//import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowStaticsAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowTagKeysAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowTagValuesAction;
//import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowUsersAction;

import info.clearthought.layout.TableLayout;

public class InfluxDBTreePanel extends EnhancedJPanel {

	private static final long serialVersionUID = -5995287400775901972L;
	private InfluxDBWorkArea workAreaReference;
	private InfluxDBToolBar toolbarReference;

	private InfluxDBJTree tree;
	private DefaultMutableTreeNode rootNode;
	private DefaultTreeModel model;
	private ArrayList<InfluxDBConnection> connectedServers;

	public InfluxDBTreePanel(InfluxDBWorkArea workAreaReference) {

		this.workAreaReference = workAreaReference;

		connectedServers = new ArrayList<InfluxDBConnection>();

		rootNode = new DefaultMutableTreeNode("ROOT");
		setBorder(new EtchedBorder());
		tree = new InfluxDBJTree(rootNode);
		model = (DefaultTreeModel) tree.getModel();

		JScrollPane scrollPane = new JScrollPane(tree);
		double size[][] = { { 5, TableLayout.FILL, 5 }, { 5, TableLayout.FILL, 15 } };
		setLayout(new TableLayout(size));
		add(scrollPane, "1,1");

		tree.addMouseListener(new InfluxDBTreeMouseListener(tree, this));

	}

	public InfluxDBTreeElement getSelectedTreeElement() {
		TreePath[] paths = tree.getSelectionPaths();
		if (paths != null) {
			InfluxDBTreeNode selectedNode = (InfluxDBTreeNode) paths[0].getLastPathComponent();
			return (InfluxDBTreeElement) selectedNode.getUserObject();

		} else {
			return null;
		}
	}

	public void addInfluxDBServer(InfluxDBConnection influxDBConnection) {
		if (isAlreadyConnected(influxDBConnection)) {
			return;
		} else {
			InfluxDBTreeNode server = createServerNode(influxDBConnection);
			rootNode.add(server);
			model.reload(rootNode);
			tree.repaint();
			connectedServers.add(influxDBConnection);
		}
	}
	
	private InfluxDBTreeNode createServerNode(InfluxDBConnection influxDBConnection) {
		InfluxDBTreeNode server = new InfluxDBTreeNode(influxDBConnection);
		InfluxDBFacility facility = InfluxDBFacility.getFacilityReference();
		InfluxDB influxDB = facility.getInfluxDB(influxDBConnection);
		if(influxDBConnection.getDatabase().equals("")){
			for (String databaseName : influxDB.describeDatabases()) {
				createDatabaseNode( databaseName, influxDBConnection,  facility,  server);
			}
		}
		else {
			createDatabaseNode( influxDBConnection.getDatabase(), influxDBConnection,  facility,  server);
		}
		return server;
	}
	
	private void createDatabaseNode(String databaseName,InfluxDBConnection influxDBConnection, InfluxDBFacility facility, InfluxDBTreeNode server) {
		InfluxDBDataBase influxDBDataBase = new InfluxDBDataBase(databaseName, influxDBConnection);
		InfluxDBTreeNode database = new InfluxDBTreeNode(influxDBDataBase);
		QueryResult queryResult = facility.query("SHOW MEASUREMENTS", databaseName, influxDBConnection);
		if (!queryResult.hasError()) {
			List<Result> results = queryResult.getResults();
			if (results != null) {
				for (Result resut : results) {
					List<Series> series = resut.getSeries();
					if (series != null) {
						for (Series serie : resut.getSeries()) {
							List<List<Object>> values = serie.getValues();
							if (values != null) {
								for (Object valuesList : values) {
									if (valuesList != null) {
										ArrayList<Object> value = (ArrayList<Object>) valuesList;
										for (Object obj : value) {
											InfluxDBTreeNode measurement = new InfluxDBTreeNode(
													new InfluxDBMeasuramet(obj.toString(), database.toString(),
															influxDBConnection));
											database.add(measurement);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		server.add(database);
	}

	public void addDatabase(InfluxDBConnection connection, String databaseName) {

		for (int index = 0; index < rootNode.getChildCount(); index++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) rootNode.getChildAt(index);
			InfluxDBConnection existingConnection = (InfluxDBConnection) child.getUserObject();
			if (existingConnection.getID().equals(connection.getID())) {
				child.add(new InfluxDBTreeNode(new InfluxDBDataBase(databaseName, connection)));
				model.reload(child);
			}
		}

		tree.repaint();
	}

	public void removeSever(InfluxDBConnection connection) {
		for (int index = 0; index < rootNode.getChildCount(); index++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) rootNode.getChildAt(index);
			InfluxDBConnection existingConnection = (InfluxDBConnection) child.getUserObject();

			if (existingConnection.getID().equals(connection.getID())) {
				rootNode.remove(child);
			}
		}
		model.reload(rootNode);
		tree.repaint();
	}

	public void disconnectServer(InfluxDBConnection connection) {
		removeSever(connection);
		connectedServers.remove(connection);
		toolbarReference.setEnableAll(false);
		InfluxDBFacility.getFacilityReference().closeAndRemoveConnection(connection);
	}

	public boolean isAlreadyConnected(InfluxDBConnection influxDBConnection) {
		for (InfluxDBConnection conn : connectedServers) {
			if (conn.getID().equals(influxDBConnection.getID()))
				return true;
		}
		return false;

	}

	public void removeNode(InfluxDBTreeElement selectedTreeElement) {

		Enumeration<DefaultMutableTreeNode> enumeration = rootNode.preorderEnumeration();
		while (enumeration.hasMoreElements()) {
			DefaultMutableTreeNode node = enumeration.nextElement();
			if (node.getUserObject().equals(selectedTreeElement)) {
				node.removeFromParent();
			}
		}
		model.reload(rootNode);
		tree.repaint();

	}
	public void refresh() {
		// TODO   Pendind to implement this feature

		
	}
	public void setToolBarReference(InfluxDBToolBar toolBar) {
		this.toolbarReference = toolBar;

	}

	class InfluxDBTreeMouseListener extends MouseAdapter {

		private InfluxDBJTree tree;
		private InfluxDBTreePanel treePanel;

		private static final int SERVER = 2;
		private static final int DATABASE = 3;
		private static final int MEASURAMENT = 4;

		public InfluxDBTreeMouseListener(InfluxDBJTree tree, InfluxDBTreePanel treePanel) {
			this.tree = tree;
			this.treePanel = treePanel;
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() > 1) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (node == null)
					return;
				InfluxDBTreeElement treeElement = (InfluxDBTreeElement) node.getUserObject();
				String measuramentName = "measurament";
				if (treeElement.getClass().equals(InfluxDBMeasuramet.class)) {
					measuramentName = ((InfluxDBMeasuramet) treeElement).toString();
					String databaseName = ((InfluxDBMeasuramet) treeElement).getDatabase();
					workAreaReference.addWorkPanel(
							new NewQueryActionPanel(treeElement.getConnection(), measuramentName, databaseName));
					super.mouseClicked(e);
				} else {
					super.mouseClicked(e);
				}
			}
		}
		public void mousePressed(MouseEvent mouseEvent) {

			if (SwingUtilities.isRightMouseButton(mouseEvent)) {
				TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
				Rectangle pathBounds = tree.getUI().getPathBounds(tree, path);

				if (pathBounds != null && pathBounds.contains(mouseEvent.getX(), mouseEvent.getY())) {
					JPopupMenu menu = new JPopupMenu();
					switch (path.getPathCount()) {
					case SERVER:
						toolbarReference.setConnectionEnabled();
						
						//                                       //
						// Some actions are not implemented yet  // 
						//                                       //
						
						//menu.add(new JMenuItem(new RefreshAction(workAreaReference, treePanel)));
						menu.add(new JMenuItem(new CreateDataBaseAction(workAreaReference, treePanel)));
						menu.add(new JMenuItem(new ShowRetentionPoliciesAction(workAreaReference, treePanel)));
						//menu.add(new JMenuItem(new ShowUsersAction(workAreaReference, treePanel)));
						//menu.add(new JMenuItem(new ShowQueriesAction(workAreaReference, treePanel)));
						//menu.add(new JMenuItem(new ShowStaticsAction(workAreaReference, treePanel)));
						//menu.add(new JMenuItem(new DiagnosticsAction(workAreaReference, treePanel)));
						menu.add(new JMenuItem(new DisconnectAction(workAreaReference, treePanel)));
						break;
					case DATABASE:
						toolbarReference.setDatabaseEnabled();
						//menu.add(new JMenuItem(new RefreshAction(workAreaReference, treePanel)));
						menu.add(new JMenuItem(new NewQueryAction(workAreaReference, treePanel)));
						//menu.add(new JMenuItem(new ShowContinuousQueriesAction(workAreaReference, treePanel)));
						//menu.add(new JMenuItem(new RunBackFillAction(workAreaReference, treePanel)));
						menu.add(new JMenuItem(new DropDatabaseAction(workAreaReference, treePanel)));
						break;
					case MEASURAMENT:
						toolbarReference.setMeasuramentEnabled();
						menu.add(new JMenuItem(new NewQueryAction(workAreaReference, treePanel)));
						menu.add(new JMenuItem(new ShowTagKeysAction(workAreaReference, treePanel)));
						menu.add(new JMenuItem(new ShowTagValuesAction(workAreaReference, treePanel)));
						menu.add(new JMenuItem(new ShowFieldKeysAction(workAreaReference, treePanel)));
						menu.add(new JMenuItem(new ShowSeriesAction(workAreaReference, treePanel)));
						menu.add(new JMenuItem(new DropMeasuramentAction(workAreaReference, treePanel)));
						menu.add(new JMenuItem(new DropSeriesAction(workAreaReference, treePanel)));
						break;
					}
					menu.show(tree, pathBounds.x, pathBounds.y + pathBounds.height);
				}
			} else {
				TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
				Rectangle pathBounds = tree.getUI().getPathBounds(tree, path);
				if (pathBounds != null && pathBounds.contains(mouseEvent.getX(), mouseEvent.getY())) {
					switch (path.getPathCount()) {
					case SERVER:
						toolbarReference.setConnectionEnabled();
						break;
					case DATABASE:
						toolbarReference.setDatabaseEnabled();
						break;
					case MEASURAMENT:
						toolbarReference.setMeasuramentEnabled();
						break;
					}
				}
			}
		}
	}
}