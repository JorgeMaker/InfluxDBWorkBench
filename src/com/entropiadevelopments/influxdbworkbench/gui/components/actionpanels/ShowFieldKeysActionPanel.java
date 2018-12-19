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
import com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels.ShowTagKeysActionPanel.TagKeyTableRecord;

public class ShowFieldKeysActionPanel extends ActionPanel {

	static final long serialVersionUID = -1207305966403205210L;
	private InfluxDBConnection connection;
	private String mesurementName;
	private String databaseName;

	private JScrollPane mainPanel;
	private KeyFieldsTable keyFieldsTable;

	public ShowFieldKeysActionPanel(InfluxDBConnection connection, String mesurementName, String databaseName) {

		super(connection.getConnectionName() + "." + mesurementName + ".fieldKeys", "FieldKeys.png");

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
		keyFieldsTable = new KeyFieldsTable();
		mainPanel = new JScrollPane(keyFieldsTable);
	}

	@Override
	public void runQuery() {
		keyFieldsTable.removeAllRows();
		InfluxDBFacility facility = InfluxDBFacility.getFacilityReference();
		InfluxDB influxDB = facility.getInfluxDB(connection);
		Query query = new Query("SHOW FIELD KEYS FROM \"" + mesurementName + "\"", databaseName);
		QueryResult reuslt = influxDB.query(query);
		for (Result result : reuslt.getResults()) {
			for (Series series : result.getSeries()) {
				int num = 1;
				for (List<Object> list : series.getValues()) {
					keyFieldsTable.addTableRecord(new FieldKeysTableRecord(num, (String)list.get(0), (String)list.get(1)));
					num++;
				}
			}
		}
	}

	class FieldKeysTableRecord implements TableRecord {
		private int num;
		private String fieldKey;
		private String fieldType;

		public FieldKeysTableRecord(int num, String fieldKey, String fieldType) {
			this.num = num;
			this.fieldKey = fieldKey;
			this.fieldType = fieldType;
		}

		@Override
		public Object getFieldAt(int fieldIndex) {
			if (fieldIndex == 0)
				return num;
			if (fieldIndex == 1)
				return fieldKey;
			if (fieldIndex == 2)
				return fieldType;
			return null;
		}

		@Override
		public int getNumberOfField() {
			return 3;
		}

		@Override
		public void setFieldAt(int fieldIndex, Object field) {

		}
	}

}
