package com.justa.sales;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class FileProcessor {
	
	private List<Product> products;
	
	public FileProcessor() throws IOException {
		products = loadProducts();		
	}
	
	public static void main(String[] args) throws IOException {
		FileProcessor fp =  new FileProcessor();
		fp.loadProducts();
	}
	
	private List<Product> loadProducts() throws IOException {
		List<Product> products =  new ArrayList<>();
		InputStream is = FileProcessor.class.getClassLoader().getResourceAsStream("products.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
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
		Vector<Vector<Object>> result =  new Vector<>();
		for(Product p : products) {
			result.add(p.getDataVector());
		}
		return result;
	}
	
	private static Product createProduct(String line) {
		String[] fields = line.split(",");
		if(fields.length != 5) {
			throw new RuntimeException("unexpected data in the file products.txt: " + line);
		}
		Product p = new Product();
		p.setName(fields[0].trim());
		p.setDesc(fields[1].trim());
		p.setSpec(fields[2].trim());
		Double price =Double.valueOf(fields[3].trim()); 
		p.setPrice(price);
		Integer amount = Integer.valueOf(fields[4].trim());
		p.setAmount(amount);		
		
		return p;
		
		
	}

	public List<Product> getProducts() {
		return products;
	}
}
