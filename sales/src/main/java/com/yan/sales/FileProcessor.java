package com.yan.sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class FileProcessor {
	private static String filePath = System.getProperty("user.home") + "/products.txt";
	private List<Product> products;

	public FileProcessor() throws IOException {
		products = loadProducts();
	}

	public static void main(String[] args) throws IOException {
		FileProcessor fp = new FileProcessor();
	}

	public List<Product> loadProducts() throws IOException {
		File file = new File(filePath);
		List<Product> products = new ArrayList<>();
		BufferedReader br;
		if (file.exists()) {
			br = new BufferedReader(new FileReader(file));
		} else {
			InputStream is = FileProcessor.class.getClassLoader().getResourceAsStream("products.txt");
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		}
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
			Product p = createProduct(line);
			products.add(p);

		}

		br.close();

		return products;
	}

	public Vector<Vector<Object>> getProductsData() throws IOException {
		Vector<Vector<Object>> result = new Vector<>();
		List<Product> products = this.getProducts();
		for (Product p : products) {
			result.add(p.getDataVector());
		}
		return result;
	}

	private static Product createProduct(String line) {
		String[] fields = line.split(",");
		if (fields.length != 6) {
			throw new RuntimeException("unexpected data in the file products.txt: " + line);
		}
		Product p = new Product();
		Integer id = Integer.valueOf(fields[0].trim());
		p.setID(id);
		p.setName(fields[1].trim());
		p.setDesc(fields[2].trim());
		p.setSpec(fields[3].trim());
		Double price = Double.valueOf(fields[4].trim());
		p.setPrice(price);
		Integer amount = Integer.valueOf(fields[5].trim());
		p.setStock(amount);

		return p;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void save(AbstractTableModel dataTable) throws FileNotFoundException {

		try (PrintWriter out = new PrintWriter(filePath)) {
			for (int i = 0; i < dataTable.getRowCount(); i++) {
				Object value = dataTable.getValueAt(i, 0);
				out.print(value);
				out.print(",");
				value = dataTable.getValueAt(i, 1);
				out.print(value);
				out.print(",");
				value = dataTable.getValueAt(i, 2);
				out.print(value);
				out.print(",");
				value = dataTable.getValueAt(i, 3);
				out.print(value);
				out.print(",");
				value = dataTable.getValueAt(i, 4);
				out.print(value);
				out.print(",");
				value = dataTable.getValueAt(i, 5);
				out.print(value);
				out.println();
			}
		}
	}
}