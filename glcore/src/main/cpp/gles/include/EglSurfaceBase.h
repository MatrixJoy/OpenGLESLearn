//
// Created by matrixzhou on 2020-03-22.
//

#ifndef OPENGLESLEARN_EGLSURFACEBASE_H
#define OPENGLESLEARN_EGLSURFACEBASE_H

#include "EglCore.h"
#include "log.h"


class EGLSurfaceBase {
protected:
    EglCore *eglCore;
public:
    EGLSurfaceBase() = default;

    EGLSurfaceBase(EglCore *_eglCore) : eglCore(_eglCore) {

    };

    void createWindowSurface(ANativeWindow *aNativeWindow);

    void createOffscreenSurface(EGLint width, EGLint height);

    EGLint getWidth() const;


    EGLint getHeight() const;

    void releaseEglSurface();

    void makeCurrent();

    void makeCurrentReadFrom(EGLSurfaceBase &readSurface);

    EGLBoolean swapBuffers();

    void setPresentationTime(long sect);

private:
    EGLSurface eglSurface = EGL_NO_SURFACE;
    EGLint width = -1;
    EGLint height = -1;

};

#endif //OPENGLESLEARN_EGLSURFACEBASE_H
