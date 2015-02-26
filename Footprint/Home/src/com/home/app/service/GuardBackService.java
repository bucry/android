package com.home.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 后台守护进程
 * @author root
 *
 */
public class GuardBackService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	public void onCreate() {
		
	}
	
	@Override
	public void onDestroy() {
		//Toast.makeText(this, "My Service Stoped", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStart(Intent intent, int startid) {
		//Toast.makeText(this, "My Service Start", Toast.LENGTH_LONG).show();
	}

}
