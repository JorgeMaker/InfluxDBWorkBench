package com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.influxdb.dto.QueryResult.Series;

import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedTable;
import com.entropiadevelopments.influxdbworkbench.gui.common.TableRecord;

public class QueryResultTable extends EnhancedTable {

	private static final long serialVersionUID = 1L;
	private QueryResultTableModel tableModel;
	private int numOfrows;

	public QueryResultTable(Series serie) {
		setUpUI();
		numOfrows = 1;
		ArrayList<String> columNames = new ArrayList<String>(serie.getColumns());
		tableModel = new QueryResultTableModel(columNames);
		for (Object valuesList : serie.getValues()) {
			ArrayList<Object> rowValues = (ArrayList<Object>) valuesList;
			QueryResultTableRecord tableRecord = new QueryResultTableRecord(numOfrows, rowValues);
			tableModel.addTableRecord(tableRecord);
			numOfrows++;
		}
		setModel(tableModel);
	}

	private void setUpUI() {
		setGridColor(Color.LIGHT_GRAY);
		setShowHorizontalLines(true);
		setShowVerticalLines(true);
		JTableHeader header = getTableHeader();
		header.setBackground(Color.LIGHT_GRAY);
		setTableHeader(header);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		Component comp = super.prepareRenderer(renderer, row, col);
		((JLabel) comp).setHorizontalAlignment(JLabel.LEFT);
		return comp;
	}

	public int getNumOfRows() {
		return numOfrows - 1;
	}

	class QueryResultTableRecord implements TableRecord {

		private ArrayList<Object> tabelRecordData;

		public QueryResultTableRecord(int recordNumber, ArrayList<Object> data) {
			tabelRecordData = new ArrayList<Object>();
			tabelRecordData.add(recordNumber);
			for (Object element : data) {
				tabelRecordData.add(element);
			}
		}

		@Override
		public Object getFieldAt(int fieldIndex) {
			return tabelRecordData.get(fieldIndex);
		}

		@Override
		public int getNumberOfField() {
			return tabelRecordData.size();
		}

		@Override
		public void setFieldAt(int fieldIndex, Object field) {
			tabelRecordData.set(fieldIndex, field);

		}
	}

	class QueryResultTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 8919352997347026280L;
		private Vector<Object> rowsVector;
		private ArrayList<String> columnNames;

		public QueryResultTableModel(ArrayList<String> nameOfColums) {
			rowsVector = new Vector<Object>();
			this.columnNames = new ArrayList<String>();
			this.columnNames.add("#");
			for (String value : nameOfColums) {
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
			if (rowsVector.contains(record))
				return true;
			else
				return false;
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
}
