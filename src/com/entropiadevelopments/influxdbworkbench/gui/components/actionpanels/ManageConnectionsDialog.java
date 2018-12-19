package com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBConnection;
import com.entropiadevelopments.influxdbworkbench.facility.InfluxDBFacility;
import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedDialog;
import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedJPanel;
import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedTableModel;
import com.entropiadevelopments.influxdbworkbench.gui.common.GuiToolkit;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;

import info.clearthought.layout.TableLayout;

public class ManageConnectionsDialog extends EnhancedDialog implements ActionListener {

	private static final long serialVersionUID = -6349599499459674834L;
	private JButton createButton;
	private JButton editButton;
	private JButton removeButton;
	private ConnectionsTable connectionsTable;
	private InfluxDBTreePanel treepanelReference;

	public ManageConnectionsDialog(JFrame parent, InfluxDBTreePanel treepanel) {
		super(parent, "Manage InfluxDB Connections", true, "Connect", "Cancel");

		treepanelReference = treepanel;
		
		createButton = new JButton("Create");
		createButton.setIcon(GuiToolkit.getIcon("CreateConnection.png"));
		createButton.addActionListener(this);

		editButton = new JButton("Edit");
		editButton.setIcon(GuiToolkit.getIcon("EditConnection.png"));
		editButton.addActionListener(this);

		removeButton = new JButton("Remove");
		removeButton.setIcon(GuiToolkit.getIcon("Disconnect.png"));
		removeButton.addActionListener(this);

		EnhancedJPanel buttonsPanel = layoutTopButtonsPanel();
		connectionsTable = new ConnectionsTable();
		

		JSONArray array = loadConnectionsFile();
		for (Object connObj : array) {
			connectionsTable.addTableRecord(new InfluxDBConnection((JSONObject) connObj));

		}
		setLayoutToInternalPanel(new BorderLayout());
		addComponentToInternalPanel(buttonsPanel, BorderLayout.NORTH);
		addComponentToInternalPanel(new JScrollPane(connectionsTable), BorderLayout.CENTER);
		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {

		if (event.getSource().equals(createButton)) {
			ConnectionSettingsDialog ConnectionSettingsDialog = new ConnectionSettingsDialog(this, null);
		}
		if (event.getSource().equals(removeButton)) {
			int selectedRowIndex = connectionsTable.getSelectedRow();
			if (selectedRowIndex >= 0) {
				EnhancedTableModel model = (EnhancedTableModel) connectionsTable.getModel();

				InfluxDBConnection conection = (InfluxDBConnection) model.getValueAtRow(selectedRowIndex);
				JSONArray connectionsArray = loadConnectionsFile();
				connectionsArray.remove(conection.toJSon());
				saveArrayConnections(connectionsArray);
				model.removeRow(selectedRowIndex);
				connectionsTable.repaint();
			}
		}
		if (event.getSource().equals(editButton)) {
			int selectedRowIndex = connectionsTable.getSelectedRow();
			if (selectedRowIndex >= 0) {
				EnhancedTableModel model = (EnhancedTableModel) connectionsTable.getModel();
				InfluxDBConnection conection = (InfluxDBConnection) model.getValueAtRow(selectedRowIndex);
				ConnectionSettingsDialog ConnectionSettingsDialog = new ConnectionSettingsDialog(this, conection);
			}
		}
		super.actionPerformed(event);

	}

	public JSONArray loadConnectionsFile() {
		Path path = FileSystems.getDefault().getPath("connections.json").toAbsolutePath();
		try {
			JSONParser parser = new JSONParser();
			JSONArray array = (JSONArray) parser.parse(new FileReader(path.toString()));
			return array;
		} catch (ParseException e) {
			return new JSONArray();

		} catch (Exception e) {
			//JOptionPane.showMessageDialog(this, "Not possible to read connections file ", "Failure",JOptionPane.ERROR_MESSAGE);
		}
		return new JSONArray();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(350, 200);
	}

	private EnhancedJPanel layoutTopButtonsPanel() {

		EnhancedJPanel buttonsPanel = new EnhancedJPanel();
		double border = 1;
		double size[][] = { { border, 100, border, 100, border, 100, border }, { 25, border } };

		buttonsPanel.setLayout(new TableLayout(size));
		buttonsPanel.add(createButton, "1,0");
		buttonsPanel.add(editButton, "3,0");
		buttonsPanel.add(removeButton, "5,0");

		buttonsPanel.setBorder(new EtchedBorder());

		return buttonsPanel;

	}

	public void addConnection(InfluxDBConnection influxDBConnection) {

		JSONArray array = loadConnectionsFile();
		if (isANewConnection(influxDBConnection,array) == null) {
			array.add(influxDBConnection.toJSon());
			saveArrayConnections(array);
			connectionsTable.addTableRecord(influxDBConnection);
		}
		else {
			EnhancedTableModel model = (EnhancedTableModel) connectionsTable.getModel();
			JSONArray connectionsArray = loadConnectionsFile();
			InfluxDBConnection editedConnection = isANewConnection(influxDBConnection,array);
			connectionsArray.remove(editedConnection.toJSon());
			connectionsArray.add(influxDBConnection.toJSon());
			saveArrayConnections(connectionsArray);
			Vector<Object> rows =model.getAllRowsVector();
			Vector<Object> newRows = new Vector<Object>();
			for (Object obj: rows) {
				InfluxDBConnection conn = (InfluxDBConnection) obj;
				if (conn.getID().equals(influxDBConnection.getID())) {
				}
				else {
					newRows.add(obj);
				}
			}
			model.addAllRows(newRows);
			connectionsTable.repaint();
	    	model.addTableRecord(influxDBConnection);
	    	connectionsTable.repaint();
		}

	}
	private InfluxDBConnection isANewConnection(InfluxDBConnection influxDBConnection,JSONArray array ) {
		
		for (Object object: array) {
			InfluxDBConnection storedConnection = new InfluxDBConnection((JSONObject) object);
			if (influxDBConnection.getID().equals(storedConnection.getID())) {
				return storedConnection;
			}
		}
		return null;
	}
	
	private void saveArrayConnections(JSONArray array) {
		try {
			Path path = FileSystems.getDefault().getPath("connections.json").toAbsolutePath();
			FileWriter fileWriter = new FileWriter(path.toString());
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(array.toJSONString());
			printWriter.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Not possible to save connection ", "Failure",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	protected void okButtonPressedAction() {
		int selectedRowIndex = connectionsTable.getSelectedRow();
		if (selectedRowIndex >= 0) {
			InfluxDBConnection influxDBConnection = (InfluxDBConnection) connectionsTable.getAllRowsVector()
					.get(selectedRowIndex);
			InfluxDBFacility facility = InfluxDBFacility.getFacilityReference();
			facility.getInfluxDB(influxDBConnection);
			treepanelReference.addInfluxDBServer(influxDBConnection);
			dispose();
		}
	}

	protected void cancelButtonPressedAction() {
		dispose();
	}
}
