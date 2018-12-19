package com.entropiadevelopments.influxdbworkbench.gui.components.actions;

import java.awt.event.ActionEvent;

import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBWorkArea;

public class ShowQueriesAction extends BasicAbstractAction{
    

	private static final long serialVersionUID = 5467740924384849511L;
    public ShowQueriesAction(InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treePanelReference){
    	super("Show Queries", "ShowQueries.png", "Show Queries",workAreaReference, treePanelReference);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
    	
    }
}