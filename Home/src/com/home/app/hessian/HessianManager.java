package com.home.app.hessian;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

public class HessianManager {
	
	public static final String BASE_URL = "http://www.bucry.com:8080/HomeAppServer/hessian/";
	//public static final String BASE_URL = "http://192.168.1.217:9000/HomeAppServer/hessian/";
	
	public static Object createConnect(Class<?> clazz, String serviceName) {
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setHessian2Reply(false);
			factory.setOverloadEnabled(true);
			Object obj = factory.create(clazz, BASE_URL + serviceName);
			return obj;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
