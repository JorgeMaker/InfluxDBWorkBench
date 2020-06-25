package com.entropiadevelopments.influxdbworkbench.facility;

import java.util.HashMap;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBConnection;

public class InfluxDBFacility {

	private static InfluxDBFacility facility;
	private HashMap<InfluxDBConnection, InfluxDB> connectionsRepository;
	
	private InfluxDBFacility() {
		connectionsRepository = new HashMap <InfluxDBConnection, InfluxDB> ();		
	}
	public InfluxDB getInfluxDB(InfluxDBConnection connection) {
		if (connection != null) {
			String connectionString = "http://"+connection.getIpAddresss()+":"+connection.getPort();
		
			InfluxDB influxDB = connectionsRepository.get(connection);
			if (influxDB != null){
				return influxDB;
			}
			else {
				if( (connection.getUserName() == null || connection.getUserName().trim().equalsIgnoreCase("")) &&
						(connection.getPassword() == null || connection.getPassword().trim().equalsIgnoreCase(""))){
					influxDB = InfluxDBFactory.connect(connectionString);
				}
				else {
					influxDB = InfluxDBFactory.connect(connectionString,connection.getUserName(),connection.getPassword());
				}
				if(connection.getDatabase() != null) {
					influxDB.setDatabase(connection.getDatabase());
				}
				connectionsRepository.put(connection, influxDB);
				return influxDB;
			}
		}
		else return null;
	}
	public void closeAndRemoveConnection(InfluxDBConnection connection) {
		InfluxDB influxDB  =connectionsRepository.get(connection);
		influxDB.close();
		connectionsRepository.remove(connection);
	}

	public Pong pingConnection(InfluxDBConnection connection) {
		InfluxDB influxDB = getInfluxDB(connection);
		return influxDB.ping();
	}

	public QueryResult query(String queryString , String databaseName, InfluxDBConnection connection){
		Query query = new Query(queryString, databaseName);
		InfluxDB influxDB = getInfluxDB(connection);
		return influxDB.query(query);
	}
    public static InfluxDBFacility getFacilityReference(){
        if(facility == null){
        	facility = new InfluxDBFacility();
        }
        return facility;
    }

}
