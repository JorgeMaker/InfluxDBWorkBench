package com.entropiadevelopments.influxdbworkbench.gui.components.actions;


import java.awt.event.ActionEvent;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBMeasuramet;
import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBTreeElement;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBWorkArea;
import com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels.NewQueryActionPanel;

public class NewQueryAction extends BasicAbstractAction{
    
	private static final long serialVersionUID = 5467740924384849511L;
    public NewQueryAction(InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treePanelReference){
    	super("New Query", "NewQuery.png", "New Query", workAreaReference, treePanelReference);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
    	
    	InfluxDBTreeElement treeElement = treePanelReference.getSelectedTreeElement();
    	String measuramentName = "measurament";
    	String database = "";
    	if (treeElement != null) {
        	if (treeElement.getClass().equals(InfluxDBMeasuramet.class)) {
        		measuramentName = treeElement.toString();
        		database = ((InfluxDBMeasuramet)treeElement).getDatabase();
        		
        	}
        	workAreaReference.addWorkPanel(new NewQueryActionPanel(treeElement.getConnection(), measuramentName, database));
    	}
    }
} 