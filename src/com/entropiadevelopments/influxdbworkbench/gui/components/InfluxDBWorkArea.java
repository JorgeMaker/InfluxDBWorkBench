package com.entropiadevelopments.influxdbworkbench.gui.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import com.entropiadevelopments.influxdbworkbench.gui.common.GuiToolkit;
import com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels.ActionPanel;

public class InfluxDBWorkArea extends JTabbedPane { 
		
	private static final long serialVersionUID = 1L;
	private int tabCounter;

	public InfluxDBWorkArea(){   
		setBorder(new EtchedBorder());
        this.setTabPlacement(JTabbedPane.TOP);
        this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        this.setFont(GuiToolkit.getFont());
        tabCounter = 0;
	}
	public void addWorkPanel(ActionPanel actionPanel ) {
		this.addTab(actionPanel.getName(), actionPanel.getIcon(), actionPanel, "siiiii");
        new ClosableTabPane(this,actionPanel.getIcon() ,tabCounter ,true);
        actionPanel.setIndex(tabCounter);
        tabCounter +=1;
	}
	protected void removePanel(ClosableTabPane actionPanel ) {
		int i = indexOfTabComponent(actionPanel);
        if (i != -1){
        	remove(i);
        	tabCounter -=1;
        }
	}
	public void runQuery(){
		ActionPanel actionPanel = (ActionPanel) getSelectedComponent();
		if (actionPanel != null){
			actionPanel.runQuery();
		}
	}
}

class ClosableTabPane   extends JPanel implements ActionListener{
    
	private static final long serialVersionUID = 1L;
	private InfluxDBWorkArea workAreaReference;
	
    public ClosableTabPane(final InfluxDBWorkArea workAreaReference, Icon icon, final int index, boolean isVisible){
    	
        this.workAreaReference = workAreaReference;
        
        setOpaque(false);
        JLabel tabLabel = new JLabel(workAreaReference.getTitleAt(index));
        if (icon!=null) {
        	tabLabel.setIcon(icon);
        } 
        add(tabLabel);
        
        Icon closeIcon = new CloseIcon(false);
        Icon closeIconR = new CloseIcon(true);
        
        JButton closeButton = new JButton(closeIcon);
        closeButton.setContentAreaFilled(false);
        closeButton.setVisible(isVisible);
        closeButton.setRolloverEnabled(true);
        closeButton.setRolloverIcon(closeIconR);
        closeButton.setUI(new BasicButtonUI());
        closeButton.setPreferredSize(new Dimension(closeIcon.getIconWidth(), closeIcon.getIconHeight()));
        add(closeButton);
        closeButton.addActionListener(this);
        
        workAreaReference.setTabComponentAt(index, this); 
    }
    
    public void actionPerformed(final ActionEvent e) {
    	workAreaReference.removePanel(this);
        
    }
}

class CloseIcon implements Icon{
	
    private boolean rollover;
    
    public CloseIcon(boolean rollover){
        this.rollover = rollover;
    }
    
    public void paintIcon(Component component, Graphics graphics, int x, int y) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        Stroke stroke = graphics2D.getStroke();
        graphics2D.setStroke(new BasicStroke(2));
        graphics.setColor(Color.BLACK);
        if (rollover) {
        	graphics.setColor(Color.RED);
        }
        graphics.drawLine(6, 6, getIconWidth() - 7, getIconHeight() - 7);
        graphics.drawLine(getIconWidth() - 7, 6, 6, getIconHeight() - 7);
        graphics2D.setStroke(stroke);
    }
    
    public int getIconWidth(){
    	return 17;
    }
    
    public int getIconHeight(){
    	return 17;
    }
}