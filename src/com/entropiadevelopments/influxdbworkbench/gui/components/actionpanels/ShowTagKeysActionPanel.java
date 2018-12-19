package com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels;

import java.awt.BorderLayout;
import java.util.List;

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

public class ShowTagKeysActionPanel extends ActionPanel {

	private static final long serialVersionUID = -7077134831254192379L;
	private InfluxDBConnection connection;
	private String mesurementName;
	private String databaseName;
	
	private JScrollPane mainPanel;
	private TagKeysTable tagsKeyTable;

	public ShowTagKeysActionPanel(InfluxDBConnection connection, String mesurementName, String databaseName) {
	
	super(connection.getConnectionName()+ "."+ mesurementName+".tagKeys", "TagKeys.png");
	
	this.connection = connection;
	this.mesurementName = mesurementName;
	this.databaseName = databaseName;
	
	setBorder(new EtchedBorder());
	setLayout(new BorderLayout());
	initComponets();
	runQuery();
	add(mainPanel);
	
	}
	private void initComponets() {
		tagsKeyTable = new TagKeysTable();
		mainPanel  = new JScrollPane(tagsKeyTable);
	
	}
	@Override
	public void runQuery() {
		tagsKeyTable.removeAllRows();
		InfluxDBFacility facility = InfluxDBFacility.getFacilityReference();
		InfluxDB influxDB = facility.getInfluxDB(connection);
		Query query = new Query("SHOW TAG KEYS FROM \""+mesurementName +"\" ", databaseName);
		QueryResult reuslt = influxDB.query(query);
		for (Result result : reuslt.getResults()) {
			if(reuslt != null) {
				for (Series series: result.getSeries()){
					 int num =1;
					 if (series!= null) {
						 for(List<Object> list : series.getValues()){
							 tagsKeyTable.addTableRecord(new TagKeyTableRecord(num,(String)list.get(0)));
							 num++;
						 }
					 }
				}
			}
		}
	}
	
	class TagKeyTableRecord implements TableRecord{
		private int num; 
		private String tagKey;
		
		public TagKeyTableRecord(int num, String tagKey) {
			this.num =  num;
			this.tagKey = tagKey;
		}
		@Override
		public Object getFieldAt(int fieldIndex) {
			if (fieldIndex==0) return num;
			if (fieldIndex==1) return tagKey;
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
