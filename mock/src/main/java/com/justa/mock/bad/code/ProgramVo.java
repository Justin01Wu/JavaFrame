package com.justa.mock.bad.code;

public class ProgramVo {
	
	static {
		NoWayToCallInUnitTest.call();
	}
	
	private Integer id  = 34; 	
	private String name; 	
	private JSFUtil jsfUtils;
	
	public ProgramVo(){
		new JSFUtil().getInstance();
		jsfUtils = JSFUtil.getCurrentInstance();
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public String getDisplayName() {
		return jsfUtils.getName();
	}

}

