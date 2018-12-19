package com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.influxdb.InfluxDBIOException;
import org.influxdb.dto.Pong;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBConnection;
import com.entropiadevelopments.influxdbworkbench.facility.InfluxDBFacility;
import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedJPanel;
import com.entropiadevelopments.influxdbworkbench.gui.common.IntegerField;

import info.clearthought.layout.TableLayout;

public class ConnectionSettingsDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = -7900896869547070728L;
	private JButton cancelButton;
	private JButton pingButton;
	private JButton testButton;
	private JButton saveButton;

	private JTextField nameField;
	private JTextField addresField;
	private JTextField databaseField;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private IntegerField portField;

	private JCheckBox sllCheckBox;
	private ManageConnectionsDialog parentWindow;
	private String connectionID;
	

	public ConnectionSettingsDialog(ManageConnectionsDialog parentWindow, InfluxDBConnection connection) {
		super(parentWindow, "Connection Settings");
		this.parentWindow = parentWindow;
		setModal(true);
		initComponents();
		EnhancedJPanel mainPanel = new EnhancedJPanel();
		mainPanel.setLayout(new BorderLayout());

		mainPanel.add(layoutTopPanel(),BorderLayout.NORTH);
		mainPanel.add(layoutMidPanel(),BorderLayout.CENTER);
		mainPanel.add(layoutBottomPanel(),BorderLayout.SOUTH);
		double border = 10;
		double size [][] = {
        		{border,TableLayout.FILL,border},
                {border,TableLayout.FILL,border}
        };
		
		setLayout(new TableLayout(size));
		add(mainPanel,"1,1");
		if(connection != null) {
			connectionID = connection.getID();
			loadConnectionValues(connection);
			
		}
		else {
			connectionID = null;
		}
		pack();
		setResizable(false);
    	setLocationRelativeTo(SwingUtilities.getWindowAncestor(this));
    	setVisible(true);
	}
	private void loadConnectionValues(InfluxDBConnection connection) {
		nameField.setText(connection.getConnectionName());
		addresField.setText(connection.getIpAddresss());
		databaseField.setText(connection.getDatabase());
		userNameField.setText(connection.getUserName());
		passwordField.setText(connection.getPassword());
		userNameField.setText(connection.getUserName());
		portField.setText(Long.toString(connection.getPort()));
	}

	private EnhancedJPanel layoutBottomPanel() {
		EnhancedJPanel bottomPenel = new EnhancedJPanel();
		double border = 5;
		double size [][] = {
        		{border,0.25,border,0.25, border,0.25, border,0.25,border},
                {border,20,border}
        };
		
		bottomPenel.setLayout(new TableLayout(size));
		bottomPenel.add(testButton,   "1,1");
		bottomPenel.add(pingButton,   "3,1");
		bottomPenel.add(saveButton,   "5,1");
		bottomPenel.add(cancelButton, "7,1");
		
		// TODO Auto-generated method stub
		return bottomPenel;
	}

	private EnhancedJPanel layoutMidPanel() {
		
		EnhancedJPanel midPanel = new EnhancedJPanel();
		double border = 5;
		double size [][] = {
        		{border,80,border,TableLayout.FILL,border},
                {border,20,border,20,border,20,border}
        };
		
		midPanel.setLayout(new TableLayout(size));
		midPanel.add(new JLabel("User Name: "),   "1,1");
		midPanel.add(userNameField, "3,1");
		midPanel.add(new JLabel("Password: "),   "1,3");
		midPanel.add(passwordField, "3,3");
		midPanel.add(new JLabel("Security: "),   "1,5");
		midPanel.add(sllCheckBox, "3,5");
		
		return midPanel;
	}

	private EnhancedJPanel layoutTopPanel() {
		EnhancedJPanel topPanel = new EnhancedJPanel();

        double border = 5;
        double label = 60;

        double size [][] = {
        		{border,label,border, TableLayout.FILL, border, 0.3, border},
                {border,20, 20,border,20, 20, border,20,20, border}
        };
        
        topPanel.setLayout(new TableLayout(size));
        
        topPanel.add(new JLabel("Name: "), "1,1");
        topPanel.add(nameField, "3,1,5,1");
        
        JLabel messageLabel = new JLabel("Choose a connection name to identify the connection");
        messageLabel.setForeground(Color.GRAY);
        topPanel.add(messageLabel,"3,2,5,2");
		
        topPanel.add(new JLabel("Address: "), "1,4");
        topPanel.add(addresField, "3,4");
        topPanel.add(portField, "5,4");
        
        JLabel messageAddresLabel =new JLabel("Specify host and port of InfluxDB");
        messageAddresLabel.setForeground(Color.GRAY);
        topPanel.add(messageAddresLabel, "3,5,5,5");
        
        topPanel.add(new JLabel("Database: "), "1,7");
        topPanel.add(databaseField, "3,7,5,7");
        
        JLabel optionalMessageLabel =new JLabel("Optionally specify a database ");
        optionalMessageLabel.setForeground(Color.GRAY);
        topPanel.add(optionalMessageLabel, "3,8,5,8");
        
		return topPanel;
	
	}

	private void initComponents() {
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		pingButton = new JButton("Ping");
		pingButton.addActionListener(this);
		
		testButton = new JButton("Test");
		testButton.addActionListener(this);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);

		nameField = new JTextField();
		addresField = new JTextField();
		databaseField = new JTextField();
		userNameField = new JTextField();
		passwordField = new JPasswordField();
		portField = new IntegerField();

		sllCheckBox = new JCheckBox("Use SSL");
		// NOT IMPLEMENTED YET
		sllCheckBox.setEnabled(false);
	}
	private InfluxDBConnection collectData(){
		if (connectionID == null) {
			connectionID = Long.toString((System.currentTimeMillis()));
		}
		InfluxDBConnection connection = new InfluxDBConnection(connectionID);
		connection.setUserName(userNameField.getText());
		connection.setDatabase(databaseField.getText());
		connection.setIpAddresss(addresField.getText());
		connection.setPort(Integer.parseInt(portField.getText()));
		connection.setPassword(passwordField.getText());
		connection.setConnectionName(nameField.getText());
		connection.setConnectionName(nameField.getText());
		connection.setUseSLL(sllCheckBox.isSelected());
		
		return connection;
	}

	@Override
	public void actionPerformed(ActionEvent event){
		if (event.getSource().equals(cancelButton)){
			dispose();
			
		}
		if (event.getSource().equals(pingButton)){
			InfluxDBConnection connection = collectData();
			try {
				InfluxDBFacility facility = InfluxDBFacility.getFacilityReference();
				Pong pong = facility.pingConnection(connection);
				JOptionPane.showMessageDialog(this, "Remote Time:"+ pong.getResponseTime()+ "\nInfluxDB Version: "+pong.getVersion(),"Pong", JOptionPane.INFORMATION_MESSAGE);

			}
			catch(InfluxDBIOException influxDBIOException) {
				JOptionPane.showMessageDialog(this, "Not possible to connect to InfluxDB","Pong", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (event.getSource().equals(testButton)){
			InfluxDBConnection connection = collectData();
			try {
				InfluxDBFacility facility = InfluxDBFacility.getFacilityReference();
				facility.getInfluxDB(connection);
				JOptionPane.showMessageDialog(this, "Connection sucessfull","Success", JOptionPane.INFORMATION_MESSAGE);
			}
			catch(InfluxDBIOException influxDBIOException) {
				JOptionPane.showMessageDialog(this, "Not possible to connect to InfluxDB","Failure", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (event.getSource().equals(saveButton)){
			InfluxDBConnection connection = collectData();
			parentWindow.addConnection(connection);
			dispose();
		}
	}
}
