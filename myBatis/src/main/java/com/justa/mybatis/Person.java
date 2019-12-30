package com.justa.mybatis;

import java.util.List;

public class Person {
	
	private Integer personId;
	private String name;
	private List<String> addresses;
	
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}
	
	@Override
	public String toString() {
		return "id=" +personId +",name:" + name;
	}

}
