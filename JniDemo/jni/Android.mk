LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := demo-jni
LOCAL_SRC_FILES := demo-jni.c

LOCAL_LDLIBS    := -llog

include $(BUILD_SHARED_LIBRARY)