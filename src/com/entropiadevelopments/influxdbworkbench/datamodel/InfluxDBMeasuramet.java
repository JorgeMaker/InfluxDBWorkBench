package com.entropiadevelopments.influxdbworkbench.datamodel;

public class InfluxDBMeasuramet extends InfluxDBTreeElement{
	
	private String measuramentName;
	private InfluxDBConnection connection;
	private String database;
	
	public InfluxDBMeasuramet(String name, String database, InfluxDBConnection connection) {
		super(name);
		measuramentName = name;
		this.connection = connection;
		this.database = database;
	}

	@Override
	public int getNodeType() {
		return MEASURAMENT;
	}
	@Override
	public String toString() {
		return measuramentName;
	}

	@Override
	public InfluxDBConnection getConnection() {
		return connection;
	}
	public String getDatabase() {
		return database;
	}
}