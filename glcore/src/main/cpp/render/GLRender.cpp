//
// Created by matrixzhou on 2020-03-25.
//

#include "include/GLRender.h"

GLRender::~GLRender() {
    release();
}

void GLRender::release() {
    if (windowSurface) {
        windowSurface->release();
        delete windowSurface;
        windowSurface = nullptr;
    }
    if (eglCore) {
        eglCore->release();
        delete eglCore;
        eglCore = nullptr;
    }
}

bool GLRender::init(EGLContext sharedContext, ANativeWindow *surface, int flags) {
    eglCore = new EglCore();

    bool isInit = eglCore->init(sharedContext, flags);

    if (!isInit) {
        return false;
    }

    this->surface = surface;

    windowSurface = new WindowSurface(eglCore, surface, false);

    windowSurface->makeCurrent();

    if (type == 1) {
        triangleProgram->onSurfaceCreate();
    } else {
        textureProgram->onSurfaceCreate();
    }

    return isInit;
}

void GLRender::resize(GLint width, GLint height) {
    if (type == 1) {
        triangleProgram->onSurfaceChanged(width, height);
        return;
    }
    textureProgram->onSurfaceChanged(width, height);
}

void GLRender::drawFrame(float *mtx) {
    if (type == 1) {
        triangleProgram->onDrawFrame(mtx);
    } else {
        textureProgram->onDrawFrame(mtx);
    }
    windowSurface->swapBuffers();
}

GLRender::GLRender() {
    textureProgram = new TextureProgram();
}

TextureProgram *GLRender::getTextureProgram() const {
    return textureProgram;
}

GLRender::GLRender(int type) : type(type) {
    if (type == 1) {
        triangleProgram = new TriangleProgram();
    }
}


