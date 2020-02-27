package com.justa.springboot.db;

import javax.persistence.Entity;
import javax.persistence.Transient;

// from https://spring.io/guides/gs/accessing-data-mysql/
@Entity // This tells Hibernate to make a table out of this class
public class User extends BaseEntity  {

	private String name;
	
	@Transient   // it means JPA won't persist this field
	private String gender;
	
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
