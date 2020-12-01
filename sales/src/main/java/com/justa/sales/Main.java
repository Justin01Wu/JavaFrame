package com.justa.sales;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

// from https://stackoverflow.com/questions/14375920/java-addressbook-input-data-into-a-jtable
public class Main {
	
	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame();
        frame.setSize(768, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
//        JButton addButton = new JButton("Add");
//        addButton.setBounds(10, 228, 89, 23);
//        addButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//
//                JOptionPane.showInputDialog("First Name: ");
//                JOptionPane.showInputDialog("Last Name: ");
//                JOptionPane.showInputDialog("Address: ");
//                JOptionPane.showInputDialog("Town: ");
//                JOptionPane.showInputDialog("County: ");
//                JOptionPane.showInputDialog("Post Code: ");
//                JOptionPane.showInputDialog("Phone Number: ");
//
//            }
//        });
        frame.getContentPane().setLayout(null);
//        frame.getContentPane().add(addButton);

        JButton editButton = new JButton("CheckOut");
        editButton.setBounds(109, 428, 169, 23);
        frame.getContentPane().add(editButton);

        JButton delButton = new JButton("Reset");
        delButton.setBounds(308, 428, 89, 23);
        frame.getContentPane().add(delButton);        

        JMenuBar menuBar = createMenuBar();
        frame.getContentPane().add(menuBar);

        JScrollPane scrollPane = getProductPane();
        frame.getContentPane().add(scrollPane);

        JScrollPane orderPane = getOrderPane();
        frame.getContentPane().add(orderPane);

        frame.setVisible(true);

    }
	
	static JScrollPane getProductPane() throws IOException {
		JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 32, 732, 151);
        JTable table = getProductTable();
        scrollPane.setViewportView(table);
        return scrollPane;
        
	}
	
	static JScrollPane getOrderPane() throws IOException {
		JScrollPane pane = new JScrollPane();
		pane.setBounds(10, 232, 732, 151);
        JTable table = getProductTable();
        pane.setViewportView(table);
        return pane;
        
	}
	
	static JTable getProductTable() throws IOException {
        AbstractTableModel dataTable = getDataTable();
        JTable table = new JTable();
        table.setModel(dataTable);
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		            // your valueChanged overridden method 
		        	System.out.println(table.getSelectedRow());
		        }
		    }
		});
        return table;
	}
	
	static AbstractTableModel getDataTable() throws IOException {
		
        FileProcessor fp =  new FileProcessor();
		Vector<Vector<Object>> data = fp.getProductsData();
		Vector<String> columns = Product.getColumns();
		AbstractTableModel table = new MyModel(data, columns);

		return table;
		
	}
	
	static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 742, 21);

        JMenu mnFile = createFileMenu();
        menuBar.add(mnFile);

        JMenu menuEdit = createEditMenu();
        menuBar.add(menuEdit);
        
        return menuBar;
	}
	
	static JMenu createFileMenu() {

        JMenu mnFile = new JMenu("File");
        // Declares and adds items to the menu
        JMenuItem menuItemNew = new JMenuItem("New");
        mnFile.add(menuItemNew);

        JMenuItem menuItemOpen = new JMenuItem("Open");
        mnFile.add(menuItemOpen);

        JMenuItem menuItemSave = new JMenuItem("Save");
        mnFile.add(menuItemSave);

        JMenuItem menuItemSaveAs = new JMenuItem("Save As..");
        mnFile.add(menuItemSaveAs);

        JMenuItem menuItemPrint = new JMenuItem("Print");
        mnFile.add(menuItemPrint);

        JMenuItem menuItemQuit = new JMenuItem("Quit");
        menuItemQuit.addActionListener(new exitApp());
        mnFile.add(menuItemQuit);

        
        return mnFile;
	}
	
	static JMenu createEditMenu() {
        JMenu menuEdit = new JMenu("Edit");

        JMenuItem menuItemEditSelectedPerson = new JMenuItem(
                "Edit Selected Person");
        menuEdit.add(menuItemEditSelectedPerson);

        JMenuItem menuItemSortByName = new JMenuItem("Sort By Name");
        menuEdit.add(menuItemSortByName);

        JMenuItem menuItemSortPostCode = new JMenuItem("Sort By Post Code");
        menuEdit.add(menuItemSortPostCode);
        return menuEdit;
	}
	


    static class exitApp implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            // closes the application
            System.exit(0);

        }

    }
	
}
