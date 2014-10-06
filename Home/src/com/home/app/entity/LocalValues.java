package com.home.app.entity;

import java.io.Serializable;
import java.util.List;

import com.home.entity.response.HomeResponse;

/**
 * 本地缓存
 * 
 * @author root
 * 
 */
public class LocalValues implements Serializable {

	private static final long serialVersionUID = -8220382064263503703L;

	private int uid;
	private String phoneNumber;
	private String nickName;
	private String password;
	private List<HomeResponse> homeList;
	private boolean isReadIntroduce;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getNickName() {
		return nickName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<HomeResponse> getHomeList() {
		return homeList;
	}

	public void setHomeList(List<HomeResponse> homeList) {
		this.homeList = homeList;
	}

	public boolean isReadIntroduce() {
		return isReadIntroduce;
	}

	public void setReadIntroduce(boolean isReadIntroduce) {
		this.isReadIntroduce = isReadIntroduce;
	}

}
