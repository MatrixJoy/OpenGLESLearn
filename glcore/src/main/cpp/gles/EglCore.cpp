//
// Created by matrixzhou on 2020-03-20.
//

#include "EglCore.h"

#include <stdexcept>
#include <iostream>
#include "sstream"
#include "./include/log.h"

bool EglCore::init(EGLContext sharedContext, int flags) {
    if (eglDisplay != EGL_NO_DISPLAY) {
        LOGE("EGL already set up");
        return false;
    }

    if (sharedContext == nullptr) {
        sharedContext = EGL_NO_CONTEXT;
    }

    // 建立显示连接
    eglDisplay = eglGetDisplay(EGL_DEFAULT_DISPLAY);

    if (eglDisplay == EGL_NO_DISPLAY) {
        LOGE("unable get EGL display");
        return false;
    }

    // 主版本号
    EGLint majorVersion;

    // 次版本号
    EGLint minorVersion;

    // 初始化 egl
    if (!eglInitialize(eglDisplay, &majorVersion, &minorVersion)) {
        eglDisplay = nullptr;
        LOGE("unable to initialize EGL");
        return false;
    }
    if ((flags & FLAG_TRY_GLES3)) {
        EGLConfig config = getEglConfig(flags, 3);
        if (config != nullptr) {
            EGLint attrib3_list[] = {
                    EGL_CONTEXT_CLIENT_VERSION, 3,
                    EGL_NONE
            };
            // 创建一个渲染上下文
            EGLContext _eglContext = eglCreateContext(eglDisplay, config, sharedContext,
                                                      attrib3_list);
            if (eglGetError() == EGL_SUCCESS) {
                eglConfig = config;
                eglContext = _eglContext;
                eglVersion = 3;
            }
        }
    }

    // 如果egl3 创建失败 或者指定了 egl2
    if (eglContext == EGL_NO_CONTEXT) {
        EGLConfig config = getEglConfig(flags, 2);
        if (config == nullptr) {
            LOGE("Unable to find a suitable EGLConfig");
            return false;
        }

        EGLint attrib2_list[] = {
                EGL_CONTEXT_CLIENT_VERSION, 2,
                EGL_NONE
        };
        EGLContext _eglContext = eglCreateContext(eglDisplay, config, sharedContext, attrib2_list);

        if (!checkEglError("eglCreateContext")) {
            return false;
        }
        eglConfig = config;
        eglContext = _eglContext;
        eglVersion = 2;
    }
    EGLint value;
    eglQueryContext(eglDisplay, eglContext, EGL_CONTEXT_CLIENT_VERSION, &value);
    LOGV("EGLContext created, client version %d", value);
    return true;
}

/**
 * 获取创建egl 相关的表面配置信息
 * @param flags 标签
 * @param version  版本号  2 或者 3
 * @return 创建好的egl配置信息
 */
EGLConfig EglCore::getEglConfig(int flags, int version) {
    EGLint renderableType = EGL_OPENGL_ES2_BIT;

    // 版本大于3 就选择为gles 3
    if (version >= 3) {
        renderableType |= EGL_OPENGL_ES3_BIT_KHR;
    }

    EGLint attribList[] = {
            EGL_RED_SIZE, 8,
            EGL_GREEN_SIZE, 8,
            EGL_BLUE_SIZE, 8,
            EGL_ALPHA_SIZE, 8,
            EGL_RENDERABLE_TYPE, renderableType,
            EGL_NONE, 0, // placeholder for recordable [@-3]
            EGL_NONE
    };
    int length = sizeof(attribList) / sizeof(*attribList);

    // 如果需要硬编码视频 则开启 下列的config

    if (flags & FLAG_RECORDABLE) {
        attribList[length - 3] = EGL_RECORDABLE_ANDROID;
        attribList[length - 2] = 1;
    }
    EGLConfig _eglConfig;
    EGLint numConfigs;
    if (!eglChooseConfig(eglDisplay, attribList, &_eglConfig, 1, &numConfigs)) {
        LOGE("unable to find RGB8888 / %d EGLConfig", version);
        return nullptr;
    }
    return _eglConfig;
}


bool EglCore::checkEglError(const char *msg) {
    EGLint error = eglGetError();
    if (error != EGL_SUCCESS) {
        LOGE("GL error after %s(): 0x%08x\n", msg, error);
        return false;
    }
    return true;
}

