//
// Created by matrixzhou on 2020-03-22.
//

#ifndef OPENGLESLEARN_OFFSCREENSURFACE_H
#define OPENGLESLEARN_OFFSCREENSURFACE_H

#include "EglSurfaceBase.h"


class OffscreenSurface : public EGLSurfaceBase {
public:
    OffscreenSurface() = default;

    OffscreenSurface(EglCore *_eglCore, int width, int height) : EGLSurfaceBase(_eglCore) {

    };

    void release();
};

#endif //OPENGLESLEARN_OFFSCREENSURFACE_H
