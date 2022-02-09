package com.justa.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Contract2 {

	@Id
	private Integer id;
	private Integer programId;

	@Column(length = 250)
	private String name;
	private Integer parentId;
	private Character type;
	
	@Column(name = "event_limit")
	private double eventLimit;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}

	public double getEventLimit() {
		return eventLimit;
	}

	public void setEventLimit(double eventLimit) {
		this.eventLimit = eventLimit;
	}

}
