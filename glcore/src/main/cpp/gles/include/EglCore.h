//
// Created by matrixzhou on 2020-03-20.
// EGL 初始化核心类头文件
//

#ifndef OPENGLESLEARN_EGLCORE_H
#define OPENGLESLEARN_EGLCORE_H

#include "EGL/egl.h"
#include "GLES3/gl3.h"
#include "EGL/eglext.h"
#include <EGL/eglplatform.h>
#include "android/native_window.h"

typedef EGLBoolean (EGLAPIENTRYP EGL_PRESENTATION_TIME_ANDROID)(EGLDisplay dpy, EGLSurface surface,
                                                                EGLnsecsANDROID time);

const int FLAG_RECORDABLE = 0x01;
const int FLAG_TRY_GLES3 = 0x02;


class EglCore {
public:
    EglCore() = default;

    virtual ~EglCore();

    bool init(EGLContext sharedContext = nullptr, int flags = 0);

    void release();

    void releaseSurface(EGLSurface eglSurface);

    EGLSurface createWindowSurface(ANativeWindow *window);

    EGLSurface createOffscreenSurface(int width, int height);

    void makeCurrent(EGLSurface eglSurface);

    void makeCurrent(EGLSurface drawSurface, EGLSurface readSurface);

    void makeNothingCurrent();

    EGLBoolean swapBuffers(EGLSurface eglSurface);

    void setPresentationTime(EGLSurface eglSurface, long nsecs);

    bool isCurrent(EGLSurface eglSurface);

    GLint querySurface(EGLSurface eglSurface, EGLint what) const;

    const char *queryString(EGLint what);

    int getGlVersion();

    static void logCurrent(char *msg);

    EGL_PRESENTATION_TIME_ANDROID eglPresentationTimeANDROID;

private:

    EGLConfig getEglConfig(int flags, int version);

    bool checkEglError(const char *msg);

    EGLDisplay eglDisplay = EGL_NO_DISPLAY;
    EGLContext eglContext = EGL_NO_CONTEXT;
    EGLConfig eglConfig = NULL;
    EGLint eglVersion = -1;
};


#endif //OPENGLESLEARN_EGLCORE_H
