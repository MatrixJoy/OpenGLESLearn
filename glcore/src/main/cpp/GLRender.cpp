//
// Created by matrixzhou on 2020-03-25.
//

#include "GLRender.h"

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

    program = bulidProgram(vertexShader, fragmentShader);
    positionLocation = glGetAttribLocation(program, "a_Position");

    return isInit;
}

void GLRender::resize(GLint width, GLint height) {
    glViewport(0, 0, width, height);
}

void GLRender::drawFrame() {

    glViewport(0, 0, windowSurface->getWidth(), windowSurface->getHeight());

    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    glClear(GL_COLOR_BUFFER_BIT);

    glUseProgram(program);

    glVertexAttribPointer(positionLocation, 2, GL_FLOAT, GL_FALSE, 0, vertextArray);

    glEnableVertexAttribArray(positionLocation);

    glDrawArrays(GL_TRIANGLES, 0, 3);

    glDisableVertexAttribArray(positionLocation);

    windowSurface->swapBuffers();
}


