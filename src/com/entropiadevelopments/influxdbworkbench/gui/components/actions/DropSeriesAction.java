package com.entropiadevelopments.influxdbworkbench.gui.components.actions;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.influxdb.dto.Query;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBConnection;
import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBMeasuramet;
import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBTreeElement;
import com.entropiadevelopments.influxdbworkbench.facility.InfluxDBFacility;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBWorkArea;

public class DropSeriesAction extends BasicAbstractAction {

	private static final long serialVersionUID = 5467740924384849511L;

	public DropSeriesAction(InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treePanelReference) {
		super("Drop Series", "DropSeries.png", "Drop Series", workAreaReference, treePanelReference);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		InfluxDBTreeElement selectedTreeElement = treePanelReference.getSelectedTreeElement();
		if (selectedTreeElement != null) {
			if (selectedTreeElement.getClass().equals(InfluxDBMeasuramet.class)) {
				int response = JOptionPane.showConfirmDialog(SwingUtilities.getRoot(treePanelReference),
						"Do you want drop series?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.NO_OPTION) {
					return;
				} else if (response == JOptionPane.YES_OPTION) {

					String masuramentName = ((InfluxDBMeasuramet) selectedTreeElement).toString();
					String databaseName = ((InfluxDBMeasuramet) selectedTreeElement).getDatabase();
					InfluxDBConnection connection = selectedTreeElement.getConnection();
					Query query = new Query("DROP SERIES FROM " + masuramentName, databaseName);
					InfluxDBFacility.getFacilityReference().getInfluxDB(connection).query(query);

				} else if (response == JOptionPane.CLOSED_OPTION) {
					return;
				}
			}
		}
	}

}