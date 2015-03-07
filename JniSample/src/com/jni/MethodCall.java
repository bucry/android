package com.jni;

import android.util.Log;

public class MethodCall {
	final String tag = "MethodCall";
	static {
		System.loadLibrary("methodcall");
	}

	public native String jniCallMethod1();

	public native String jniCallMethod2();

	public native String jniCallStaticMethod();

	public void javaMethod1() {
		Log.e(tag, "javaMethod1");
	}

	public String javaMethod2() {
		Log.e(tag, "javaMethod2");
		return "javaMethod2";
	}

	public static void javaStaticMethod(String input) {
		Log.e("MethodCall", "" + input);
	}
}
