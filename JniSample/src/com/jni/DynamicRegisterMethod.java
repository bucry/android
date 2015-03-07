package com.jni;

public class DynamicRegisterMethod {
	static {
		System.loadLibrary("dynamicregistermethod");
	}

	public native String dynamicRegisterMethod();
}
