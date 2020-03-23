//
// Created by matrixzhou on 2020-03-22.
//

#include "include/OffScreenSurface.h"

void OffscreenSurface::release() {
    releaseEglSurface();
}