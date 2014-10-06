package com.home.app.server;

import com.home.app.hessian.HessianManager;
import com.home.app.user.IUserServer;

public class UserServerInterface extends ServiceName {
	
	private IUserServer userServer=(IUserServer)HessianManager.createConnect(IUserServer.class, IUSER_SERVER);
	
	private static UserServerInterface instance;
	
	public static UserServerInterface getInstance() {
		if (instance == null) {
			instance = new UserServerInterface();
		}
		return instance;
	}

	public IUserServer getUserServer() {
		return userServer;
	}
}
