//
// Created by matrixzhou on 2020-03-24.
//

#ifndef OPENGLESLEARN_GLUTIL_H
#define OPENGLESLEARN_GLUTIL_H

#include "log.h"
#include "GLES3/gl3.h"
#include "GLES2/gl2ext.h"

/**
 * 编译顶点着色的
 * @param vertexShader  顶点着色器源码
 */
GLuint compileVertexShader(const char *vertexShader);

/**
 * 编译片源着色器
 * @param fragShader 片源着色器源码
 * @return  编译好的 片源着色器
 */
GLuint comileFragmentShader(const char *fragShader);

/**
 * 编译着色器
 * @param type 着色器类型
 * @param shader 着色器源码
 * @return 着色器id
 */
GLuint compileShader(const GLenum type, const char *shader);

/**
 * 链接着色器程序
 * @param vertexShader
 * @param fragmentShader
 * @return program id
 */
GLuint linkProgram(GLuint vertexShader, GLuint fragmentShader);

/**
 *
 * @param vertexShader
 * @param fragmentShader
 * @return 编译链接好的着色器 program
 */
GLuint buildProgram(const char *vertexShaderSource, const char *fragmentShaderSource);

/**
 * 创建纹理id
 * @param textureTarget
 * @return
 */
GLuint createTexture(const GLenum textureTarget);

#endif //OPENGLESLEARN_GLUTIL_H
