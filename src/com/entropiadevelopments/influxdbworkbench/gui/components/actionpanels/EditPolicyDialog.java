package com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBConnection;
import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBRetentionPolicy;
import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedDialog;
import com.entropiadevelopments.influxdbworkbench.gui.common.IntegerField;

import info.clearthought.layout.TableLayout;

public class EditPolicyDialog extends EnhancedDialog {

	private static final long serialVersionUID = -3462109819564066726L;
	private JTextField policyNameField;
	private JTextField durationField;
	private IntegerField replicationField;
	private JCheckBox defaultCheckBox;

	private InfluxDBConnection connection;
	private RetentionPolicyPanel retentionPanelReference;

	public EditPolicyDialog(JFrame jFrame, InfluxDBConnection connection, RetentionPolicyPanel retentionPanelReference,
			InfluxDBRetentionPolicy editedRP) {
		super(jFrame, "Create Retention Policy", true, "Edit", "Cancel");

		this.connection = connection;
		this.retentionPanelReference = retentionPanelReference;

		policyNameField = new JTextField();
		durationField = new JTextField();
		replicationField = new IntegerField();
		defaultCheckBox = new JCheckBox();

		double border = 5;
		double size[][] = { { border, 120, border, 200, border },
				{ border, 30, border, 30, border, 30, border, 30, border, 30, border } };

		setLayoutToInternalPanel(new TableLayout(size));
		addComponentToInternalPanel(new JLabel("Policy Name: "), "1,1");
		addComponentToInternalPanel(policyNameField, "3,1");

		addComponentToInternalPanel(new JLabel("Duration: "), "1,3");
		addComponentToInternalPanel(durationField, "3,3");

		addComponentToInternalPanel(new JLabel("Replication: "), "1,5");
		addComponentToInternalPanel(replicationField, "3,5");

		addComponentToInternalPanel(new JLabel("Default: "), "1,7");
		addComponentToInternalPanel(defaultCheckBox, "3,7");

		loadValues(editedRP);

		pack();
		setResizable(false);
		setLocationRelativeTo(jFrame);
		setVisible(true);
	}

	private void loadValues(InfluxDBRetentionPolicy editedRP) {
		if (editedRP!= null) {
			policyNameField.setText(editedRP.getPolicyName());
			durationField.setText(editedRP.getDuration());
			replicationField.setText(Double.toString((editedRP.getReplication())));
			defaultCheckBox.setSelected(editedRP.isDefault());
		}
	}

	private InfluxDBRetentionPolicy recollectData() {

		InfluxDBRetentionPolicy influxDBRetentionPolicy = new InfluxDBRetentionPolicy(connection);
		influxDBRetentionPolicy.setDefault(defaultCheckBox.isSelected());
		influxDBRetentionPolicy.setDuration(durationField.getText());
		influxDBRetentionPolicy.setPolicyName(policyNameField.getText());
		influxDBRetentionPolicy.setReplication(Integer.parseInt(replicationField.getText()));

		return influxDBRetentionPolicy;
	}

	@Override
	protected void okButtonPressedAction() {
		retentionPanelReference.editRetentionPolicy(recollectData());
		dispose();
	}

	@Override
	protected void cancelButtonPressedAction() {
		dispose();
	}
}
