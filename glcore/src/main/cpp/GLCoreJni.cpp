//
// Created by matrixzhou on 2020-03-21.
//
#include <WindowSurface.h>
#include "GlCoreJni.h"
#include "EglCore.h"
#include "android/native_window_jni.h"

EglCore *eglCore;
WindowSurface *windowSurface;
extern "C" jboolean
Java_catnemo_top_glcore_GLCore_init(JNIEnv *env, jobject /*this*/, jobject sharcontext, jint flag,
                                    jobject surface) {
    eglCore = new EglCore();
    bool isIinit = eglCore->init(sharcontext, flag);
    if (isIinit) {
        ANativeWindow *aNativeWindow = ANativeWindow_fromSurface(env, surface);

        windowSurface = new WindowSurface(eglCore, aNativeWindow, false);
        windowSurface->makeCurrent();
        glViewport(0, 0, windowSurface->getWidth(), windowSurface->getHeight());
        glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        windowSurface->swapBuffers();
    }
    return static_cast<jboolean>(isIinit);
}

extern "C" void Java_catnemo_top_glcore_GLCore_release(JNIEnv *, jobject) {

    if (windowSurface) {
        windowSurface->release();
        delete windowSurface;
        windowSurface = NULL;
    }

    if (eglCore) {
        eglCore->release();
        delete eglCore;
        eglCore = NULL;
    }
}

