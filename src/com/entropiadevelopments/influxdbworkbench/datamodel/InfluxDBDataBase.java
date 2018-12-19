package com.entropiadevelopments.influxdbworkbench.datamodel;

import java.util.ArrayList;

public class InfluxDBDataBase extends InfluxDBTreeElement{
	private String databaseName;
	private ArrayList<InfluxDBMeasuramet> measuraments;
	private InfluxDBConnection connection;
	
	public InfluxDBDataBase(String name,InfluxDBConnection connection ) {
		super(name);
		databaseName = name;
		measuraments = new  ArrayList<InfluxDBMeasuramet> ();
		this.connection = connection;
	}
	public InfluxDBDataBase(String name, ArrayList<InfluxDBMeasuramet> measuraments , InfluxDBConnection connection) {
		super(name);
		this.measuraments = measuraments;
		this.connection = connection;
	}
	public ArrayList<InfluxDBMeasuramet> getMeasuraments() {
		return measuraments;
	}
	public void addMeasurament(InfluxDBMeasuramet influxDBMeasuramet) {
		measuraments.add(influxDBMeasuramet);
	}

	public void setMeasuraments(ArrayList<InfluxDBMeasuramet> measuraments) {
		this.measuraments = measuraments;
	}
	@Override
	public int getNodeType() {

		return DATABASE;
	}
	public String toString() {
		return databaseName;
	}
	@Override
	public InfluxDBConnection getConnection() {
		return connection;
	}
}