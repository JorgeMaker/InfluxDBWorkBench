package com.entropiadevelopments.influxdbworkbench.gui.components.actions;

import java.awt.event.ActionEvent;

import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBWorkArea;

public class RunBackFillAction extends BasicAbstractAction{
    

	private static final long serialVersionUID = 5467740924384849511L;
	
	
    public RunBackFillAction(InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treePanelReference){
    	super("Run Back Fill", "BackFill.png", "Run Back Fill", workAreaReference, treePanelReference);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
    }
}