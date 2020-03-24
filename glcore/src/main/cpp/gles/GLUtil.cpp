//
// Created by matrixzhou on 2020-03-24.
//

#include <malloc.h>
#include "include/GLUtil.h"


GLuint compileVertexShader(const char *vertexShader) {
    return compileShader(GL_VERTEX_SHADER, vertexShader);
}

GLuint comileFragmentShader(const char *fragShader) {
    return compileShader(GL_FRAGMENT_SHADER, fragShader);
}

GLuint compileShader(const GLenum type, const char *shaderSource) {
    GLuint shader;
    // 创建shader 指针
    shader = glCreateShader(type);
    if (shader == 0) {
        LOGE("glCreateShader FAIL");
        return 0;
    }
    // 加载shader源文件
    glShaderSource(shader, 1, &shaderSource, nullptr);
    // 编译shader
    glCompileShader(shader);

    GLint compiled;
    // 获取编译状态
    glGetShaderiv(shader, GL_COMPILE_STATUS, &compiled);
    if (!compiled) {
        GLint infoLen = 0;
        glGetShaderiv(shader, GL_INFO_LOG_LENGTH, &infoLen);
        if (infoLen > 1) {
            char *infoLog = static_cast<char *>(malloc(sizeof(char) * infoLen));
            // 获取编译日志
            glGetShaderInfoLog(shader, infoLen, nullptr, infoLog);
            LOGE("%s", infoLog);
            free(infoLog);
        }
        // 删除shader
        glDeleteShader(shader);
        return 0;
    }
    return shader;
}

GLuint linkProgram(GLuint vertexShader, GLuint fragmentShader) {
    GLuint program;
    // 创建一个作色器程序
    program = glCreateProgram();
    if (program == 0) {
        LOGV("glCreateProgram failed");
        return 0;
    }
    // 绑定着色器语言
    glAttachShader(program, vertexShader);

    glAttachShader(program, fragmentShader);

    // 链接着色器程序
    glLinkProgram(program);

    GLint linked;
    // 检查链接错误
    glGetProgramiv(program, GL_LINK_STATUS, &linked);
    if (!linked) {
        GLint infoLen = 0;
        glGetProgramiv(program, GL_INFO_LOG_LENGTH, &infoLen);

        if (infoLen > 1) {
            char *infoLog = static_cast<char *>(malloc(sizeof(char) * infoLen));
            glGetProgramInfoLog(program, infoLen, nullptr, infoLog);
            LOGE("%s", infoLog);
            free(infoLog);
        }
        glDeleteProgram(program);
        return 0;
    }
    return program;
}

GLuint bulidProgram(const char *vertexShaderSource, const char *fragmentShaderSource) {
    GLuint program;
    GLuint vertexShader = compileVertexShader(vertexShaderSource);
    GLuint frgamnetShader = comileFragmentShader(fragmentShaderSource);
    program = linkProgram(vertexShader, frgamnetShader);
    return program;
}
