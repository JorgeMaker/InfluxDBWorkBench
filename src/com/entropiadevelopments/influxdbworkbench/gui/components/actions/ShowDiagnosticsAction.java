package com.entropiadevelopments.influxdbworkbench.gui.components.actions;

import java.awt.event.ActionEvent;

import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBWorkArea;
import com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels.ShowDiagnosticsActionPanel;

public class ShowDiagnosticsAction extends BasicAbstractAction{

	private static final long serialVersionUID = 5467740924384849511L;
    public ShowDiagnosticsAction(InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treePanelReference){
    	super("Show Diagnostics", "Diagnostics.png", "Show Diagnostics",workAreaReference, treePanelReference);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		workAreaReference.addWorkPanel(new ShowDiagnosticsActionPanel(null));
		
	}
}