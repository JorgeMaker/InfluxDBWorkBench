package com.entropiadevelopments.influxdbworkbench.gui.components.actions;

import java.awt.Color;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.entropiadevelopments.influxdbworkbench.gui.common.GuiToolkit;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBTreePanel;
import com.entropiadevelopments.influxdbworkbench.gui.components.InfluxDBWorkArea;

public abstract class BasicAbstractAction extends AbstractAction {
	
	private static final long serialVersionUID = -9098489003565895768L;
	
	private String iconName;
	private String name;
	protected InfluxDBWorkArea workAreaReference;
	protected InfluxDBTreePanel treePanelReference;

	public BasicAbstractAction(String name, String iconName, String  tooltip, InfluxDBWorkArea workAreaReference, InfluxDBTreePanel treePanelReference) {
		
		this.iconName = iconName;
		this.name = name;
    	this.workAreaReference = workAreaReference;
    	this.treePanelReference = treePanelReference;
		if (name != null) {
			putValue(AbstractAction.NAME,name);
		}
    	if (iconName != null) {
    		this.putValue(AbstractAction.SMALL_ICON, GuiToolkit.getIcon(iconName));
    	}
    	if (tooltip != null) {
    		this.putValue(AbstractAction.SHORT_DESCRIPTION,tooltip);
    	}
    	setupUI();
	}
	public void setupUI() {
    	UIManager.put("ToolTip.background", Color.LIGHT_GRAY);
	    ToolTipManager.sharedInstance().setInitialDelay(500);
	    Border border = BorderFactory.createLineBorder(new Color(76,79,83)); // The color is #4c4f53.
	    UIManager.put("ToolTip.border", border);
	    UIManager.put("ToolTip.font", GuiToolkit.getFont());
	}
	public String getIconName () {
		return iconName;

	}
	public String getName () {
		return name;
	}
} 