package com.justa.sales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// from https://stackoverflow.com/questions/14375920/java-addressbook-input-data-into-a-jtable
public class Main {

	static String[] columnNames = {"Name",  "Desc", "Spec", "price","amount", "orderNum"};
	
	static Object[][] data = {
			{"Iphone11", "Iphone11, the latest iphone",  "64gb", 12.34d, 14, 0},
		    {"XPS13", "Dell XPS 11 desktop",		     "16GB", 899.99d, 24, 0},
		    {"Bao", "Bao",		     "Xiao", 99999999.99d, 1, 0}
		};	
	
	public static void main(String[] args) {
		JTable table = new JTable(data, columnNames);
		
		
		JFrame frame = new JFrame();
        frame.setSize(768, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton addButton = new JButton("Add");
        addButton.setBounds(10, 228, 89, 23);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showInputDialog("First Name: ");
                JOptionPane.showInputDialog("Last Name: ");
                JOptionPane.showInputDialog("Address: ");
                JOptionPane.showInputDialog("Town: ");
                JOptionPane.showInputDialog("County: ");
                JOptionPane.showInputDialog("Post Code: ");
                JOptionPane.showInputDialog("Phone Number: ");

            }
        });
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(addButton);

        JButton editButton = new JButton("Create Order");
        editButton.setBounds(109, 228, 169, 23);
        frame.getContentPane().add(editButton);

        JButton delButton = new JButton("Reset");
        delButton.setBounds(208, 228, 89, 23);
        frame.getContentPane().add(delButton);        

        JMenuBar menuBar = createMenuBar();
        frame.getContentPane().add(menuBar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 32, 732, 151);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        table.setModel(new DefaultTableModel(data, columnNames));
        scrollPane.setViewportView(table);

        frame.setVisible(true);

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
