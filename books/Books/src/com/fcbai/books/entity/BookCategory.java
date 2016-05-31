package com.fcbai.books.entity;

import java.io.Serializable;

public class BookCategory implements Serializable {

	private static final long serialVersionUID = -5251450178755180358L;

	private int id;
	private String categoryName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
