package com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBConnection;
import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBRetentionPolicy;
import com.entropiadevelopments.influxdbworkbench.facility.InfluxDBFacility;
import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedJPanel;
import com.entropiadevelopments.influxdbworkbench.gui.common.GuiToolkit;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;

import info.clearthought.layout.TableLayout;

public class RetentionPolicyPanel extends ActionPanel implements ActionListener, ListSelectionListener{

	private JComboBox<String> databaseComboBox;
	
	private JButton createPolicyButton;
	private JButton alterPolicyButton;
	private JButton dropPolicyButton;

	private RetentionPoliciesTable policiesTable;

	private EnhancedJPanel topPanel;
	private EnhancedJPanel bottomPanel;

	private InfluxDBConnection connection;
	private HashMap<String, Series> seriedHashMap;
	
	private InfluxDBRetentionPolicy  setectedRetentionPolicy;
	private InfluxDBTreePanel treePanelReference;

	public RetentionPolicyPanel(InfluxDBTreePanel treePanelReference, InfluxDBConnection connection) {
		super(connection.getConnectionName() + ".policies", "RetentionPolicy.png");
		this.connection = connection;
		this.treePanelReference = treePanelReference;
		seriedHashMap = new HashMap<String, Series>();
		setBorder(new EtchedBorder());
		setLayout(new BorderLayout());

		initComponets();
		layuotComponets();
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.CENTER);
		runQuery();
	}

	private void initComponets() {

		databaseComboBox = new JComboBox();
		databaseComboBox.addActionListener (this);
		createPolicyButton = new JButton("Create Policy");
		
		createPolicyButton.setIcon(GuiToolkit.getIcon("CreateRetentionPolicy.png"));
		createPolicyButton.addActionListener(this);
		
		alterPolicyButton = new JButton("AlterPpolicy");
		alterPolicyButton.setIcon(GuiToolkit.getIcon("EditRetentionPolicy.png"));
		alterPolicyButton.setEnabled(false);
		alterPolicyButton.addActionListener(this);
		
		dropPolicyButton = new JButton("Drop Policy");
		dropPolicyButton.setIcon(GuiToolkit.getIcon("DropRetentionPolicy.png"));
		dropPolicyButton.setEnabled(false);
		dropPolicyButton.addActionListener(this);

		policiesTable = new RetentionPoliciesTable();
		policiesTable.getSelectionModel().addListSelectionListener(this);

		topPanel = new EnhancedJPanel();
		bottomPanel = new EnhancedJPanel();

	}
	private void layuotComponets() {
		setLayout(new BorderLayout());
		layuotTopPanel();
		layuotBottomPanel();

	}

	private void layuotBottomPanel() {
		double size[][] = { { TableLayout.FILL }, { TableLayout.FILL } };

		bottomPanel.setLayout(new TableLayout(size));
		bottomPanel.add(new JScrollPane(policiesTable), "0,0");

	}

	private void layuotTopPanel() {
		double border = 5;
		double field = 120;
		double size[][] = { { 10, 100, border, 150, border, field, border, field, border, field, border },
				{ border, 30, border } };
		topPanel.setLayout(new TableLayout(size));

		topPanel.add(new JLabel("Select database:"), "1,1");
		topPanel.add(databaseComboBox, "3,1");
		topPanel.add(createPolicyButton, "5,1");
		topPanel.add(alterPolicyButton, "7,1");
		topPanel.add(dropPolicyButton, "9,1");

		topPanel.setBorder(new EtchedBorder());

	}
	private void putSerieInTable(Series serie) {
		policiesTable.removeAllRows();
		if (serie != null) {
			for (Object valuesList : serie.getValues()) {
				ArrayList<Object> rowValues = (ArrayList<Object>) valuesList;
				InfluxDBRetentionPolicy retentionPolicy = new InfluxDBRetentionPolicy(connection, rowValues);
				policiesTable.addTableRecord(retentionPolicy);
			}
		}
		
	}
	@Override
	public void runQuery() {
		InfluxDBFacility facility = InfluxDBFacility.getFacilityReference();
		// databaseComboBox.removeAll();
		seriedHashMap.clear();
		InfluxDB influxDB = facility.getInfluxDB(connection);
		for (String databaseName : influxDB.describeDatabases()) {
			databaseComboBox.addItem(databaseName);
			Query query = new Query("SHOW RETENTION POLICIES", databaseName);
			QueryResult queryResult = influxDB.query(query);
			for (Result result : queryResult.getResults()) {
				for (Series serie : result.getSeries()) {
					seriedHashMap.put(databaseName, serie);
				}
			}
		}
		Series serie = seriedHashMap.get(databaseComboBox.getSelectedItem());
		putSerieInTable(serie);
	}
	@Override
	public void actionPerformed (ActionEvent actionEvent) {
		if(actionEvent.getSource().equals(databaseComboBox)) {
			Series serie = seriedHashMap.get(databaseComboBox.getSelectedItem());
			putSerieInTable(serie);
		}
		if(actionEvent.getSource().equals(createPolicyButton)){
			JFrame rootFrame = (JFrame) SwingUtilities.getRoot(treePanelReference);
			CreatePolicyDialog createPolicyDialog = new CreatePolicyDialog(rootFrame, connection, this);
			
		}
		if(actionEvent.getSource().equals(alterPolicyButton)){
			JFrame rootFrame = (JFrame) SwingUtilities.getRoot(treePanelReference);
			if (setectedRetentionPolicy != null) {
				EditPolicyDialog editPolicyDialog = new EditPolicyDialog(rootFrame, connection, this, setectedRetentionPolicy);
			}
			
		}
		if(actionEvent.getSource().equals(dropPolicyButton)){
			
			JFrame rootFrame = (JFrame) SwingUtilities.getRoot(treePanelReference);
			int response = JOptionPane.showConfirmDialog(rootFrame, "Do you want drop retention policy?", "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.NO_OPTION) {
				return;
			} else if (response == JOptionPane.YES_OPTION) {
				InfluxDBFacility facility = InfluxDBFacility.getFacilityReference();
				InfluxDB influxDB = facility.getInfluxDB(connection);
				influxDB.dropRetentionPolicy(setectedRetentionPolicy.getPolicyName(),(String) databaseComboBox.getSelectedItem());
				policiesTable.removeSelectedRows();
			} else if (response == JOptionPane.CLOSED_OPTION) {
				return;
			}	
		}
	}
	
	@Override
    public void valueChanged(ListSelectionEvent event) {
    	setectedRetentionPolicy =   (InfluxDBRetentionPolicy) policiesTable.getSelectedRowRecord();
    	alterPolicyButton.setEnabled(true);
    	dropPolicyButton.setEnabled(true);
    }

	public void addRetentionPolicy(InfluxDBRetentionPolicy retentionPolicy) {
		if (retentionPolicy != null) {
			InfluxDBFacility facility = InfluxDBFacility.getFacilityReference();
			InfluxDB influxDB = facility.getInfluxDB(connection);
			influxDB.createRetentionPolicy(retentionPolicy.getPolicyName(),(String)databaseComboBox.getSelectedItem(), retentionPolicy.getDuration(), (int) retentionPolicy.getReplication(), retentionPolicy.isDefault());
			policiesTable.addTableRecord(retentionPolicy);
		}
	}

	public void editRetentionPolicy(InfluxDBRetentionPolicy retentionPolicy) {
		InfluxDBFacility facility = InfluxDBFacility.getFacilityReference();
		InfluxDB influxDB = facility.getInfluxDB(connection);
		influxDB.dropRetentionPolicy(setectedRetentionPolicy.getPolicyName(), (String)databaseComboBox.getSelectedItem());
		influxDB.createRetentionPolicy(retentionPolicy.getPolicyName(),(String)databaseComboBox.getSelectedItem(), retentionPolicy.getDuration(), (int) retentionPolicy.getReplication(), retentionPolicy.isDefault());
		policiesTable.removeSelectedRows();
		policiesTable.addTableRecord(retentionPolicy);
	}
}
