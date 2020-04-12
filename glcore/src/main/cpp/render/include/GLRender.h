//
// Created by matrixzhou on 2020-03-25.
//

#ifndef OPENGLESLEARN_GLRENDER_H
#define OPENGLESLEARN_GLRENDER_H

#include "../../gles/include/GLUtil.h"
#include "../../gles/include/EglCore.h"
#include "../../gles/include/WindowSurface.h"
#include "TxtureProgram.h"

class GLRender {

public:
    GLRender();

    GLRender(int type);

    bool init(EGLContext sharedContext, ANativeWindow *surface, int flags);

    void resize(GLint width, GLint height);

    void drawFrame(float *mtx);

    void release();

    virtual ~GLRender();

    TextureProgram *getTextureProgram() const;

private:
    ANativeWindow *surface;
    EglCore *eglCore;
    WindowSurface *windowSurface;
    TextureProgram *textureProgram;
    TriangleProgram *triangleProgram;

    int type;
};

#endif //OPENGLESLEARN_GLRENDER_H
