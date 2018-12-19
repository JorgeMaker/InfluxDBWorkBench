package com.entropiadevelopments.influxdbworkbench.gui.components;

import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;

import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedJPanel;

public class MainWindowPanel extends EnhancedJPanel {

	private static final long serialVersionUID = 8136807080606247154L;
    private InfluxDBToolBar toolBar;
    private JSplitPane centalsplitPane;
    private InfluxDBStatusBarPanel statusBarPanel;
    private InfluxDBWorkArea workArea;
    private InfluxDBTreePanel treePanel;
    
    public MainWindowPanel() { 
    	
        setLayout(new BorderLayout());
        setBorder(new EtchedBorder());
        workArea = new InfluxDBWorkArea();
        statusBarPanel = new InfluxDBStatusBarPanel();
        treePanel = new InfluxDBTreePanel(workArea);
        toolBar = new InfluxDBToolBar(workArea); 
        treePanel.setToolBarReference(toolBar);
        toolBar.setTreePanelReference(treePanel);
        toolBar.createToolBar();
        centalsplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        centalsplitPane.setOneTouchExpandable(false);
        centalsplitPane.setLeftComponent(treePanel); 
        centalsplitPane.setRightComponent(workArea);
        centalsplitPane.setResizeWeight(0.25);
        centalsplitPane.setDividerSize(5);
         
        this.add(toolBar, BorderLayout.NORTH);
        this.add(centalsplitPane, BorderLayout.CENTER);
        this.add(statusBarPanel, BorderLayout.SOUTH);
    }
    public InfluxDBWorkArea getWorkAreaReference(){
    	return workArea;
    	
    }
    public InfluxDBTreePanel getTreePanelReference(){
    	return treePanel;
    	
    }
}
