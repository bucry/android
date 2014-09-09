package com.home.app.entity;

import java.util.ArrayList;
import java.util.List;

import com.home.entity.response.HomeResponse;

public class UserInfo {
	private String phoneNumber;
	private String userName;
	private int homeId;
	private String homeName;
	private int uid;
	private String password;
	private List<HomeResponse> homes = new ArrayList<HomeResponse>();

	public void addHome(HomeResponse home) {
		homes.add(home);
	}
	
	public List<HomeResponse> getHomes() {
		return homes;
	}

	public void setHomes(List<HomeResponse> homes) {
		this.homes = homes;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getHomeId() {
		return homeId;
	}

	public void setHomeId(int homeId) {
		this.homeId = homeId;
	}

	public String getHomeName() {
		return homeName;
	}

	public void setHomeName(String homeName) {
		this.homeName = homeName;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
