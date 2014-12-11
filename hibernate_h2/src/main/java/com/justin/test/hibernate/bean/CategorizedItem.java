package com.justin.test.hibernate.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * An entity class that represents a link table with additional columns
 */

@Entity
@Table(name = "CATEGORIZED_ITEM")
public class CategorizedItem {

	@EmbeddedId
	private _MyEmbededId id = new _MyEmbededId();
	
	@Column(name = "ADDED_BY_USER")
	private String username;
	
	@Column(name = "ADDED_ON")
	private Date dateAdded = new Date();
	
	@ManyToOne
	@JoinColumn(name = "item_id", insertable = false, updatable = false)
	private Item item;
	
	@ManyToOne
	@JoinColumn(name = "cat_id", insertable = false, updatable = false)
	private Category category;

	public CategorizedItem() {
	}

	public CategorizedItem(String username, Category category, Item item) {
		// Set fields
		this.username = username;
		this.category = category;
		this.item = item;
		// Set identifier values
		this.id.setCategoryId(category.getId());
		this.id.setItemId( item.getId());
		// Guarantee referential integrity
		category.getCategorizedItems().add(this);
		item.getCategorizedItems().add(this);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public _MyEmbededId getId() {
		return id;
	}


}
