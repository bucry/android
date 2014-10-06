package com.home.app;

import android.app.Application;
import android.app.Service;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.home.app.entity.LocalValues;
import com.home.app.entity.UserInfo;
import com.home.app.hessian.HessianManager;
import com.home.app.user.IUserServer;
import com.home.app.utils.ActivityManager;
import com.home.app.utils.NetUtil;
import com.home.app.utils.ObjectSerializable;
import com.home.app.utils.StringUtils;
import com.home.entity.request.LocalPositionRequest;

public class LocationApplication extends Application {
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	private String lastAddres = "nothing";
	public TextView mLocationResult,logMsg;
	public TextView trigger,exit;
	public Vibrator mVibrator;
	private UserInfo userInfo;
	private ActivityManager activityManager = null; 
	private LocalValues localValues;
	public static int mNetWorkState;
	@Override
	public void onCreate() {
		super.onCreate();
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		localValues = (LocalValues) ObjectSerializable.readObject();
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		//初始化自定义Activity管理器 
        activityManager = ActivityManager.getScreenManager(); 
        initData();
	}

	private void initData() {
		mNetWorkState = NetUtil.getNetworkState(this);
	}
	
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			LocalPositionRequest request = new LocalPositionRequest();
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			request.setUploadDate(System.currentTimeMillis());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			request.setErrorCode(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			request.setLatitude(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			request.setLongitude(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			request.setRedius(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
				request.setAddress(location.getAddrStr());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				request.setAddress(location.getAddrStr());
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				request.setOnwerUid(userInfo.getUid());
				request.setOperationers(location.getOperators());
			}
			logMsg(sb.toString());
			request.setDesc(sb.toString());
			
			if (StringUtils.hasText(location.getAddrStr())) {
				if (StringUtils.hasText(lastAddres) && !lastAddres.equals(location.getAddrStr())) {
					lastAddres = location.getAddrStr();
					new AsyncAndGetUser().execute(request);
					Log.i("BaiduLocationApiDem", sb.toString());
				} else {
					System.out.println("与上次位置相同，不上传");
				}
			}
		}


	}
	
	
	private class AsyncAndGetUser extends AsyncTask<LocalPositionRequest, Integer, Integer> {
		@Override
		protected Integer doInBackground(LocalPositionRequest... params) {
			try {
				IUserServer loginService=(IUserServer)HessianManager.createConnect(IUserServer.class, "IUserServer");
				loginService.uploadLocalPosition(params[0]);
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(Integer result) {
		}
	}
	
	public void logMsg(String str) {
		try {
			if (mLocationResult != null)
				mLocationResult.setText(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public ActivityManager getActivityManager() {
		return activityManager;
	}

	public void setActivityManager(ActivityManager activityManager) {
		this.activityManager = activityManager;
	}

	public void setLocalValues(LocalValues localValues) {
		this.localValues = localValues;
	}

	public LocalValues getLocalValues() {
		if (localValues == null) {
			localValues = new LocalValues();
		}
		return localValues;
	}
}
