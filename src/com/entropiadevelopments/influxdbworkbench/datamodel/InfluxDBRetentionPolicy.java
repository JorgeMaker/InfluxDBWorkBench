package com.entropiadevelopments.influxdbworkbench.datamodel;

import java.util.ArrayList;

import com.entropiadevelopments.influxdbworkbench.gui.common.TableRecord;

public class InfluxDBRetentionPolicy implements TableRecord{
	
	private String policyName;
	private String duration;
	private String shardGroup;
	private double replication;
	private boolean isDefault;
	private InfluxDBConnection connection;
	private String dataBase;
	
	public InfluxDBRetentionPolicy(InfluxDBConnection connection) {
		this.connection = connection;
	}
	
	public InfluxDBRetentionPolicy(InfluxDBConnection connection, ArrayList<Object> data) {
		this.connection = connection;
		policyName = (String)data.get(0);
		duration = (String)data.get(1);
		shardGroup = (String)data.get(2);
		replication = (double)data.get(3);
		isDefault = (boolean)data.get(4);
	}

	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getShardGroup() {
		return shardGroup;
	}
	public void setShardGroup(String shardGroup) {
		this.shardGroup = shardGroup;
	}
	public double getReplication() {
		return replication;
	}
	public boolean getISDefault() {
		return isDefault;
	}
	
	public void setReplication(int replication) {
		this.replication = replication;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public InfluxDBConnection getConnection() {
		return connection;
	}
	public void setConnection(InfluxDBConnection connection) {
		this.connection = connection;
	}
	@Override
	public Object getFieldAt(int fieldIndex) {
		switch(fieldIndex) {
		case 0:
			return policyName;
		case 1:
			return duration;
		case 2:
			return shardGroup;
		case 3:
			return replication;
		case 4:
			return isDefault;
		}
				return null;
	}
	@Override
	public int getNumberOfField() {
		return 5;
	}
	@Override
	public void setFieldAt(int fieldIndex, Object field) {		
	}
}
