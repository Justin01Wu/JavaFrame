package com.justa.jmx;

public interface Log4JConsoleMBean {
	public String getAllLevels();
	public String getLevel(String classPath);
	public int getLevelInt(String classPath);
	public String setLevel(String classPath, int levelInt);
}
