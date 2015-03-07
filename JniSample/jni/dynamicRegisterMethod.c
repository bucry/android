#include <string.h>
#include <jni.h>

#ifndef _Included_org_spring_SpringUtils
#define _Included_org_spring_SpringUtils

jstring JNICALL java_dynamicRegisterMethod(JNIEnv * env, jobject obj) {
	return (*env)->NewStringUTF(env, "dynamicRegisterMethod");
}

static JNINativeMethod gmethods[] = { { "dynamicRegisterMethod",
		"()Ljava/lang/String;", (void*) java_dynamicRegisterMethod } };

static int registerNativeMethods(JNIEnv * env, const char* className,
		JNINativeMethod* gMethods, int numMethods) {
	jclass clazz;
	clazz = (*env)->FindClass(env, className);
	if (clazz == NULL)
		return JNI_FALSE;
	if (((*env)->RegisterNatives(env, clazz, gMethods, numMethods) < 0)) {
		return JNI_FALSE;
	}
	return JNI_TRUE;
}

static int registerNatives(JNIEnv* env) {
	if (!registerNativeMethods(env, "com/jni/DynamicRegisterMethod", gmethods,
			sizeof(gmethods) / sizeof(gmethods[0]))) {
		return JNI_FALSE;
	}
	return JNI_TRUE;
}

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
	jint result = -1;
	JNIEnv* env = NULL;
	if ((*vm)->GetEnv(vm, (void **) &env, JNI_VERSION_1_4)) {
		goto fail;
	}
	if (registerNatives(env) != JNI_TRUE) {
		goto fail;
	}
	result = JNI_VERSION_1_4;
	fail: return result;
}

#endif
