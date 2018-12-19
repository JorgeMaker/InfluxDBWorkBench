package com.entropiadevelopments.influxdbworkbench.gui.components.actions;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBConnection;
import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBDataBase;
import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBTreeElement;
import com.entropiadevelopments.influxdbworkbench.facility.InfluxDBFacility;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBWorkArea;

public class DropDatabaseAction extends BasicAbstractAction {

	private static final long serialVersionUID = 5467740924384849511L;

	public DropDatabaseAction(InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treePanelReference) {
		super("Drop Database", "DropDatabase.png", "Drop Database", workAreaReference, treePanelReference);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		InfluxDBTreeElement selectedTreeElement = treePanelReference.getSelectedTreeElement();
		if (selectedTreeElement != null) {
			if (selectedTreeElement.getClass().equals(InfluxDBDataBase.class)) {
				int response = JOptionPane.showConfirmDialog((JFrame) SwingUtilities.getWindowAncestor(workAreaReference), "Do you want delete database ?", "Confirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.NO_OPTION) {
					return;
				} else if (response == JOptionPane.YES_OPTION) {
					String databaseName = ((InfluxDBDataBase) selectedTreeElement).getID();
					InfluxDBConnection connection = selectedTreeElement.getConnection();
					InfluxDBFacility.getFacilityReference().getInfluxDB(connection).deleteDatabase(databaseName);
					treePanelReference.removeNode(selectedTreeElement);
				} else if (response == JOptionPane.CLOSED_OPTION) {
					return;
				}
			}
		}
	}
}