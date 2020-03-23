//
// Created by matrixzhou on 2020-03-22.
//

#include "include/EglSurfaceBase.h"

#include "include/log.h"

void EGLSurfaceBase::createWindowSurface(ANativeWindow *aNativeWindow) {
    if (eglSurface != EGL_NO_SURFACE) {
        LOGE("surface already create");
        return;
    }

    eglSurface = eglCore->createWindowSurface(aNativeWindow);
}

void EGLSurfaceBase::createOffscreenSurface(EGLint width, EGLint height) {
    if (eglSurface != EGL_NO_SURFACE) {
        LOGE("surface already create");
        return;
    }
    eglSurface = eglCore->createOffscreenSurface(width, height);

    this->width = width;
    this->height = height;
}

EGLint EGLSurfaceBase::getWidth() const {
    if (width < 0) {
        return eglCore->querySurface(eglSurface, EGL_WIDTH);
    }
    return width;
}


EGLint EGLSurfaceBase::getHeight() const {
    if (height < 0) {
        return eglCore->querySurface(eglSurface, EGL_HEIGHT);
    }
    return height;
}


void EGLSurfaceBase::releaseEglSurface() {
    eglCore->releaseSurface(eglSurface);
    eglSurface = EGL_NO_SURFACE;
    width = height = -1;
}

void EGLSurfaceBase::makeCurrent() {
    eglCore->makeCurrent(eglSurface);
}

void EGLSurfaceBase::makeCurrentReadFrom(EGLSurfaceBase &readSurface) {
    eglCore->makeCurrent(eglSurface, readSurface.eglSurface);
}

EGLBoolean EGLSurfaceBase::swapBuffers() {
    EGLBoolean result = eglCore->swapBuffers(eglSurface);
    if (!result) {
        LOGV("WARNING: swapBuffers() failed");
    }
    return result;
}

void EGLSurfaceBase::setPresentationTime(long sect) {
    eglCore->setPresentationTime(eglSurface, sect);
}