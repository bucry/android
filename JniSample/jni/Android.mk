LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := helloworld
LOCAL_SRC_FILES := helloworld.cpp
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := methodcall
LOCAL_SRC_FILES := methodcall.cpp
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := methodcall1
LOCAL_SRC_FILES := methodcall.c
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := dynamicregistermethod
LOCAL_SRC_FILES := dynamicRegisterMethod.c
include $(BUILD_SHARED_LIBRARY)

