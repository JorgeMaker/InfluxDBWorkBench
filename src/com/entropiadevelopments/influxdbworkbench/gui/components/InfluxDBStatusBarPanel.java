package com.entropiadevelopments.influxdbworkbench.gui.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.border.SoftBevelBorder;

import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedJPanel;


public class InfluxDBStatusBarPanel extends EnhancedJPanel {
    
	private static final long serialVersionUID = -6370999898534667900L;
	private JLabel dateLabel;
    private JLabel statusLabel;
    private JLabel messageLabel;

    public InfluxDBStatusBarPanel(){

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        this.setLayout(gridBagLayout);
        
        this.setPreferredSize(new Dimension(400,24));
  
        statusLabel= new JLabel("");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setVerticalAlignment(JLabel.CENTER);
        statusLabel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        statusLabel.setPreferredSize(new Dimension(96,20));
        statusLabel.setMaximumSize(new Dimension(96,20));
        
        messageLabel= new JLabel(" "); 
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setVerticalAlignment(JLabel.CENTER);
        messageLabel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        messageLabel.setPreferredSize(new Dimension(396,20));
        
        dateLabel = new JLabel("");
        
        dateLabel.setHorizontalAlignment(JLabel.CENTER);
        dateLabel.setVerticalAlignment(JLabel.CENTER);
        dateLabel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        dateLabel.setPreferredSize(new Dimension(96,20));
        dateLabel.setMaximumSize(new Dimension(96,20));
   
        gridBagConstraints.insets = new Insets(2,1,2,2);
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        this.add(statusLabel,gridBagConstraints);
        
        gridBagConstraints.insets = new Insets(2,2,2,2);
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 1;
        this.add(messageLabel,gridBagConstraints);

        gridBagConstraints.insets = new Insets(2,2,2,1);
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 1;
        this.add(dateLabel,gridBagConstraints);
       
    }
    /** El string que se le pasa aparece displayado en la etiqueta
     * donde se muestran los mensajes al usuario.
     * @param message l string que se le pasa aparece displayado en la etiqueta
     * donde se muestran los mensajes al usuario.
     */    
    public void setMesage(String message){
        messageLabel.setText(message);
    }
    public void setStatus(String status){
        statusLabel.setText(status);
    }
    public void clear(){
        statusLabel.setText("");
        messageLabel.setText("");
    }
    public void clearMesage(){
        messageLabel.setText("");
    }
    public void clearStatus(){
        statusLabel.setText("");
    }
}