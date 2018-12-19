package com.entropiadevelopments.influxdbworkbench.gui.common;

import javax.swing.JTable;

import java.util.Vector;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.ListSelectionModel;

public class EnhancedTable extends JTable {
    
	private static final long serialVersionUID = 1L;
	private EnhancedTableModel tableModel;
    /** Creates a new instance of BasicTable */
    public EnhancedTable(EnhancedTableModel model) { 
        setModel(model);
        getTableHeader().setReorderingAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    public EnhancedTable() {
        getTableHeader().setReorderingAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    public void setModel(EnhancedTableModel model){
        tableModel = model;
        super.setModel(tableModel);
    }
    public void addTableRecord(TableRecord record){
        tableModel.addTableRecord(record);
        tableModel.fireTableDataChanged();
    }
    public void changeTableRecord(TableRecord record, int rowIndex){
        tableModel.changeTableRecord(record, rowIndex);
        tableModel.fireTableDataChanged();
    }
    public void removeSelectedRows() {
        int[] slectedRows = this.getSelectedRows();
        if(slectedRows.length == 0) return;
        Vector<TableRecord> toDeleteVector = new Vector<TableRecord>();
        for(int i=0; i<slectedRows.length;i++){
            toDeleteVector.add(tableModel.getValueAtRow(slectedRows[i]));
        }
        for(int i=0; i<toDeleteVector.size();i++){
            tableModel.removeRow((TableRecord)toDeleteVector.get(i));
        }
        tableModel.fireTableDataChanged();
    }
    public TableRecord getSelectedRowRecord(){
        if(this.getSelectedRow() < 0) return null;
        return tableModel.getValueAtRow(this.getSelectedRow());
    }
    public Vector<Object> getAllRowsVector(){
        return tableModel.getAllRowsVector();
    }
    public void removeAllRows(){
        tableModel.removeAllRows();
        tableModel.fireTableDataChanged();
    }
    public void setAllRows(Vector<Object> alllRows){
        tableModel.addAllRows(alllRows);
        tableModel.fireTableDataChanged();
    }
    public void setUniqueCellRender(TableCellRenderer cellRender){
        for(int i =0; i<tableModel.getColumnCount();i++){
            this.getColumn(tableModel.getColumnName(i)).setCellRenderer(cellRender);
        }
    }
    public void setCellRender( String columnName, TableCellRenderer cellRender){
        this.getColumn(columnName).setCellRenderer(cellRender);
    }
    public void setEnabled(boolean aFlag){
        super.setEnabled(aFlag);
        if(aFlag){
            this.setBackground((Color) UIManager.getDefaults().get("Button.light"));
        }else{
            this.setBackground((Color) UIManager.getDefaults().get("Button.background"));
        }
    }
    public Class getColumnClass(int c){
        try{
            if (getValueAt(0, c) == null){
                return Class.forName("java.lang.String");
            }
            else return getValueAt(0, c).getClass();
        }
        catch(ClassNotFoundException exception){
            exception.printStackTrace();
            return null;
        }
    }
}


