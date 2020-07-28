package com.justa.springboot.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.justa.springboot.db.PositionEnum;
import com.justa.springboot.db.PositionEnumConverter;

// from https://spring.io/guides/gs/accessing-data-mysql/
@Entity // This tells Hibernate to make a table out of this class
public class User extends BaseEntity  {

	private String name;
	
	@Transient   // it means JPA won't persist this field
	private String gender;
	
	@Column(length = 25)
	//@Convert(converter = PositionEnumConverter.class)
	//@Enumerated(EnumType.STRING)
	private PositionEnum position;
	
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
	
	@Override
	public int hashCode() {
		return Objects.hash(getId(), name, email);
	}

	public PositionEnum getPosition() {
		return position;
	}

	public void setPosition(PositionEnum position) {
		this.position = position;
	}
}
