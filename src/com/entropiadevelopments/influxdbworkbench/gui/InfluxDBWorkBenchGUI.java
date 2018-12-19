package com.entropiadevelopments.influxdbworkbench.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.entropiadevelopments.influxdbworkbench.gui.common.GuiToolkit;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBMenuBar;
import com.entropiadevelopments.influxdbworkbench.gui.components.MainWindowPanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels.ManageConnectionsDialog;

public class InfluxDBWorkBenchGUI extends JFrame {

	private static final long serialVersionUID = 3004554741430283817L;
	private InfluxDBMenuBar menuBar;
	private MainWindowPanel mainWindowPanel;

	public InfluxDBWorkBenchGUI() {
		super("InfluxDB WorkBench");
		
		switch (UIManager.getSystemLookAndFeelClassName()){
		case "com.apple.laf.AquaLookAndFeel":
			setAppleLookAndFeel(); 
			break;
		case "com.sun.java.swing.plaf.windows.WindowsLookAndFeel":
			setWindowsLookAndFeel();
			break;
		}
		
		mainWindowPanel = new MainWindowPanel();
		menuBar = new InfluxDBMenuBar(mainWindowPanel.getWorkAreaReference(), mainWindowPanel.getTreePanelReference());
		setJMenuBar(menuBar);
		getContentPane().add(mainWindowPanel);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
		setSize(900, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setVisible(true);
		ManageConnectionsDialog connectinsDialog = new ManageConnectionsDialog(null, mainWindowPanel.getTreePanelReference());
	}
	private void setWindowsLookAndFeel() {
		GuiToolkit.setDefaultFont();
		try{
			setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			this.setIconImage(GuiToolkit.getImageIcon("InfluxDBWorkBench.png").getImage());
		}
		catch(Exception exception) {
			exception.printStackTrace(System.out);
		}
	}
	private void setAppleLookAndFeel() {
		// Set the font for all GUI Components
		GuiToolkit.setDefaultFont();
		try{
			setDefaultLookAndFeelDecorated(true);
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "InfluxDB WorkBench");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			System.setProperty("com.apple.macos.smallTabs", "true");
		}
		catch(Exception exception) {
			exception.printStackTrace(System.out);
		}
	} 
}