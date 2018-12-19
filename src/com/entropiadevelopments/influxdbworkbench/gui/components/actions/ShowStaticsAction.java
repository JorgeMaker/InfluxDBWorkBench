package com.entropiadevelopments.influxdbworkbench.gui.components.actions;

import java.awt.event.ActionEvent;

import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBWorkArea;

public class ShowStaticsAction extends BasicAbstractAction{
    
	private static final long serialVersionUID = 5467740924384849511L;
    
	public ShowStaticsAction(InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treePanelReference){
		super("Show Statics", "Stats.png", "Show Statics", workAreaReference, treePanelReference);
		this.workAreaReference = workAreaReference;
    } 
    
    @Override
    public void actionPerformed(ActionEvent event) {

    }
}