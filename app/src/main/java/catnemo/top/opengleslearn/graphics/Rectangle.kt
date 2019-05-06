package catnemo.top.opengleslearn.graphics

import android.opengl.GLES20
import android.opengl.Matrix
import catnemo.top.opengleslearn.program.UniformColorShaderProgram

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/25
 *
 */
class Rectangle : Graphics() {
    private var vArray: FloatArray = floatArrayOf(
            -0.4f, -0.6f, 0.0f,
            0.4f, -0.6f, 0.0f,
            0.4f, 0.6f, 0.0f,
            -0.4f, 0.6f, 0.0f
    )

    init {
        putVertexArray(vArray)
    }

    var mProgram = UniformColorShaderProgram()

    override fun draw(mtx: FloatArray) {
        mProgram.useProgram()

        vertex?.setVertexAttribPointer(0, mProgram.aPositionLocation, 3, 0)
        val projection = FloatArray(16)
        Matrix.perspectiveM(projection, 0, 45f, mSurfaceWidth / mSurfaceHeight.toFloat(), 1f, 10f)
        val viewMtx = FloatArray(16)
        Matrix.setIdentityM(viewMtx, 0)
        Matrix.translateM(viewMtx, 0, 0f, 0f, -2f)
//        Matrix.rotateM(viewMtx, 0, -60f, 1f, 0f, 0f)
        Matrix.multiplyMM(mtx, 0, projection, 0, viewMtx, 0)
        mProgram.setUniFormMat4(mProgram.uMatrixLocation, mtx)
        GLES20.glUniform4f(mProgram.uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)
    }

    override fun release() {
        mProgram.release()
    }
}