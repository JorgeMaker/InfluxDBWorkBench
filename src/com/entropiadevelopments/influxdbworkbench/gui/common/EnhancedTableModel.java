package com.entropiadevelopments.influxdbworkbench.gui.common;

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.Vector;

public class EnhancedTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8919352997347026280L;
	private Vector<Object> rowsVector;
	private ArrayList<String> columnNames;

	public EnhancedTableModel(ArrayList<String> nameOfColums) {
		rowsVector = new Vector<Object>();
		this.columnNames = new ArrayList<String>();
		for (String value: nameOfColums) {
			this.columnNames.add(value);
		}
	}

	public String getColumnName(int column) {

		return (String) columnNames.get(column);
	}

	public int getColumnCount() {
		return columnNames.size();
	}

	public void addTableRecord(TableRecord record) {
		rowsVector.add(record);
	}

	public TableRecord getValueAtRow(int rowIndex) {
		return (TableRecord) rowsVector.get(rowIndex);
	}

	public void removeRow(int row) {
		if (rowsVector.isEmpty()) {
			return;
		} else if (rowsVector.size() >= row) {
			rowsVector.remove(row);
		}
	}
	public boolean hasTableRecord(TableRecord record) {
		if (rowsVector.contains(record)) return true;
		else return false;
	}

	public void removeAllRows() {
		if (rowsVector.isEmpty()) {
			return;
		} else {
			rowsVector.removeAllElements();
		}
	}

	public Vector<Object> getAllRowsVector() {
		return rowsVector;
	}

	public void addAllRows(Vector<Object> alllRows) {
		if (alllRows != null) {
			rowsVector = alllRows;
		} else
			removeAllRows();
	}

	public void removeRow(TableRecord record) {
		if (rowsVector.isEmpty()) {
			return;
		} else if (rowsVector.contains(record)) {
			rowsVector.remove(record);
		}
	}

	public void changeTableRecord(TableRecord record, int rowIndex) {
		rowsVector.setElementAt(record, rowIndex);
	}

	public int getRowCount() {
		return rowsVector.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if (((TableRecord) rowsVector.get(rowIndex)) != null) {
			return ((TableRecord) rowsVector.get(rowIndex)).getFieldAt(columnIndex);
		} else {
			return "";
		}
	}
}
