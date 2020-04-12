//
// Created by matrixzhou on 2020/3/30.
//

#ifndef OPENGLESLEARN_TRIANGLEPROGRAM_H
#define OPENGLESLEARN_TRIANGLEPROGRAM_H

#include "../../gles/include/GLUtil.h"

const char vertex_Shader[] = "attribute vec4 a_Position;\n"
                             "void main(){\n"
                             "    // 分配最终坐标 给当前顶点\n"
                             "    gl_Position = a_Position;\n"
                             "}";
const char fragment_Shader[] = "precision mediump float;\n"
                               "void main()\n"
                               "{\n"
                               "gl_FragColor=vec4(1.0,0.0,0.0,1.0);\n"
                               "}\n";
const float vertex_Array[] = {
        0.0f, 0.5f,
        -0.5f, -0.5f,
        0.5f, -0.5f
};

class TriangleProgram {
public:
    void onSurfaceCreate();

    void onSurfaceChanged(int width, int height);

    void onDrawFrame(float *textMatrix);

private:
    GLuint program;
    GLint positionLocation;
};

#endif //OPENGLESLEARN_TRIANGLEPROGRAM_H
