package com.justa.mybatis;

import java.util.Date;
import java.util.List;

public class Person {
	
	private Integer personId;
	private String name;
	private Date birthday;
	
	private StatusEnum status;   
	// by default myBatis will use enum.name() to convert an enum to a string
	// but you can use StatusTypeHandler to do a customized converting
	
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
		return "id=" +personId +",name:" + name +", birthday:" + birthday + ", status:" + status;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}

}
