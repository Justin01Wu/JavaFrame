package com.justa.library.test.freemarker;

public class ParameterEntry {
	private String name;
	private String javaType;
	private String type;  // @QueryParam or  @PathParam

	public ParameterEntry(String name, String javaType, String type) {
		super();
		this.name = name;
		this.javaType = javaType;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
