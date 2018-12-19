package com.entropiadevelopments.influxdbworkbench.gui.components;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.entropiadevelopments.influxdbworkbench.gui.common.GuiToolkit;
import com.entropiadevelopments.influxdbworkbench.gui.components.actions.*;


public class InfluxDBMenuBar extends JMenuBar {

	private static final long serialVersionUID = -7588474878565666284L;

	private JMenu flieMenu;
	private JMenu connectionsMenu;
	private JMenu queryMenu;
	private JMenu settingsMenu;
	private JMenu helpMenu;
	
	private JMenu importMenu;
	private JMenu exportMenu;
	
	private JMenuItem importSettinsMenuItem;;
	private JMenuItem exportSettinsMenuItem;
	private JMenuItem exitMenuItem;
	
	private JMenuItem refreshMenuItem;
	private JMenuItem manageMenuItem;
	
	private JMenuItem newQueryMenuItem;
	private JMenuItem runQueryMenuItem;
	private JMenuItem showQueryMenuItem;
	
	private JMenu dateFormatMenu;
	private JMenu timeFormatMenu;
	
	
	private JMenuItem time12FormatMenuItem;
	private JMenuItem time24FormatMenuItem;
	
	private JMenuItem englishDateFormatMenuItem;
	private JMenuItem latinDateFormatMenuItem;
	
	private JMenuItem allowSLLMenuItem;
	
	private JMenuItem aboutMenuItem;


	public InfluxDBMenuBar(InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treepanelReference ) {
		
		setFont(GuiToolkit.getFont());
				
		flieMenu = new JMenu("File");
		flieMenu.setMnemonic('F');

		importMenu = new JMenu("Import");
		exportMenu = new JMenu("Export");
		
		exportSettinsMenuItem = new JMenuItem(new ExportSettingstAction(workAreaReference, treepanelReference));
		importMenu.add(exportSettinsMenuItem);
		// NOT IMPLEMENTED YET
		importMenu.disable();
		
		importSettinsMenuItem = new JMenuItem(new ImportSettinsAction(workAreaReference, treepanelReference));
		exportMenu.add(importSettinsMenuItem);
		// NOT IMPLEMENTED YET
		exportMenu.disable();
		
		exitMenuItem =  new JMenuItem(new ExitAction(workAreaReference, treepanelReference));

		flieMenu.add(importMenu);
		flieMenu.add(exportMenu);
		flieMenu.add(exitMenuItem);
		add(flieMenu);
		
		connectionsMenu = new JMenu("Connection");
		connectionsMenu.setMnemonic('C');
		
		refreshMenuItem  = new JMenuItem(new RefreshAction(workAreaReference, treepanelReference));
		connectionsMenu.add(refreshMenuItem);
		// NOT IMPLEMENTED YET
		refreshMenuItem.disable();
		
		manageMenuItem = new JMenuItem(new ManageAction(workAreaReference, treepanelReference));
		connectionsMenu.add(manageMenuItem);
		
		add(connectionsMenu);
		

		queryMenu = new JMenu("Query"); 
		queryMenu.setMnemonic('Q');
		
		newQueryMenuItem  = new JMenuItem(new NewQueryAction(workAreaReference, treepanelReference));
		queryMenu.add(newQueryMenuItem);	
		
		runQueryMenuItem  = new JMenuItem(new RunQueryAction(workAreaReference, treepanelReference));
		queryMenu.add(runQueryMenuItem);
		
		showQueryMenuItem  = new JMenuItem(new ShowQueriesAction(workAreaReference, treepanelReference));
		queryMenu.add(showQueryMenuItem);
		// NOT IMPLEMENTED YET
		showQueryMenuItem.disable();
		
		add(queryMenu);
		
		settingsMenu = new JMenu("Settings");
		settingsMenu.setMnemonic('S');
		
		dateFormatMenu = new JMenu("Date Format");
		
		englishDateFormatMenuItem = new JMenuItem(new EnglishDateFormatAction(workAreaReference, treepanelReference));
		dateFormatMenu.add(englishDateFormatMenuItem);
		
		latinDateFormatMenuItem = new JMenuItem(new LatinDateFormatAction(workAreaReference, treepanelReference));
		dateFormatMenu.add(latinDateFormatMenuItem);
		timeFormatMenu = new JMenu("Time Format");
		
		time12FormatMenuItem = new JMenuItem(new Time12hFormatAction(workAreaReference, treepanelReference));
		timeFormatMenu.add(time12FormatMenuItem);
		time24FormatMenuItem = new JMenuItem(new Time24hFormatAction(workAreaReference, treepanelReference));
		
		allowSLLMenuItem = new JMenuItem(new AllowUntrustedSLLAction(workAreaReference, treepanelReference));
		
		settingsMenu.add(dateFormatMenu);
		settingsMenu.add(timeFormatMenu);
		settingsMenu.add(allowSLLMenuItem);
		
		
		timeFormatMenu.add(time24FormatMenuItem);
		add(settingsMenu);
		// NOT IMPLEMENTED YET
		settingsMenu.disable();
		
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		
		aboutMenuItem = new JMenuItem(new AboutAction(workAreaReference, treepanelReference));
		
		helpMenu.add(aboutMenuItem);
		add(helpMenu);
		// NOT IMPLEMENTED YET
		helpMenu.disable();
		
	}
}