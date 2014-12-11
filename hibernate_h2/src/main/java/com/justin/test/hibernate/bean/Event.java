package com.justin.test.hibernate.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table( name = "EVENTS" )
public class Event extends _Base {
	
	private static final long serialVersionUID = -285082342378496203L;

    private String title;
    private String location;
	
    private EventTypeEnum type;

    public Event() {}

    @Column(name = "title", nullable = false, length = 20)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(name = "type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    public EventTypeEnum getType() {
        return type;
    }

    public void setType(EventTypeEnum type) {
        this.type = type;
    }

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}    
	
}
