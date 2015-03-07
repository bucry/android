package com.jni;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	final String tag = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.helloworld:
			HelloWorld hw = new HelloWorld();
			Log.e(tag, "" + hw.helloworld());
			break;
		case R.id.jnicallmethod1:
			MethodCall mc = new MethodCall();
			mc.jniCallMethod1();
			break;
		case R.id.jnicallmethod2:
			mc = new MethodCall();
			mc.jniCallMethod2();
			break;
		case R.id.jnicallstaticmethod:
			mc = new MethodCall();
			mc.jniCallStaticMethod();
			break;
		case R.id.jnicallstaticmethod_c:
			MethodCall1 mc1 = new MethodCall1();
			Log.e(tag, MethodCall1.value + "->" + mc1.jniCalljavaMethod());
			break;
		case R.id.dynamicregistermethod:
			DynamicRegisterMethod drm = new DynamicRegisterMethod();
			Log.e(tag, drm.dynamicRegisterMethod());
			break;
		}
	}
}
