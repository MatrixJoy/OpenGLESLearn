//
// Created by matrixzhou on 2020-03-22.
//


#include "include/WindowSurface.h"

WindowSurface::WindowSurface(EglCore *_eglCore, ANativeWindow *nativeWindow,
                             bool _releaseSurface) : EGLSurfaceBase(_eglCore),
                                                     surface(nativeWindow),
                                                     releaseSurface(_releaseSurface) {
    createWindowSurface(surface);
}

void WindowSurface::release() {
    releaseEglSurface();
    if (surface) {
        if (releaseSurface) {
            ANativeWindow_release(surface);
        }
        surface = nullptr;
    }
}

void WindowSurface::recreate(EglCore *newEglCore) {
    if (surface == nullptr) {
        LOGV("not yet implemented for SurfaceTexture");
        return;
    }
    eglCore = newEglCore;
    createWindowSurface(surface);
}