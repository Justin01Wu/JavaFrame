package com.justa.sales;

import java.util.Vector;

public class Product {

	private int id;
	private String name;
	private String desc;
	private String spec;
	private double price;
	private int amount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Object[] getData() {
		Object[] data = new Object[7];
		data[0] = id;
		data[1] = name;
		data[2] = desc;
		data[3] = spec;
		data[4] = price;
		data[5] = amount;
		data[6] = 0;
		return data;
	}

	public String toString() {
		return id + "," + name + "," + desc + "," + spec + ", ";
	}

	public Vector<Object> getDataVector() {
		Vector<Object> result = new Vector<>();
		Object[] a = getData();
		for (Object o : a) {
			result.add(o);
		}
		return result;
	}

	public static Vector<String> getColumns() {
		Vector<String> result = new Vector<>();
		result.add("ID");
		result.add("Name");
		result.add("Desc");
		result.add("Spec");
		result.add("price");
		result.add("amount");
		result.add("orderNum");

		return result;

	}

}
