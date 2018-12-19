package com.entropiadevelopments.influxdbworkbench.gui.common;


import javax.swing.JPanel;

import com.entropiadevelopments.influxdbworkbench.gui.common.GuiToolkit;

import java.awt.Font;


public class EnhancedJPanel extends JPanel {

	private static final long serialVersionUID = -2069219152382542555L;
	public static final Font FONT = GuiToolkit.getFont();
    public EnhancedJPanel(){
        this.setFont(FONT);
    }
}