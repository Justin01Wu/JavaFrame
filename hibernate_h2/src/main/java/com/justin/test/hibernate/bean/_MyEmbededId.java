package com.justin.test.hibernate.bean;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

@Embeddable
public class _MyEmbededId implements java.io.Serializable{
	
	private static final long serialVersionUID = 1324234235L;

	@Basic
	private Long categoryId;
	
	@Basic
	private Long itemId;

	public _MyEmbededId() {
	}

	public _MyEmbededId(Long categoryId, Long itemId) {
		this.categoryId = categoryId;
		this.itemId = itemId;
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof _MyEmbededId) {
			_MyEmbededId that = (_MyEmbededId) o;
			return this.categoryId.equals(that.categoryId)
					&& this.itemId.equals(that.itemId);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return categoryId.hashCode() + itemId.hashCode();
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

}
