//
// Created by matrixzhou on 2020-03-22.
//
#ifndef OPENGLESLEARN_WINDOWSURFACE_H
#define OPENGLESLEARN_WINDOWSURFACE_H

#include "EglSurfaceBase.h"

class WindowSurface : public EGLSurfaceBase {
public:
    WindowSurface(EglCore *eglCore, ANativeWindow *nativeWindow, bool releaseSurface);


    void release();

    void recreate(EglCore *_eglCore);

private:
    ANativeWindow *surface;
    bool releaseSurface;
};

#endif // OPENGLESLEARN_WINDOWSURFACE_H