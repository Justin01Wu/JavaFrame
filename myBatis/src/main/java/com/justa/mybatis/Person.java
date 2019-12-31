package com.justa.mybatis;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Person {
	
	private Integer personId;
	private String name;
	private Date birthday;	
	
	private GenderEnum gender;
	private StatusEnum status;   
	// by default myBatis will use enum.name() to convert an enum to a string
	// but you can use statusAsStr to do a customized converting
	
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
	
	public Integer getStatusAsInt() {
		return status.getValue();
	}
	public void setStatusAsInt(Integer status) throws SQLException {
		this.status = StatusTypeHandler.getEnum(status);
	}
	public GenderEnum getGender() {
		return gender;
	}
	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return "id=" +personId 
				+",name:" + name 
				+", birthday:" + birthday 
				+ ", status:" + status
				+ ", gender:" + gender
				+ ", address:" + addresses;
	}

}
