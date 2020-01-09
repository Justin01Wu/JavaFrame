package com.justa.library.test.jackson;

public class Client {

	private String name;
	private int id;
	private String dnfReinsurer;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDnfReinsurer() {
		return dnfReinsurer;
	}
	public void setDnfReinsurer(String dnfReinsurer) {
		this.dnfReinsurer = dnfReinsurer;
	}
	
	public String toString() {
		return "id:"+ id + ",name:" + name + ", dnfReinsurer:" + dnfReinsurer;
	}


}
