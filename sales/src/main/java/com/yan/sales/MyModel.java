package com.yan.sales;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class MyModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public MyModel(Vector<Vector<Object>> data, Vector<String> columns) {
		super(data, columns);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void reduceStock(int row) {
		Vector rowValues = (Vector) super.getDataVector().get(row);
		Object value = rowValues.get(5);
		Integer newValue = (Integer) value;
		rowValues.set(5, newValue - 1);
		fireTableCellUpdated(row, 5);
	}

	public int getStock(int row) {
		Vector rowValues = (Vector) super.getDataVector().get(row);
		Object value = rowValues.get(5);
		Integer newValue = (Integer) value;
		return newValue;
	}
}
