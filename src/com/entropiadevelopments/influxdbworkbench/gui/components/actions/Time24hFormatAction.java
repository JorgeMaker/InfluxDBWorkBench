package com.entropiadevelopments.influxdbworkbench.gui.components.actions;


import java.awt.event.ActionEvent;

import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBWorkArea;

public class Time24hFormatAction extends BasicAbstractAction{
    

	private static final long serialVersionUID = 5467740924384849511L;
    public Time24hFormatAction(InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treePanelReference){
    	super("14:30:45 24 Hour", null, null,workAreaReference, treePanelReference);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
    }
}