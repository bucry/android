#include <jni.h>
#include <android/log.h>
#include <string.h>

#ifndef _Included_com_jni_MethodCall
#define _Included_com_jni_MethodCall

#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jstring JNICALL Java_com_jni_MethodCall_jniCallMethod1(JNIEnv *,
		jobject);
JNIEXPORT jstring JNICALL Java_com_jni_MethodCall_jniCallMethod2(JNIEnv *,
		jobject);
JNIEXPORT jstring JNICALL Java_com_jni_MethodCall_jniCallStaticMethod(JNIEnv *,
		jobject);
#ifdef __cplusplus
}
#endif
#endif

JNIEXPORT jstring JNICALL Java_com_jni_MethodCall_jniCallMethod1(JNIEnv * env,
		jobject obj) {
	jmethodID mid; // 方法标识id
	jclass cls = env->GetObjectClass(obj); // 类的对象实例
	mid = env->GetMethodID(cls, "javaMethod1", "()V");
	env->CallVoidMethod(obj, mid);
	return env->NewStringUTF("jniCallMethod1");
}

JNIEXPORT jstring JNICALL Java_com_jni_MethodCall_jniCallMethod2(JNIEnv * env,
		jobject obj) {
	jmethodID mid; // 方法标识id
	jclass cls = env->GetObjectClass(obj); // 类的对象实例
	mid = env->GetMethodID(cls, "javaMethod2", "()Ljava/lang/String;");
	jstring js = (jstring) env->CallObjectMethod(obj, mid);
	return js;
}

JNIEXPORT jstring JNICALL Java_com_jni_MethodCall_jniCallStaticMethod(
		JNIEnv * env, jobject obj) {
	jmethodID mid; // 方法标识id
	jclass cls = env->GetObjectClass(obj); // 类的对象实例
	mid = env->GetStaticMethodID(cls, "javaStaticMethod",
			"(Ljava/lang/String;)V");
	jstring input = env->NewStringUTF("jniCallStaticMethod->>javaStaticMethod");
	env->CallStaticVoidMethod(cls, mid, input);
	return env->NewStringUTF("jniCallStaticMethod");
}
