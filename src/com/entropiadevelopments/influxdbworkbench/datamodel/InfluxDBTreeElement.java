package com.entropiadevelopments.influxdbworkbench.datamodel;

public abstract class  InfluxDBTreeElement {
	
	public static final int SERVER = 1;
	public static final int DATABASE = 2;
	public static final int MEASURAMENT = 3;
	
	protected String id;
	
	public InfluxDBTreeElement(String id) {
		setName(id);
	}

	public String getID() {
		return id;
	}

	public void setName(String id) {
		this.id = id;
	}

	public abstract int getNodeType();
	public abstract InfluxDBConnection getConnection();
}
