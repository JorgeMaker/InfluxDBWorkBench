package com.entropiadevelopments.influxdbworkbench.gui.components;


import javax.swing.JToolBar;

import com.entropiadevelopments.influxdbworkbench.gui.components.actions.CreateDataBaseAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.DisconnectAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.DropDatabaseAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.DropMeasuramentAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.DropSeriesAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ManageAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.NewQueryAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.RefreshAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.RunBackFillAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.RunQueryAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowContinuousQueriesAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowDiagnosticsAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowFieldKeysAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowQueriesAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowRetentionPoliciesAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowSeriesAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowStaticsAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowTagKeysAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowTagValuesAction;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.ShowUsersAction;

public class InfluxDBToolBar extends JToolBar {


	private static final long serialVersionUID = -6640151909241655982L;
	
	private ManageAction manageAction;
	private DisconnectAction disconnectAction;
	private ShowRetentionPoliciesAction showRetentionPoliciesAction;
	private ShowUsersAction showUsersAction;
	private ShowStaticsAction showStaticsAction;
	private ShowDiagnosticsAction showDiagnosticsAction;
	private ShowQueriesAction showQueriesAction;
	private RefreshAction refreshAction;
	private NewQueryAction newQueryAction;
	private RunQueryAction runQueryAction;
	private CreateDataBaseAction createDataBaseAction;
	private ShowContinuousQueriesAction showContinuousQueriesAction;
	private RunBackFillAction runBackFillAction;
	private DropDatabaseAction dropDatabaseAction;
	private ShowTagKeysAction showTagKeysAction;
	private ShowTagValuesAction showTagValuesAction;
	private ShowFieldKeysAction showFieldKeysAction;
	private ShowSeriesAction showSeriesAction;
	private DropSeriesAction dropSeriesAction;
	private DropMeasuramentAction dropMeasuramentAction;
	
	private InfluxDBTreePanel treePanelRegerence;
	private InfluxDBWorkArea workAreaReference;

	public InfluxDBToolBar(InfluxDBWorkArea workAreaReference) {
		
		this.workAreaReference = workAreaReference;
	}	
	public void  createToolBar() {	
		

		manageAction = new ManageAction(workAreaReference, treePanelRegerence);
		disconnectAction = new DisconnectAction(workAreaReference, treePanelRegerence);
		showRetentionPoliciesAction = new ShowRetentionPoliciesAction(workAreaReference, treePanelRegerence);
		showUsersAction = new ShowUsersAction(workAreaReference, treePanelRegerence);
		// NOT IMPLEMENTED YET
		showUsersAction.setEnabled(false);
		showStaticsAction = new ShowStaticsAction(workAreaReference, treePanelRegerence);
		// NOT IMPLEMENTED YET
		showStaticsAction.setEnabled(false);
		showDiagnosticsAction = new ShowDiagnosticsAction(workAreaReference, treePanelRegerence);
		// NOT IMPLEMENTED YET
		showDiagnosticsAction.setEnabled(false);
		showQueriesAction = new ShowQueriesAction(workAreaReference, treePanelRegerence);
		// NOT IMPLEMENTED YET
		showQueriesAction.setEnabled(false);
		refreshAction = new RefreshAction(workAreaReference, treePanelRegerence);
		// NOT IMPLEMENTED YET
		refreshAction.setEnabled(false);
		newQueryAction = new NewQueryAction(workAreaReference, treePanelRegerence);
		runQueryAction = new RunQueryAction(workAreaReference, treePanelRegerence);
		createDataBaseAction = new CreateDataBaseAction(workAreaReference, treePanelRegerence );
		showContinuousQueriesAction = new ShowContinuousQueriesAction(workAreaReference, treePanelRegerence);
		// NOT IMPLEMENTED YET
		showContinuousQueriesAction.setEnabled(false);
		runBackFillAction = new RunBackFillAction(workAreaReference, treePanelRegerence);
		// NOT IMPLEMENTED YET
		runBackFillAction.setEnabled(false);
		dropDatabaseAction = new DropDatabaseAction(workAreaReference, treePanelRegerence);
		showTagKeysAction = new ShowTagKeysAction(workAreaReference, treePanelRegerence);
		showTagValuesAction = new ShowTagValuesAction(workAreaReference, treePanelRegerence);
		showFieldKeysAction = new ShowFieldKeysAction(workAreaReference, treePanelRegerence);
		showSeriesAction = new ShowSeriesAction(workAreaReference, treePanelRegerence);
		dropSeriesAction = new DropSeriesAction(workAreaReference, treePanelRegerence);
		dropMeasuramentAction = new DropMeasuramentAction(workAreaReference, treePanelRegerence);

		add(manageAction); 
		add(disconnectAction);
		add(showRetentionPoliciesAction);
		add(showUsersAction);
		add(showStaticsAction);
		add(showDiagnosticsAction);
		add(showQueriesAction);
		addSeparator();
		add(refreshAction);
		add(newQueryAction);
		add(runQueryAction);
		addSeparator();
		add(createDataBaseAction);
		add(showContinuousQueriesAction);
		add(runBackFillAction);
		add(dropDatabaseAction);
		addSeparator();
		add(showTagKeysAction);
		add(showTagValuesAction);
		add(showFieldKeysAction);
		add(showSeriesAction);
		add(dropSeriesAction);
		add(dropMeasuramentAction);
		setEnableAll(false);
	}
	public void setEnableAll(boolean isEnabled) {
		manageAction.setEnabled(true);
		disconnectAction.setEnabled(isEnabled);
		showRetentionPoliciesAction.setEnabled(isEnabled);
		//showUsersAction.setEnabled(isEnabled);
		//showStaticsAction.setEnabled(isEnabled);
		//showDiagnosticsAction.setEnabled(isEnabled);
		//showQueriesAction.setEnabled(isEnabled);
		//refreshAction.setEnabled(isEnabled);
		newQueryAction.setEnabled(isEnabled);
		runQueryAction.setEnabled(isEnabled);
		createDataBaseAction.setEnabled(isEnabled);
		//showContinuousQueriesAction.setEnabled(isEnabled);
		//runBackFillAction.setEnabled(isEnabled);
		dropDatabaseAction.setEnabled(isEnabled);
		showTagKeysAction.setEnabled(isEnabled);
		showTagValuesAction.setEnabled(isEnabled);
		showFieldKeysAction.setEnabled(isEnabled);
		showSeriesAction.setEnabled(isEnabled);
		dropSeriesAction.setEnabled(isEnabled); 
		dropMeasuramentAction.setEnabled(isEnabled);
	}

