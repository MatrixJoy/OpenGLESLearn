package catnemo.top.opengleslearn.javagl.graphics

import android.opengl.GLES20
import android.opengl.Matrix
import catnemo.top.opengleslearn.javagl.gles.program.BaseShaderProgram

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
class Triangle : Graphics<BaseShaderProgram>() {

    private val vertexArray: FloatArray = floatArrayOf(
            0.0f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f)

    override val program: BaseShaderProgram = BaseShaderProgram()

    override fun onInited() {
        COMPONENT_COUNT = 2
        putVertexArray(vertexArray)
    }

    override fun onPreDraw(mtx: FloatArray) {
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
        program.setUnifrom4f(program.uColorLocation, 1.0f, 1.0f, 1.0f, 0.0f)

        vertex?.setVertexAttribPointer(0, program.aPositionLocation, COMPONENT_COUNT, COMPONENT_COUNT * PER_FLOAT_BYTE)
    }

    override fun drawCommand() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexArray.size / COMPONENT_COUNT)
        GLES20.glDisableVertexAttribArray(program.aPositionLocation)
    }

    override fun onReleased() {
        program.release()
    }
}