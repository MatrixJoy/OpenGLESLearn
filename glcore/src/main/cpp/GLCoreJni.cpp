//
// Created by matrixzhou on 2020-03-21.
//

#include "android/native_window_jni.h"
#include "GLRender.h"
#include "memory.h"
#include "GlCoreJni.h"


typedef struct a_surface_texture {
    JavaVM *vm;
    jclass surfaceClass;
    jobject surfaceTextureObj;
} ASurfaceTexture;

ASurfaceTexture aSurfaceTexture;

GLRender *glRender;

extern "C" jboolean
Java_catnemo_top_glcore_GLCore_init(JNIEnv *env, jobject /*this*/, jobject sharcontext, jint flag,
                                    jobject surface) {
    glRender = new GLRender();

    ANativeWindow *aNativeWindow = ANativeWindow_fromSurface(env, surface);
    bool isIinit = glRender->init(sharcontext, aNativeWindow, flag);
    return static_cast<jboolean>(isIinit);
}

extern "C" void Java_catnemo_top_glcore_GLCore_release(JNIEnv *env, jobject) {

    if (glRender) {
        glRender->release();
        delete glRender;
        glRender = nullptr;
    }
    env->DeleteGlobalRef(aSurfaceTexture.surfaceTextureObj);
    env->DeleteGlobalRef(aSurfaceTexture.surfaceClass);

}

extern "C" jobject
Java_catnemo_top_glcore_GLCore_createSurfaceTexture(JNIEnv *env, jobject/*this*/) {
    jclass clz = env->FindClass("android/graphics/SurfaceTexture");
    jmethodID constructId = env->GetMethodID(clz, "<init>", "(I)V");
    GLuint textureId = glRender->getTextureProgram()->generateTexture(GL_TEXTURE_EXTERNAL_OES);
    jint tid = textureId;
    jobject obj = env->NewObject(clz, constructId, tid);
    aSurfaceTexture.surfaceTextureObj = env->NewGlobalRef(obj);
    aSurfaceTexture.surfaceClass = static_cast<jclass>(env->NewGlobalRef(clz));
    return obj;
}


JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;

    memset(&aSurfaceTexture, 0, sizeof(aSurfaceTexture));

    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    aSurfaceTexture.vm = vm;

    return JNI_VERSION_1_6;
}

extern "C" void
Java_catnemo_top_glcore_GLCore_resize(JNIEnv *env, jobject thiz, jint width, jint height) {
    glRender->resize(width, height);
}

extern "C" void
Java_catnemo_top_glcore_GLCore_performDraw(JNIEnv *env, jobject thiz) {
    jmethodID mid = env->GetMethodID(aSurfaceTexture.surfaceClass, "updateTexImage", "()V");
    env->CallVoidMethod(aSurfaceTexture.surfaceTextureObj, mid);
    jmethodID mmid = env->GetMethodID(aSurfaceTexture.surfaceClass, "getTransformMatrix",
                                      "([F)V");
    float mtx[16];
    jfloatArray array = env->NewFloatArray(16);
    env->CallVoidMethod(aSurfaceTexture.surfaceTextureObj, mmid, array);
    env->GetFloatArrayRegion(array, 0, 16, mtx);
    glRender->drawFrame(mtx);
}
