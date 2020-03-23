//
// Created by matrixzhou on 2020-03-21.
//

#ifndef OPENGLESLEARN_LOG_H
#define OPENGLESLEARN_LOG_H

#include "android/log.h"

#define DEBUG 1
#define LOG_TAG "GLCORE"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#if DEBUG
#define LOGV(...)  __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)
#else
#define LOGV(...)
#endif

#endif //OPENGLESLEARN_LOG_H
