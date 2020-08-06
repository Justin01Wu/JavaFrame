package com.justa.springboot.model;

import javax.persistence.Convert;
import javax.persistence.Entity;

import com.justa.springboot.db.converter2.IndOrientation;
import com.justa.springboot.db.converter2.IntegerEnum;

@Entity 
public class TestBean extends BaseEntity{

	@Convert(converter = IndOrientation.Converter.class)
	private IndOrientation indOrientation;
	
	@Convert(converter = IntegerEnum.Converter.class)
	private IntegerEnum gender;

	public IndOrientation getIndOrientation() {
		return indOrientation;
	}

	public void setIndOrientation(IndOrientation indOrientation) {
		this.indOrientation = indOrientation;
	}

	public IntegerEnum getGender() {
		return gender;
	}

	public void setGender(IntegerEnum gender) {
		this.gender = gender;
	}
}
