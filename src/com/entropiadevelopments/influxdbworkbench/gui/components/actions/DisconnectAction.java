package com.entropiadevelopments.influxdbworkbench.gui.components.actions;

import java.awt.event.ActionEvent;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBConnection;
import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBTreeElement;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBWorkArea;

public class DisconnectAction extends BasicAbstractAction{
	    
	private static final long serialVersionUID = 5467740924384849511L;
	
    public DisconnectAction(InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treePanelReference){
    	super("Disconnect", "Disconnect.png", "Disconnect",workAreaReference, treePanelReference);
    }
    @Override
    public void actionPerformed(ActionEvent event) {
    	
    	InfluxDBTreeElement selectedTreeElement = treePanelReference.getSelectedTreeElement();
    	if(selectedTreeElement != null) {
        	InfluxDBConnection connection = selectedTreeElement.getConnection();
        	treePanelReference.disconnectServer(connection);
    	}
    }
}