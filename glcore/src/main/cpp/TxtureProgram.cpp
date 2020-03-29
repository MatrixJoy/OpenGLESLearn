//
// Created by matrixzhou on 2020/3/28.
//

#include "TxtureProgram.h"
#include "gles/include/GLUtil.h"

void TextureProgram::onSurfaceCreate() {
    program = buildProgram(vertexShader, fragShaderOes);
    uMvpMatrixLocation = glGetUniformLocation(program, "uMVPMatrix");
    uTextureMatrixLocation = glGetUniformLocation(program, "uTexMatrix");
    uTextureLocation = glGetUniformLocation(program, "sTexture");
    aTextureLocation = glGetAttribLocation(program, "aTextureCoord");
    aVertexLocation = glGetAttribLocation(program, "aPosition");
}

void TextureProgram::onSurfaceChanged(int width, int height) {
    glViewport(0, 0, width, height);
}

void TextureProgram::onDrawFrame(float *textMatrix) {
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    glClear(GL_COLOR_BUFFER_BIT);

    glUseProgram(program);

    glActiveTexture(GL_TEXTURE0);
    glBindTexture(textureTarget, textureId);
    glUniform1i(uTextureLocation, 0);

    glUniformMatrix4fv(uMvpMatrixLocation, 1, GL_FALSE, indentityMatrix);
    glUniformMatrix4fv(uTextureMatrixLocation, 1, GL_FALSE, textMatrix);

    glEnableVertexAttribArray(aVertexLocation);
    glVertexAttribPointer(aVertexLocation, 2, GL_FLOAT, GL_FALSE, 0, vertexArray);

    glEnableVertexAttribArray(aTextureLocation);
    glVertexAttribPointer(aTextureLocation, 2, GL_FLOAT, GL_FALSE, 0, textureVertext);
    glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

    glDisableVertexAttribArray(aVertexLocation);
    glDisableVertexAttribArray(aTextureLocation);
    glBindTexture(GL_TEXTURE_EXTERNAL_OES, 0);
    glUseProgram(0);
}

const GLuint TextureProgram::generateTexture(const GLenum textureTarget) {
    this->textureTarget = textureTarget;
    this->textureId = createTexture(textureTarget);
    return this->textureId;
}
