package com.home.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.home.app.LocationApplication;
import com.home.app.MainActivity;
import com.home.app.R;

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

	@SuppressWarnings("deprecation")
	private void initLBS() {
		mLocationClient = ((LocationApplication) getApplication()).mLocationClient;
		((LocationApplication) getApplication()).mLocationResult = LocationResult;
		initLocation();
		if (!mLocationClient.isStarted()) {
			mLocationClient.start();
		}
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
        
        int icon = R.drawable.icon;
        CharSequence tickerText = "安全宝正在运行";
        long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
        Context context = getApplicationContext();
        CharSequence contentTitle = "脚印正在运行";
        CharSequence contentText = "脚印正在运行";
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText,  contentIntent);
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(1, notification);
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
