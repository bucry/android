package com.fcbai.books.entity;

import java.io.Serializable;

import com.fcbai.books.orm.annotation.ColumnFiled;
import com.fcbai.books.orm.annotation.DBTable;

@DBTable(name = "book")
public class Book implements Serializable {

	private static final long serialVersionUID = 5302378824018480846L;

	@ColumnFiled(name = "id", primaryKey = true)
	private Integer id;
	@ColumnFiled(name = "name")
	private String name;
	@ColumnFiled(name = "desc")
	private String desc;
	@ColumnFiled(name = "start_ate")
	private String startDate;
	@ColumnFiled(name = "start_timestrp")
	private Long startTimestrp;
	@ColumnFiled(name = "end_date")
	private String endDate;
	@ColumnFiled(name = "end_timestrp")
	private Long endTimestrp;
	@ColumnFiled(name = "is_notify")
	private Boolean isNotify;
	@ColumnFiled(name = "start_number")
	private Integer startNumber;
	@ColumnFiled(name = "type")
	private Integer type;
	@ColumnFiled(name = "current_page_no")
	private Integer currentPageNo;
	@ColumnFiled(name = "total_page_no")
	private Integer totalPageNo;
	@ColumnFiled(name = "category")
	private Integer category;
	@ColumnFiled(name = "image_path")
	private String imagePath;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Long getStartTimestrp() {
		return startTimestrp;
	}

	public void setStartTimestrp(Long startTimestrp) {
		this.startTimestrp = startTimestrp;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getEndTimestrp() {
		return endTimestrp;
	}

	public void setEndTimestrp(Long endTimestrp) {
		this.endTimestrp = endTimestrp;
	}

	public Boolean getIsNotify() {
		return isNotify;
	}

	public void setIsNotify(Boolean isNotify) {
		this.isNotify = isNotify;
	}

	public Integer getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(Integer startNumber) {
		this.startNumber = startNumber;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(Integer currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public Integer getTotalPageNo() {
		return totalPageNo;
	}

	public void setTotalPageNo(Integer totalPageNo) {
		this.totalPageNo = totalPageNo;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
