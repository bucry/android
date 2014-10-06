package com.home.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.home.app.LocationApplication;

public class LocalPositionService extends Service {

	private LocationClient mLocationClient;
	private TextView LocationResult;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor = "gcj02";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		initLBS();
	}

	private void initLBS() {
		mLocationClient = ((LocationApplication) getApplication()).mLocationClient;
		((LocationApplication) getApplication()).mLocationResult = LocationResult;
		initLocation();
		if (!mLocationClient.isStarted()) {
			mLocationClient.start();
		}
	}
	
	@Override
	public void onDestroy() {
		System.out.println("1");
	}

	@Override
	public void onStart(Intent intent, int startid) {
		initLBS();
		System.out.println("1");
	}
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);
		option.setCoorType(tempcoor);
		int span = 1000;
		try {
			span = Integer.valueOf(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		option.setScanSpan(span);
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
}
