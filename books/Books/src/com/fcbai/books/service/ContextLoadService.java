package com.fcbai.books.service;

import com.fcbai.books.orm.LoadOrmConfigure;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ContextLoadService extends Service {

	private LoadOrmConfigure loadConfigure = new LoadOrmConfigure();
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		loadConfigure.load(this);
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public void onStart(Intent intent, int startid) {
	}

}
