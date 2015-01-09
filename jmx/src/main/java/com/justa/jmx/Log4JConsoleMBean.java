package com.justa.jmx;

public interface Log4JConsoleMBean {

	public String getClassOrPackageLevel(String loggerPath);
	public String setClassOrPackageLevel(String loggerPath, String level);
	
}
