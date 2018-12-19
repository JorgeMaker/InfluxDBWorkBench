package com.entropiadevelopments.influxdbworkbench.gui.components.actionpanels;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.table.JTableHeader;

import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedTable;
import com.entropiadevelopments.influxdbworkbench.gui.common.EnhancedTableModel;

public class RetentionPoliciesTable extends EnhancedTable {
	
	public RetentionPoliciesTable(){
		setGridColor(Color.LIGHT_GRAY);
		setShowHorizontalLines(true);
		setShowVerticalLines(true);
		JTableHeader header = getTableHeader();
		header.setBackground(Color.LIGHT_GRAY);
		setTableHeader(header);
		ArrayList<String> nameOfColums = new ArrayList<String> ();
		nameOfColums.add("Policy Name");
		nameOfColums.add("Duration Name");
		nameOfColums.add("Shard Group Duration");
		nameOfColums.add("Replica N");
		nameOfColums.add("Default");
		EnhancedTableModel tableModel = new EnhancedTableModel(nameOfColums);
		setModel(tableModel);
	}

}
