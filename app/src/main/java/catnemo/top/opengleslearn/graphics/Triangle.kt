package catnemo.top.opengleslearn.graphics

import android.opengl.GLES20
import android.opengl.Matrix
import catnemo.top.opengleslearn.program.UniformColorShaderProgram

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
class Triangle : Graphics() {
    private val vertexArray: FloatArray = floatArrayOf(
            0.0f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f)

    var program: UniformColorShaderProgram

    init {
        COMPONENT_COUNT = 2
        program = UniformColorShaderProgram()
        putVertexArray(vertexArray)
    }

    override fun draw(mtx: FloatArray) {
        program.useProgram()
        val projection = FloatArray(16)
        val ratio = if (mSurfaceWidth > mSurfaceHeight) {
            mSurfaceWidth * 1.0f / mSurfaceHeight
        } else {
            mSurfaceHeight * 1.0f / mSurfaceWidth
        }
        if (mSurfaceWidth > mSurfaceHeight) {
            Matrix.orthoM(projection, 0, -ratio, ratio, -1f, 1f, -1f, 1f)
        } else {
            Matrix.orthoM(projection, 0, -1f, 1f, -ratio, ratio, -1f, 1f)
        }
        Matrix.multiplyMM(mtx, 0, projection, 0, mtx, 0)
        program.setUniFormMat4(program.uMatrixLocation, mtx)
        vertex?.setVertexAttribPointer(0, program.aPositionLocation, 2, 2 * 4)
        GLES20.glUniform4f(program.uColorLocation, 1.0f, 1.0f, 0.0f, 0.0f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexArray.size)
    }

    override fun release() {
        program.release()
    }
}