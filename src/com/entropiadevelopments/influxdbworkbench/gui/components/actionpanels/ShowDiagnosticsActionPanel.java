package com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;

import com.entropiadevelopments.influxdbworkbench.datamodel.InfluxDBConnection;
import com.entropiadevelopments.influxdbworkbench.facility.InfluxDBFacility;
import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedJPanel;

import info.clearthought.layout.TableLayout;

public class ShowDiagnosticsActionPanel extends ActionPanel {

	private static final long serialVersionUID = 1L;

	private EnhancedJPanel mainPanel;

	private EnhancedJPanel systemPanel;
	private JLabel pidValueLabel;
	private JLabel currentTimeValueLabel ;
	private JLabel startedValueLabel;
	private JLabel upTimeValueLabel ;
	
	private EnhancedJPanel buildPanel;
	
	private JLabel branchValueLabel;
	private JLabel commitValueLabel;
	private JLabel versionValueLabel;
	
	private EnhancedJPanel goRuntimePanel;
	
	private JLabel architectureValueLabel;
	private JLabel maxProcsValueLabel;
	private JLabel osValueLabel;
	private JLabel versionGOValueLabel;
	
	private JLabel hotnameValueLabel;
	
	
	private EnhancedJPanel networkPanel;

	public ShowDiagnosticsActionPanel(InfluxDBConnection connection) {
		super("Show Diagnostics", "Diagnostics.png");
		setBorder(new EtchedBorder());
		setLayout(new BorderLayout());
		initComponets();
		layuotComponets();
		add(mainPanel);
	}

	private void initComponets(){
		mainPanel =  new EnhancedJPanel();
		systemPanel = new EnhancedJPanel();
		systemPanel.setBorder(BorderFactory.createTitledBorder("System"));
		buildPanel = new EnhancedJPanel();
		buildPanel.setBorder(BorderFactory.createTitledBorder("Build"));
		goRuntimePanel = new EnhancedJPanel();
		goRuntimePanel.setBorder(BorderFactory.createTitledBorder("Go Runtime"));
		networkPanel = new EnhancedJPanel();
		networkPanel.setBorder(BorderFactory.createTitledBorder("Network"));
		
		pidValueLabel = new JLabel("430");
		currentTimeValueLabel = new JLabel("05/12/2018 23:25:15");
		startedValueLabel = new JLabel("03/11/2018 12:45:20");
		upTimeValueLabel = new JLabel("0d 4h 39m 52s 458ms");
		
		branchValueLabel = new JLabel("1.7");
		commitValueLabel = new JLabel("ksaskjkhqeunnsadhgsbbhywemnbsdasasasfrg");
		versionValueLabel = new JLabel("1.7.1");
		
		architectureValueLabel = new JLabel("arm");
		maxProcsValueLabel = new JLabel("4");
		osValueLabel = new JLabel("Linux");
		versionGOValueLabel = new JLabel("go 1.1");
		
		hotnameValueLabel = new JLabel("raspberrypi:");

	}

	private void layuotComponets(){
		layuotMainPanel();
		layuotSystemPanel();
		layuotBuildPanel();
		layuotGoRuntimePanel();
		layuotNetworkPanel();

	}

	private void layuotBuildPanel() {
		JLabel branchLabel = new JLabel("Branch:");
		JLabel commitLabel = new JLabel("Commit:");
		JLabel versionLabel = new JLabel("Version:");
		double border = 5;
		double widthTag = 90;
		double widthValue = 300;
		double tall = 20;
		double size [][] = {
                {border,widthTag, border,widthValue,TableLayout.FILL },
                {border,tall,border,tall,border,tall,TableLayout.FILL}
            };
		buildPanel.setLayout(new TableLayout(size));
		buildPanel.add(branchLabel,"1,1");
		buildPanel.add(commitLabel,"1,3");
		buildPanel.add(versionLabel,"1,5");
		
		buildPanel.add(branchValueLabel,"3,1");
		buildPanel.add(commitValueLabel,"3,3");
		buildPanel.add(versionValueLabel,"3,5");
	}

	private void layuotNetworkPanel() {
		JLabel hotnameLabel = new JLabel("Hostname:");
		double border = 5;
		double widthTag = 90;
		double widthValue = 120;
		double tall = 20;
		
		double size [][] = {
                {border,widthTag, border,widthValue,TableLayout.FILL },
                {border,tall,border,TableLayout.FILL}
            };
		networkPanel.setLayout(new TableLayout(size));
		networkPanel.add(hotnameLabel,"1,1");
		networkPanel.add(hotnameValueLabel,"3,1");
		
	}

	private void layuotGoRuntimePanel() {
		JLabel architectureLabel = new JLabel("Architecture:");
		JLabel maxProcsLabel = new JLabel("Commit:");
		JLabel osLabel = new JLabel("OS:");
		JLabel versionLabel = new JLabel("Version:");
		double border = 5;
		double widthTag = 90;
		double widthValue = 300;
		double tall = 20;
		double size [][] = {
                {border,widthTag, border,widthValue,TableLayout.FILL },
                {border,tall,border,tall,border,tall,border,tall,TableLayout.FILL}
            };
		goRuntimePanel.setLayout(new TableLayout(size));
		goRuntimePanel.add(architectureLabel,"1,1");
		goRuntimePanel.add(maxProcsLabel,"1,3");
		goRuntimePanel.add(osLabel,"1,5");
		goRuntimePanel.add(versionLabel,"1,7");
		
		goRuntimePanel.add(architectureValueLabel,"3,1");
		goRuntimePanel.add(maxProcsValueLabel,"3,3");
		goRuntimePanel.add(osValueLabel,"3,5");
		goRuntimePanel.add(versionGOValueLabel,"3,7");
		
	}

	private void layuotSystemPanel() {
		JLabel pidLabel = new JLabel("PID:");
		JLabel currentTimeLabel = new JLabel("Current Time:");
		JLabel startedLabel = new JLabel("Started");
		JLabel upTimeLabel = new JLabel("Uptime:");
		
		double border = 5;
		double widthTag = 90;
		double widthValue = 120;
		double tall = 20;
		
		double size [][] = {
                {border,widthTag, border,widthValue,TableLayout.FILL },
                {border,tall,border,tall,border,tall,border,tall,TableLayout.FILL}
            };
		systemPanel.setLayout(new TableLayout(size));
		systemPanel.add(pidLabel,"1,1");
		systemPanel.add(currentTimeLabel,"1,3");
		systemPanel.add(startedLabel,"1,5");
		systemPanel.add(upTimeLabel,"1,7");
		
		systemPanel.add(pidValueLabel,"3,1");
		systemPanel.add(currentTimeValueLabel,"3,3");
		systemPanel.add(startedValueLabel,"3,5");
		systemPanel.add(upTimeValueLabel,"3,7");	
		
	}

	private void layuotMainPanel(){
		
		double border = 5;
		double size [][] = {
                {border, TableLayout.FILL, border},
                {border,130,border, 100,border,130,border,50,border}
            };
		mainPanel.setLayout(new TableLayout(size));
		mainPanel.add(systemPanel,"1,1");
		mainPanel.add(buildPanel,"1,3");
		mainPanel.add(goRuntimePanel,"1,5");
		mainPanel.add(networkPanel,"1,7");
	}

	@Override
	public void runQuery() {
		InfluxDBFacility influxDBFacility = InfluxDBFacility.getFacilityReference();		
	}

}
