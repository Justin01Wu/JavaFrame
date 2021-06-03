package com.justa.springboot.model;

public class Greeting {
	private final long id;
	private final String content;
	private final Integer range;

	public Greeting(long id, String content, Integer range) {
		this.id = id;
		this.content = content;
		this.range = range;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
	
	public Integer getRange() {
		return range;
	}
}