void EglCore::release() {
    if (eglDisplay != EGL_NO_DISPLAY) {
        eglMakeCurrent(eglDisplay, EGL_NO_SURFACE, EGL_NO_SURFACE, EGL_NO_CONTEXT);
        eglDestroyContext(eglDisplay, eglContext);
        eglReleaseThread();
        eglTerminate(eglDisplay);
    }
    eglDisplay = EGL_NO_DISPLAY;
    eglContext = EGL_NO_CONTEXT;
    eglConfig = NULL;
}

EglCore::~EglCore() {
    release();
}

void EglCore::releaseSurface(EGLSurface eglSurface) {
    eglDestroySurface(eglDisplay, eglSurface);
}


/**
 * 创建上屏的surface
 * @param window
 * @return
 */
EGLSurface EglCore::createWindowSurface(ANativeWindow *window) {

    EGLint surfaceAttribs[] = {
            EGL_NONE
    };
    EGLSurface eglSurface = eglCreateWindowSurface(eglDisplay, eglConfig, window, surfaceAttribs);
    if (eglSurface == nullptr) {
        LOGE("eglSurface is nullptr");
        return nullptr;
    }
    return eglSurface;
}

/**
 * 创建离屏渲染 surface
 * @param width
 * @param height
 * @return
 */
EGLSurface EglCore::createOffscreenSurface(int width, int height) {

    EGLint surfaceAttribs[] = {
            EGL_WIDTH, width,
            EGL_HEIGHT, height,
            EGL_NONE
    };

    EGLSurface eglSurface = eglCreatePbufferSurface(eglDisplay, eglConfig, surfaceAttribs);

    if (eglSurface == nullptr) {
        LOGE("eglSurface is nullptr");
        return nullptr;
    }
    return eglSurface;
}

/**
 * Makes our EGL context current, using the supplied surface for both "draw" and "read".
 * @param eglSurface
 */
void EglCore::makeCurrent(EGLSurface eglSurface) {
    if (eglDisplay == EGL_NO_DISPLAY) {
        LOGV("NOTE: makeCurrent w/o display");
    }
    if (!eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)) {
        LOGE("eglMakeCurrent failed");
    }
}

/**
 * Makes our EGL context current, using the supplied surface for both "draw" and "read".
 * @param drawSurface
 * @param readSurface
 *
 */
void EglCore::makeCurrent(EGLSurface drawSurface, EGLSurface readSurface) {
    if (eglDisplay == EGL_NO_DISPLAY) {
        LOGV("NOTE: makeCurrent w/o display");
    }
    if (!eglMakeCurrent(eglDisplay, drawSurface, readSurface, eglContext)) {
        LOGE("eglMakeCurrent failed");
    }
}

/**
 *
 * Makes no context current.
 */
void EglCore::makeNothingCurrent() {
    if (!eglMakeCurrent(eglDisplay, EGL_NO_SURFACE, EGL_NO_SURFACE, EGL_NO_CONTEXT)) {
        LOGE("eglMakeCurrent failed");
    }
}

/**
* Calls eglSwapBuffers.  Use this to "publish" the current frame.
*
* @return false on failure
*/
EGLBoolean EglCore::swapBuffers(EGLSurface eglSurface) {
    return eglSwapBuffers(eglDisplay, eglSurface);
}


void EglCore::setPresentationTime(EGLSurface eglSurface, long nsecs) {
    eglPresentationTimeANDROID(eglDisplay, eglSurface, nsecs);
}

bool EglCore::isCurrent(EGLSurface eglSurface) {
    return eglContext == eglGetCurrentContext() && eglSurface == eglGetCurrentSurface(EGL_DRAW);
}

GLint EglCore::querySurface(EGLSurface eglSurface, EGLint what) const {
    int value;
    eglQuerySurface(eglDisplay, eglSurface, what, &value);
    return value;
}

const char *EglCore::queryString(EGLint what) {
    return eglQueryString(eglDisplay, what);
}

int EglCore::getGlVersion() {
    return eglVersion;
}

void EglCore::logCurrent(char *msg) {
    EGLDisplay display;
    EGLContext context;
    EGLSurface surface;
    display = eglGetCurrentDisplay();
    context = eglGetCurrentContext();
    surface = eglGetCurrentSurface(EGL_DRAW);
    LOGV("Current EGL (%s): display=%p, context=%p,surface=%p", msg, display, context, surface);
}




