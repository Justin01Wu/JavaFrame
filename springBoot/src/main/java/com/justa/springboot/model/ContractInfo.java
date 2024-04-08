package com.justa.springboot.model;

public interface ContractInfo {
	
	public Integer getId();

	public Integer getProgramId();
	public String getName();
	public Integer getParentId();
	public Character getType();
	public Double getEventLimit(); 
}
