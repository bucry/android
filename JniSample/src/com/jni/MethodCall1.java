package com.jni;

public class MethodCall1 {
	static {
		System.loadLibrary("methodcall1");
	}

	public static int value = 0;

	public static void javaMethod() {
		value = 12;
	}

	public native int jniCalljavaMethod();
}
