package com.entropiadevelopments.influxdbworkbench.gui.common;

import javax.swing.JPanel;

import javax.swing.BorderFactory;

import javax.swing.JButton;
import java.util.Vector;
import java.awt.Dimension;
import java.awt.GridLayout;

public class ButtonPanel extends JPanel {
    
	private static final long serialVersionUID = -9119415391066095779L;
	private Vector<JButton> buttonsStore;
    /** Creates a new instance of ButtonPanel */
    public ButtonPanel(int verticalSize, int border, int buttonNumber, int buttonGap) {
        buttonsStore = new Vector<JButton>(buttonNumber);
        
        setPreferredSize(new Dimension(Short.MAX_VALUE,verticalSize));
        setMaximumSize(new Dimension(Short.MAX_VALUE,verticalSize));
        setMinimumSize(new Dimension(Short.MAX_VALUE,verticalSize));
        
        setLayout(new GridLayout(0,buttonNumber,buttonGap,0));
        setBorder(BorderFactory.createEmptyBorder(border, border, border ,border));
    }
    public void addButton(JButton button){
        buttonsStore.add(button);
        layoutButton(button);  
    }
    private void layoutButton(JButton button){
        this.add(button);
    }
    public void setEnabledPanel(boolean aFlag){
        for(int index = 0; index< buttonsStore.size(); index++){
            ((JButton)buttonsStore.get(index)).setEnabled(aFlag);
        }
        this.setEnabled(aFlag);
    }
}
