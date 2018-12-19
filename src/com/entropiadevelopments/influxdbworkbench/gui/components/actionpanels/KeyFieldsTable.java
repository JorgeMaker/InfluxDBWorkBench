package com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.table.JTableHeader;

import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedTable;
import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedTableModel;

public class KeyFieldsTable extends EnhancedTable{

	private static final long serialVersionUID = 4743143214081243444L;

	public KeyFieldsTable() {
		
		setGridColor(Color.LIGHT_GRAY);
		setShowHorizontalLines(true);
		setShowVerticalLines(true);
		JTableHeader header = getTableHeader();
		header.setBackground(Color.LIGHT_GRAY);
		setTableHeader(header);
		ArrayList<String> nameOfColums = new ArrayList<String> ();
		nameOfColums.add("#");
		nameOfColums.add("Field Key");
		nameOfColums.add("Field Type");
		EnhancedTableModel tableModel = new EnhancedTableModel(nameOfColums);
		setModel(tableModel);
	}
}
