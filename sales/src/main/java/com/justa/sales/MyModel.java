package com.justa.sales;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class MyModel extends DefaultTableModel{

	private static final long serialVersionUID = 1L;
	
	public MyModel(Vector<Vector<Object>> data, Vector<String> columns) {
		super(data,columns);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	



}
