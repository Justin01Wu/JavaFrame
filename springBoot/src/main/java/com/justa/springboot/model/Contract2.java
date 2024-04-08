package com.justa.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Contract2 implements ContractInfo{

	@Id
	private Integer id;	
	
	private Integer programId;

	@Column(length = 250)
	private String name;
	
	
	private Integer parentId;
	private Character type;	
	
	private Double eventLimit;

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Override
	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}

	@Override
	public Double getEventLimit() {
		return eventLimit;
	}

	public void setEventLimit(Double eventLimit) {
		this.eventLimit = eventLimit;
	}

}