	public void setConnectionEnabled() {
		setEnableAll(true);
		newQueryAction.setEnabled(false);
		runQueryAction.setEnabled(false);
		//showContinuousQueriesAction.setEnabled(false);
		//runBackFillAction.setEnabled(false);
		dropDatabaseAction.setEnabled(false);
		showTagKeysAction.setEnabled(false);
		showTagValuesAction.setEnabled(false);
		showFieldKeysAction.setEnabled(false);
		showSeriesAction.setEnabled(false);
		dropSeriesAction.setEnabled(false); 
		dropMeasuramentAction.setEnabled(false);
		
	}
	public void setDatabaseEnabled() {
		setEnableAll(true);
		showRetentionPoliciesAction.setEnabled(false);
		//showUsersAction.setEnabled(false);
		//showStaticsAction.setEnabled(false);
		//showDiagnosticsAction.setEnabled(false);
		//showQueriesAction.setEnabled(false);
		createDataBaseAction.setEnabled(false);
		//dropDatabaseAction.setEnabled(false);
		showTagKeysAction.setEnabled(false);
		showTagValuesAction.setEnabled(false);
		showFieldKeysAction.setEnabled(false);
		showSeriesAction.setEnabled(false);
		dropSeriesAction.setEnabled(false);
		dropMeasuramentAction.setEnabled(false);
	}
	public void setMeasuramentEnabled() {
		setEnableAll(true);
		showRetentionPoliciesAction.setEnabled(false);
		//showUsersAction.setEnabled(false);
		//showStaticsAction.setEnabled(false);
		//showDiagnosticsAction.setEnabled(false);
		//showQueriesAction.setEnabled(false);
		//refreshAction.setEnabled(false);
		createDataBaseAction.setEnabled(false);
		showContinuousQueriesAction.setEnabled(false);
		//runBackFillAction.setEnabled(false);
		dropDatabaseAction.setEnabled(false);
	}
	public void setTreePanelReference(InfluxDBTreePanel treePanel) {
		treePanelRegerence = treePanel;
		
	}
}