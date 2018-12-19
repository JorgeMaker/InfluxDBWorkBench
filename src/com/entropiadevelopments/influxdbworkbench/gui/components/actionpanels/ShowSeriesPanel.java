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

public class ShowSeriesPanel extends ActionPanel {

	private static final long serialVersionUID = 1L;
	private InfluxDBConnection connection;
	private String mesurementName;
	private String databaseName;

	private JScrollPane mainPanel;
	private SeriesTable seriesTable;

	public ShowSeriesPanel(InfluxDBConnection connection, String mesurementName, String databaseName) {

		super(connection.getConnectionName() + "." + mesurementName + ".series", "Series.png");

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
		seriesTable = new SeriesTable();
		mainPanel = new JScrollPane(seriesTable);
	}

	@Override
	public void runQuery() {
		seriesTable.removeAllRows();
		InfluxDBFacility facility = InfluxDBFacility.getFacilityReference();
		InfluxDB influxDB = facility.getInfluxDB(connection);
		Query query = new Query("SHOW SERIES FROM \"" + mesurementName + "\"", databaseName);
		QueryResult reuslt = influxDB.query(query);
		for (Result result : reuslt.getResults()) {
			for (Series series : result.getSeries()) {
				int num = 1;
				for (List<Object> list : series.getValues()) {
					seriesTable.addTableRecord(new SerieRecord(num, getHost((String) list.get(0))));
					num++;
				}
			}
		}
	}

	private String getHost(String result){
		String[] parts = result.split(",");
		for (int i = 0; i < parts.length; i++){
			if (parts[i].contains("host")) {
				String[] values = parts[i].split("=");
				return values[1];
			}
		}
		return null;
	}

	class SerieRecord implements TableRecord {
		private int num;
		private String host;

		public SerieRecord(int num, String host) {
			this.num = num;
			this.host = host;

		}
		@Override
		public Object getFieldAt(int fieldIndex) {
			if (fieldIndex == 0)
				return num;
			if (fieldIndex == 1)
				return host;
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
