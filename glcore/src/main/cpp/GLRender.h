//
// Created by matrixzhou on 2020-03-25.
//

#ifndef OPENGLESLEARN_GLRENDER_H
#define OPENGLESLEARN_GLRENDER_H

#include "gles/include/GLUtil.h"
#include "gles/include/EglCore.h"
#include "gles/include/WindowSurface.h"

const char vertexShader[] = "attribute vec4 a_Position;\n"
                            "void main(){\n"
                            "    // 分配最终坐标 给当前顶点\n"
                            "    gl_Position = a_Position;\n"
                            "}";
const char fragmentShader[] = "precision mediump float;\n"
                              "void main()\n"
                              "{\n"
                              "gl_FragColor=vec4(1.0,0.0,0.0,1.0);\n"
                              "}\n";
const float vertextArray[] = {
        0.0f, 0.5f,
        -0.5f, -0.5f,
        0.5f, -0.5f
};

class GLRender {

public:
    GLRender() = default;

    bool init(EGLContext sharedContext, ANativeWindow *surface, int flags);

    void resize(GLint width, GLint height);

    void drawFrame();

    void release();

    virtual ~GLRender();


private:
    ANativeWindow *surface;
    EglCore *eglCore;
    WindowSurface *windowSurface;
    GLuint program;
    GLint positionLocation;

};

#endif //OPENGLESLEARN_GLRENDER_H
