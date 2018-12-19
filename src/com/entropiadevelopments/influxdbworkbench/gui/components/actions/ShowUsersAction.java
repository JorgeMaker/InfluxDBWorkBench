package com.entropiadevelopments.influxdbworkbench.gui.components.actions;

import java.awt.event.ActionEvent;

import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBWorkArea;
import com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels.ShowUsersActionPanel;

public class ShowUsersAction extends BasicAbstractAction{
	
	private static final long serialVersionUID = 5467740924384849511L;
    public ShowUsersAction(InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treePanelReference){
    	super("Show Users", "Users.png", "Show Users",workAreaReference, treePanelReference);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
    	workAreaReference.addWorkPanel(new ShowUsersActionPanel());
    	
    }
}
