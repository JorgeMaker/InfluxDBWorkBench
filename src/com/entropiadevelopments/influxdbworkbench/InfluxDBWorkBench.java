package com.entropiadevelopments.influxdbworkbench;

import com.entropiadevelopments.influxdbworkbench.gui.InfluxDBWorkBenchGUI;

public class InfluxDBWorkBench {
	
	private InfluxDBWorkBenchGUI influxDBWorkBenchGUI;

	public InfluxDBWorkBench() {
		
		influxDBWorkBenchGUI = new InfluxDBWorkBenchGUI();
	}
    public static void main(String[] args) {
    	InfluxDBWorkBench influxDBWorkBench = new InfluxDBWorkBench();
	}
}