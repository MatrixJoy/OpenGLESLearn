//
// Created by matrixzhou on 2020/4/1.
//

#include "include/TriangleProgram.h"

void TriangleProgram::onSurfaceCreate() {
    program = buildProgram(vertex_Shader, fragment_Shader);
    positionLocation = glGetAttribLocation(program, "a_Position");
}

void TriangleProgram::onSurfaceChanged(int width, int height) {
    glViewport(0, 0, width, height);
}

void TriangleProgram::onDrawFrame(float *textMatrix) {

    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    glClear(GL_COLOR_BUFFER_BIT);

    glUseProgram(program);

    glVertexAttribPointer(positionLocation, 2, GL_FLOAT, GL_FALSE, 0, vertex_Array);

    glEnableVertexAttribArray(positionLocation);

    glDrawArrays(GL_TRIANGLES, 0, 3);

    glDisableVertexAttribArray(positionLocation);
}

