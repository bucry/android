#include <string.h>
#include <jni.h>

jint Java_com_jni_MethodCall1_jniCalljavaMethod(JNIEnv* env, jobject thiz)
//env:当前该线程的内容,包含线程全部的东西;thiz:当前类的实例,指.java文件的内容
{
	jint si;
	jfieldID fid; // 一个字段,实际上对应java类里面的一个字段或属性;
	jclass cls = (*env)->GetObjectClass(env, thiz); // 类的对象实例
	jmethodID mid = (*env)->GetStaticMethodID(env, cls, "javaMethod", "()V"); // 一个方法的id
	//(I)V  (I)I
	if (mid == NULL) {
		return -1;
	}
	(*env)->CallStaticVoidMethod(env, cls, mid); //调用callback方法
	fid = (*env)->GetStaticFieldID(env, cls, "value", "I"); //取出si字段
	if (fid == NULL) {
		return -2;
	}
	si = (*env)->GetStaticIntField(env, cls, fid); //取出字段对应的值(fid字段对应的值)
	return si;
	//	return (*env)->NewStringUTF(env, "init success");
}
