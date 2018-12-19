package com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Series;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBConnection;
import com.entropiadevelopments.influxdbworkbench.facility.InfluxDBFacility;
import com.entropiadevelopments.influxdbworkbench.gui.common.ButtonPanel;
import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedJPanel;
import com.entropiadevelopments.influxdbworkbench.gui.common.GuiToolkit;

import org.influxdb.dto.QueryResult.Result;

import info.clearthought.layout.TableLayout;

public class NewQueryActionPanel extends ActionPanel implements ActionListener {

	private static final long serialVersionUID = -624272778516469677L;
	private InfluxDBFacility influxDBFacility;
	private JSplitPane mainPanel;
	private JTextArea textArea;
	private JLabel messagelabel;
	private ButtonPanel buttonPanel;
	private JButton runQueryButton;
	private JButton clearButton;
	private JTabbedPane bottomPanel;
	private long responseTime;

	private InfluxDBConnection connection;
	private String mesurementName;
	private String databaseName;

	public NewQueryActionPanel(InfluxDBConnection connection, String mesurementName, String databaseName) {

		super(connection.getConnectionName() + "." + mesurementName, "NewQuery.png");

		this.connection = connection;
		this.mesurementName = mesurementName;
		this.databaseName = databaseName;

		influxDBFacility = InfluxDBFacility.getFacilityReference();
		setBorder(new EtchedBorder());
		setLayout(new BorderLayout());
		initComponets();
		layuotComponets();
		add(mainPanel);
	}

	private void initComponets() {

		mainPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainPanel.setDividerSize(5);
		textArea = new JTextArea(" SELECT * FROM \"" + mesurementName + "\" WHERE time > now() -5m");
		textArea.setPreferredSize(new Dimension(600, 600));
		messagelabel = new JLabel();
		buttonPanel = new ButtonPanel(30, 5, 2, 5);
		clearButton = new JButton("Clear results");
		clearButton.setIcon(GuiToolkit.getIcon("DropMeasurement.png"));
		clearButton.addActionListener(this);
		runQueryButton = new JButton("Run Query");
		runQueryButton.setIcon(GuiToolkit.getIcon("RunQuery.png"));
		runQueryButton.addActionListener(this);
		buttonPanel.addButton(runQueryButton);
		buttonPanel.addButton(clearButton);
		bottomPanel = new JTabbedPane();
	}

	private void layuotComponets() {
		setLayout(new BorderLayout());
		layuotTopPanel();
		layuotBottomPanel();
	}

	private void layuotTopPanel() {
		EnhancedJPanel topPanel = new EnhancedJPanel();
		double size[][] = { { TableLayout.FILL }, { TableLayout.FILL, 30, 30 } };
		topPanel.setLayout(new TableLayout(size));
		JScrollPane scrollPane = createNewScrollPaneWithEditor(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		topPanel.add(scrollPane, "0,0");
		topPanel.add(buttonPanel, "0,1");
		topPanel.add(messagelabel, "0,2");
		topPanel.setPreferredSize(new Dimension(0, 150));
		mainPanel.setTopComponent(topPanel);
	}

	private JScrollPane createNewScrollPaneWithEditor(JTextArea textArea) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getViewport().add(textArea);
		scrollPane.getViewport().setPreferredSize(textArea.getPreferredSize());
		return scrollPane;
	}

	private void layuotBottomPanel() {
		bottomPanel.setTabPlacement(JTabbedPane.TOP);
		bottomPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		mainPanel.setBottomComponent(bottomPanel);
	}

	private void putResulQueryTabedPanels(QueryResult queryResult) {
		for (Result result : queryResult.getResults()) {
			putResulqueryTab(result);
		}
	}

	private void putResulqueryTab(Result result) {
		if (!result.hasError()) {
			int seriesCounter = 1;
			int totalNumOfRows = 0;
			if (result.getSeries() != null) {
				for (Series serie : result.getSeries()) {
					QueryResultTable queryResultTable = new QueryResultTable(serie);
					bottomPanel.addTab("Result " + seriesCounter, new JScrollPane(queryResultTable));
					seriesCounter++;
					totalNumOfRows = totalNumOfRows + queryResultTable.getNumOfRows();
				}
				messagelabel.setText("   Results " + totalNumOfRows + ", response time : " + responseTime + " ms");
			}
		}
	}

	public QueryResult executeQuery() {
		String query = textArea.getText();
		QueryResult queryResult = influxDBFacility.query(query,databaseName,connection);
		if (queryResult.hasError()) {
			return null;
		} else {
			return queryResult;
		}
	}

	@Override
	public void runQuery(){
		long timeInit = System.currentTimeMillis();
		QueryResult queryResult = executeQuery();
		long timeEnd = System.currentTimeMillis();
		responseTime = (timeEnd - timeInit);
		bottomPanel.removeAll();
		putResulQueryTabedPanels(queryResult);

	}
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource().equals(runQueryButton)) {
			runQuery();
		}
		if (actionEvent.getSource().equals(clearButton)) {
			bottomPanel.removeAll();
			messagelabel.setText("");
		}		
	}
}