LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := fileio
LOCAL_SRC_FILES := fileio.c

include $(BUILD_SHARED_LIBRARY)