package com.entropiadevelopments.influxdbworkbench.datamodel;

import org.json.simple.JSONObject;

import com.entropiadevelopments.influxdbworkbench.gui.common.TableRecord;

public class InfluxDBConnection extends InfluxDBTreeElement implements TableRecord {

	private String connectionName;
	private String ipAddresss;
	private long port;
	private String database;
	private String userName;
	private String password;
	private boolean useSLL;
	

	public InfluxDBConnection(JSONObject jsonConnection) {
		super(null);
		connectionName = (String) jsonConnection.get("connectionName");
		ipAddresss = (String) jsonConnection.get("iIPAddress");
		port = (long) jsonConnection.get("port");
		database = (String) jsonConnection.get("database");
		userName = (String) jsonConnection.get("userName");
		password = (String) jsonConnection.get("password");
		useSLL = (boolean) jsonConnection.get("useSLL");
		id = (String) jsonConnection.get("id");
	}
	public InfluxDBConnection() {
		super(null);
		
	}

	public void setConnectionName(String connName) {
		connectionName = connName;
	}

	public String getConnectionName() {
		return connectionName;
	}

	public InfluxDBConnection(String name) {
		super(name);
	}

	public String getIpAddresss() {
		return ipAddresss;
	}

	public void setIpAddresss(String ipAddresss) {
		this.ipAddresss = ipAddresss;
	}

	public long getPort() {
		return port;
	}

	public void setPort(int potrt) {
		this.port = potrt;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isUseSLL() {
		return useSLL;
	}

	public void setUseSLL(boolean useSLL) {
		this.useSLL = useSLL;
	}

	@Override
	public int getNodeType() {
		// TODO Auto-generated method stub
		return SERVER;
	}

	public JSONObject toJSon() {
		JSONObject connection = new JSONObject();
		connection.put("connectionName", connectionName);
		connection.put("iIPAddress", ipAddresss);
		connection.put("port", port);
		connection.put("database", database);
		connection.put("userName", userName);
		connection.put("password", password);
		connection.put("useSLL", useSLL);
		connection.put("id", id);
	
		return connection;
	}

	@Override
	public Object getFieldAt(int fieldIndex) {
		switch (fieldIndex) {
		case 0:
			return connectionName;
		case 1:
			return ipAddresss + " : " + port;
		}
		return null;
	}
	@Override
	public int getNumberOfField() {
		return 2;
	}
	@Override
	public void setFieldAt(int fieldIndex, Object field) {
	}
	@Override
	public String toString() {
		return connectionName;
	}
	@Override
	public InfluxDBConnection getConnection() {
		return this;
	}

}
