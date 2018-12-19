package com.entropiadevelopments.influxdbworkbench.gui.common;

import javax.swing.JDialog;
import javax.swing.JFrame;


import javax.swing.JButton;
import javax.swing.BorderFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Component;
import java.awt.LayoutManager;

import info.clearthought.layout.TableLayout;

public abstract class EnhancedDialog extends JDialog implements ActionListener{
    
	private static final long serialVersionUID = -6298846922943313807L;
	private JButton cancelButton;
    private JButton okButton;
    
    private EnhancedJPanel mainPanel;
    private EnhancedJPanel internalPanel;
    
    public EnhancedDialog(JFrame jFrame, String title, boolean modal, String okLabel,String cancelLabel ){
        super(jFrame,title,modal);
        intBasicComponents(okLabel,cancelLabel );
        layuotBasicComponents();
    }
    private void intBasicComponents(String okLabel,String cancelLabel ){
        
        cancelButton = new JButton(cancelLabel);
        cancelButton.addActionListener(this);
        
        okButton = new JButton(okLabel);
        okButton.addActionListener(this);
        
        mainPanel = new EnhancedJPanel();
        mainPanel.setBorder(BorderFactory.createEtchedBorder());
        
        internalPanel = new EnhancedJPanel();
        
    }
    private void layuotBasicComponents(){
        
        double border = 5;
        double size [][] = {
            {border,0.25,border,0.25,border,0.25,border,0.25,border},
            {border,TableLayout.FILL,border*2,20,border}
        };
        mainPanel.setLayout(new TableLayout(size));
        
        mainPanel.add(internalPanel,"1,1,7,1");
        mainPanel.add(okButton, "3,3");
        mainPanel.add(cancelButton, "5,3");
        
        getContentPane().add(mainPanel);
    }
    public void addComponentToInternalPanel(Component comp, Object constraint){
        internalPanel.add(comp,constraint);
    }
    public void setLayoutToInternalPanel(LayoutManager mngr){
        internalPanel.setLayout(mngr);
    }
    public void actionPerformed(ActionEvent event) {
        if(event.getSource().equals(okButton)){
            okButtonPressedAction();
        }
        else if(event.getSource().equals(cancelButton)){
            cancelButtonPressedAction();
        }
    }
    protected abstract void  okButtonPressedAction();
    protected abstract void  cancelButtonPressedAction(); 
    
}