package com.entropiadevelopments.influxdbworkbench.gui.components.actions;

import java.awt.event.ActionEvent;

import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBWorkArea;

public class Time12hFormatAction extends BasicAbstractAction{
    

	private static final long serialVersionUID = 5467740924384849511L;
    public Time12hFormatAction(InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treePanelReference){
    	super("2:30:45 PM 12 Hour", null, null,workAreaReference, treePanelReference);
    }
    
    public void actionPerformed(ActionEvent event) {
    }
}