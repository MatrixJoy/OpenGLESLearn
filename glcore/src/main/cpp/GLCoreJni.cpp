//
// Created by matrixzhou on 2020-03-21.
//

#include "android/native_window_jni.h"
#include "GLRender.h"

GLRender *glRender;

extern "C" jboolean
Java_catnemo_top_glcore_GLCore_init(JNIEnv *env, jobject /*this*/, jobject sharcontext, jint flag,
                                    jobject surface) {
    glRender = new GLRender();

    ANativeWindow *aNativeWindow = ANativeWindow_fromSurface(env, surface);
    bool isIinit = glRender->init(sharcontext, aNativeWindow, flag);
    if (isIinit) {
        glRender->drawFrame();
    } else {
        glRender->release();
    }
    return static_cast<jboolean>(isIinit);
}

extern "C" void Java_catnemo_top_glcore_GLCore_release(JNIEnv *, jobject) {

    if (glRender) {
        glRender->release();
        delete glRender;
        glRender = nullptr;
    }
}

