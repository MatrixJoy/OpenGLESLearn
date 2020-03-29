//
// Created by matrixzhou on 2020/3/28.
//

#ifndef OPENGLESLEARN_TXTUREPROGRAM_H
#define OPENGLESLEARN_TXTUREPROGRAM_H

#include "gles/include/GLUtil.h"
#include "GLES3/gl3.h"

const char vertexShader[] = "uniform mat4 uMVPMatrix;\n"
                            "uniform mat4 uTexMatrix;\n"
                            "attribute vec4 aPosition;\n"
                            "attribute vec4 aTextureCoord;\n"
                            "varying vec2 vTextureCoord;\n"
                            "void main() {\n"
                            "gl_Position = uMVPMatrix * aPosition;\n"
                            "vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n"
                            "}\n";

const char fragShaderOes[] = "#extension GL_OES_EGL_image_external : require\n"
                             "precision mediump float;\n"
                             "varying vec2 vTextureCoord;\n"
                             "uniform samplerExternalOES sTexture;\n"
                             "void main() {\n"
                             "   vec4 tc = texture2D(sTexture, vTextureCoord);\n"
                             "  float color = tc.r * 0.3 + tc.g * 0.59 + tc.b * 0.11;\n"
                             "   gl_FragColor = vec4(color,color,color,1.0f);\n"
                             "}\n";

// 顶点坐标
const float vertexArray[] = {
        -1.0f, -1.0f, // bottom left
        1.0f, -1.0f, // bottom right
        -1.0f, 1.0f,// top left
        1.0f, 1.0f // top right
};

// 纹理坐标
const float textureVertext[] = {
        0.0f, 0.0f, //bottom left
        1.0f, 0.0f, //bottom right
        0.0f, 1.0f,//top left
        1.0f, 1.0f//top right
};

const GLfloat indentityMatrix[16] = {1, 0, 0, 0,
                                     0, 1, 0, 0,
                                     0, 0, 1, 0,
                                     0, 0, 0, 1};

class TextureProgram {
public:
    void onSurfaceCreate();

    void onSurfaceChanged(int width, int height);

    void onDrawFrame(float *textMatrix);

    const GLuint generateTexture(const GLenum textureTarget);

private:
    GLuint program;
    GLint uMvpMatrixLocation;
    GLint uTextureMatrixLocation;
    GLint aVertexLocation;
    GLint aTextureLocation;
    GLint uTextureLocation;
    GLenum textureTarget;
    GLuint textureId;

};

#endif //OPENGLESLEARN_TXTUREPROGRAM_H
