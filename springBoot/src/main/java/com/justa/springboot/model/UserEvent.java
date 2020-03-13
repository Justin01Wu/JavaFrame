package com.justa.springboot.model;

public class UserEvent {
	
	public UserEvent(User user) {
		super();
		this.user = user;
	}

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
