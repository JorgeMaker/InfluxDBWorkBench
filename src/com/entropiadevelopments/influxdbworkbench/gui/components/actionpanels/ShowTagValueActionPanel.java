package com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBConnection;
import com.entropiadevelopments.influxdbworkbench.facility.InfluxDBFacility;
import com.entropiadevelopments.influxdbworkbench.gui.common.TableRecord;

import info.clearthought.layout.TableLayout;

public class ShowTagValueActionPanel extends ActionPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private InfluxDBConnection connection;
	private String mesurementName;
	private String databaseName;
	private boolean layedOut;
	
	private JScrollPane tablePanel;
	private TagKeysValuesTable tagKeysValuesTable;
	private JPanel topPanel;
	private JComboBox<String> keyCombo;
	private HashMap<String,ArrayList<TableRecord>> reultsHashMap;
	
	
	public ShowTagValueActionPanel(InfluxDBConnection connection, String mesurementName, String databaseName) {
	
	super(connection.getConnectionName () + "."+ mesurementName +".tagValues", "TagValues.png");
	
	this.connection = connection;
	this.mesurementName = mesurementName;
	this.databaseName = databaseName;
	reultsHashMap = new HashMap<String,ArrayList<TableRecord>>();
	layedOut = false;
	
	
	setBorder(new EtchedBorder());
	setLayout(new BorderLayout());
	
	initComponets();
	layoutConponents();
	runQuery();
	
	setLayout(new BorderLayout());
	
	add(topPanel, BorderLayout.NORTH);
	add(tablePanel, BorderLayout.CENTER);
	layedOut = true;
	}
	private void layoutConponents() {
		
		topPanel = new JPanel();
		double border = 1;
		double size[][] = { { border, 80, border, 100 ,TableLayout.FILL }, 
							{ border, 30, border } };
		topPanel.setLayout(new TableLayout(size));
		topPanel.add( new JLabel("Tag Keys: ") ,"1,1");
		topPanel.add( keyCombo ,"3,1");
		
		
	}
	private void initComponets() {
		keyCombo = new JComboBox<String> ();
		
		keyCombo.addActionListener(this);
		tagKeysValuesTable = new TagKeysValuesTable();
		
		
		tablePanel  = new JScrollPane(tagKeysValuesTable);
	}
	
	@Override
	public void runQuery(){
		tagKeysValuesTable.removeAllRows();
		keyCombo.removeAllItems(); 
		ArrayList<String>  tagKeyList = getAllKeys();
		for (String key: tagKeyList){
			ArrayList<TableRecord> values = new ArrayList<TableRecord>();
			InfluxDBFacility facility = InfluxDBFacility.getFacilityReference();
			InfluxDB influxDB = facility.getInfluxDB(connection);
			Query query = new Query("SHOW TAG VALUES FROM \""+mesurementName +"\" WITH KEY = \""+key+ "\"", databaseName);
			QueryResult reuslt = influxDB.query(query);
			for (Result result : reuslt.getResults()){
				for (Series series: result.getSeries()){
					 int num =1;
					 for(List<Object> list : series.getValues()){
						 values.add(new TagKeyValuesRecord(num,(String)list.get(1)));
						 num++;
					 }
				}
			}
			reultsHashMap.put(key, values);
		}
		for (TableRecord record: reultsHashMap.get(keyCombo.getSelectedItem())){
			tagKeysValuesTable.addTableRecord(record);
		}
	}
	
	private ArrayList<String> getAllKeys(){
		ArrayList<String>  tagList = new ArrayList<String>();
		InfluxDBFacility facility = InfluxDBFacility.getFacilityReference();
		InfluxDB influxDB = facility.getInfluxDB(connection);
		Query query = new Query("SHOW TAG KEYS FROM \""+mesurementName +"\" ", databaseName);
		QueryResult reuslt = influxDB.query(query);
		
		for (Result result : reuslt.getResults()) {
			for (Series series: result.getSeries()){
				 for(List<Object> list : series.getValues()){
					 tagList.add((String)list.get(0));
					 keyCombo.addItem((String)list.get(0));
				 }
			}
		}
		return tagList;
	}	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if(layedOut){
			if(actionEvent.getSource().equals(keyCombo)){
				tagKeysValuesTable.removeAllRows();
				ArrayList<TableRecord> records = reultsHashMap.get(keyCombo.getSelectedItem());
				if (records != null) {
					for(TableRecord record : records) {
						tagKeysValuesTable.addTableRecord(record);
					}
				} 
			}
		}
	}
	class TagKeyValuesRecord implements TableRecord{
		private int num; 
		private String tagKey;
		
		public TagKeyValuesRecord(int num, String tagKey) {
			this.num =  num;
			this.tagKey = tagKey;
		}
		@Override
		public Object getFieldAt(int fieldIndex) {
			if (fieldIndex == 0) return num;
			if (fieldIndex == 1) return tagKey;
			return null;
		}

		@Override
		public int getNumberOfField() {
			return 2;
		}

		@Override
		public void setFieldAt(int fieldIndex, Object field) {
		}
	}


}
