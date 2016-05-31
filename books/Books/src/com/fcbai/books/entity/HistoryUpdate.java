package com.fcbai.books.entity;

import com.fcbai.books.orm.annotation.ColumnFiled;
import com.fcbai.books.orm.annotation.DBTable;

@DBTable(name = "history_update")
public class HistoryUpdate {

	@ColumnFiled(name = "id", primaryKey = true)
	private Integer id;
	@ColumnFiled(name = "update_time")
	private String updateTime;
	@ColumnFiled(name = "add_page_no")
	private Integer addPageNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getAddPageNo() {
		return addPageNo;
	}

	public void setAddPageNo(Integer addPageNo) {
		this.addPageNo = addPageNo;
	}

}
