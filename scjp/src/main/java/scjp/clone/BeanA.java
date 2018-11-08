package scjp.clone;

import java.util.Date;

public class BeanA implements Cloneable {
	
	private int id;
	private Integer code;
	private String name;
	private Date birthDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@Override
	public BeanA clone() throws CloneNotSupportedException {
		BeanA that =  (BeanA)super.clone();
		that.name = this.name+ "-cloned";
		return that;
	}


}
