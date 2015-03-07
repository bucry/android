package com.jni;

public class HelloWorld {
	static {
		System.loadLibrary("helloworld");
	}

	public native String helloworld();
}
