package com.justa.springboot.model;

import javax.persistence.Convert;
import javax.persistence.Entity;

import com.justa.springboot.db.IndOrientation;

@Entity 
public class TestBean extends BaseEntity{

	@Convert(converter = IndOrientation.Converter.class)
	private IndOrientation indOrientation;

	public IndOrientation getIndOrientation() {
		return indOrientation;
	}

	public void setIndOrientation(IndOrientation indOrientation) {
		this.indOrientation = indOrientation;
	}
}
