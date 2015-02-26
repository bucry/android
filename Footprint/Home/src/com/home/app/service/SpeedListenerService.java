package com.home.app.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.IBinder;

@SuppressWarnings("deprecation")
public class SpeedListenerService extends Service implements SensorListener {
	
	private SensorManager sm = null;
	private double ax = 0;
	private double ay = 0;
	private double oy = 0;
	private double oz = 0;
	private double axp = 0;
	private double ayp = 0;
	private double gp = 0;
	private double tmp1 = 0, tmp2 = 0, tmp3 = 0, tmp4 = 0;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		initScreen();
	}
	
	private void initScreen() {
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	@Override
	public void onSensorChanged(int sensor, float[] values) {
		synchronized (this) {
			if (sensor == SensorManager.SENSOR_ORIENTATION) {
				oy = values[1];
				oz = values[2];
			}
			if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
				ax = values[0];
				ay = values[1];
			}
			tmp1 = Math.sin(oz * Math.PI / 180.0);
			tmp2 = Math.sin(Math.abs(oy) * Math.PI / 180.0);
			tmp3 = Math.sqrt(tmp1 * tmp1 + tmp2 * tmp2);
			tmp4 = tmp1 / tmp3;

			gp = 10 * tmp3;
			axp = ax * Math.cos(tmp4) + ay * Math.sin(tmp4);
			ayp = -ax * Math.sin(tmp4) + ay * Math.cos(tmp4) + gp;
			System.out.println("X加速度: " + axp);
			System.out.println("Y加速度: " + ayp);
			//System.out.println("Orientation : " + getOrientation());
		}
	}
	
	@Override
	public void onAccuracyChanged(int sensor, int accuracy) {
		
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		sm.registerListener(this, SensorManager.SENSOR_ORIENTATION | SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onDestroy() {
		sm.unregisterListener(this);
		super.onDestroy();
	}

	public double getOrientation() {
		return Math.asin(tmp4) / Math.PI * 180.0;
	}
}
