package com.yan.sales;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class Sales {
	private static FileProcessor fp = null;
	private static JTable orderTable = new JTable();
	private static AbstractTableModel dataTable;
	static {
		try {
			fp = new FileProcessor();
			dataTable = getDataTable();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void main(String[] args) throws IOException {

		JFrame frame = new JFrame();
		frame.setSize(768, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Sales 1.0 - Yan Wu");
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Product List (Double Click To Add Order)");
		label.setBounds(10, 10, 250, 20);
		frame.getContentPane().add(label);
		
		JLabel orderLabel = new JLabel("Order List: ");
		orderLabel.setBounds(10, 210, 250, 20);
		frame.getContentPane().add(orderLabel);
		
		JButton checkOutButton = createButton();
		frame.getContentPane().add(checkOutButton);

		JScrollPane scrollPane = getProductPane();
		frame.getContentPane().add(scrollPane);

		JScrollPane orderPane = getOrderPane();
		frame.getContentPane().add(orderPane);

		frame.setVisible(true);
	}

	public static JButton createButton() {
		JButton checkOutButton = new JButton("Checkout");
		checkOutButton.setBounds(109, 400, 169, 23);

		checkOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("checkout");

				DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
				double cost = 0;
				for (int i = 0; i < model.getRowCount(); i++) {
					Object x = model.getValueAt(i, 4);
					cost = cost + (Double) x;
				}
				cost = Math.round(cost);
				JOptionPane.showMessageDialog(null, "$" + cost, "Total Cost: ", JOptionPane.INFORMATION_MESSAGE);
				try {
					fp.save(dataTable);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		return checkOutButton;
	}

	static JScrollPane getProductPane() throws IOException {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 32, 732, 151);
		JTable table = getProductTable();
		scrollPane.setViewportView(table);
		return scrollPane;

	}

	static JTable getProductTable() throws IOException {

		JTable table = new JTable();
		table.setModel(dataTable);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable table = (JTable) mouseEvent.getSource();
				Point point = mouseEvent.getPoint();
				if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
					DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
					MyModel productModel = (MyModel) table.getModel();
					for (Product p : fp.getProducts()) {
						int row = table.getSelectedRow();
						if (productModel.getStock(row) == 0) {
							break;
						}
						if (p.getID() == row + 1) {
							System.out.println(p);
							model.addRow(p.getData());
							productModel.reduceStock(table.getSelectedRow());
							break;
						}

					}

				}
			}
		});
		return table;
	}

	static JScrollPane getOrderPane() throws IOException {
		JScrollPane pane = new JScrollPane();
		pane.setBounds(10, 232, 732, 151);
		Vector<Vector<Object>> data = new Vector<>();
		Vector<String> columns = Product.getOrderColumns();
		DefaultTableModel dataTable = new MyModel(data, columns);
		orderTable.setModel(dataTable);

		pane.setViewportView(orderTable);
		return pane;

	}

	static AbstractTableModel getProductModel() throws IOException {

		FileProcessor fp = new FileProcessor();
		Vector<Vector<Object>> data = fp.getProductsData();
		Vector<String> columns = Product.getColumns();
		AbstractTableModel table = new MyModel(data, columns);
		return table;

	}

	static AbstractTableModel getDataTable() throws IOException {

		Vector<Vector<Object>> data = fp.getProductsData();
		Vector<String> columns = Product.getColumns();
		AbstractTableModel table = new MyModel(data, columns);

		return table;

	}

	static class exitApp implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			// closes the application
			System.exit(0);

		}

	}

}
