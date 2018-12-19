package com.entropiadevelopments.influxdbworkbench.gui.components.actions;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBWorkArea;
import com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels.CreateDatabaseDialog;

public class CreateDataBaseAction extends  BasicAbstractAction{
    
	private static final long serialVersionUID = 5467740924384849511L;

    public CreateDataBaseAction(InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treePanelReference){
    	super("Create DataBase", "CreateDataBase.png", "Create DataBase", workAreaReference, treePanelReference);
    }
   
    @Override
    public void actionPerformed(ActionEvent event) {
    	CreateDatabaseDialog dialog = new CreateDatabaseDialog((JFrame) SwingUtilities.getRoot(treePanelReference),treePanelReference );
    }
}

