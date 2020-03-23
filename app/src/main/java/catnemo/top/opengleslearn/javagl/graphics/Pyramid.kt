package catnemo.top.opengleslearn.javagl.graphics

import android.opengl.GLES20
import android.opengl.Matrix
import android.os.SystemClock
import catnemo.top.opengleslearn.javagl.gles.program.AttributeColorShaderProgram

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/26
 *
 */
class Pyramid : Graphics<AttributeColorShaderProgram>() {
    override val program: AttributeColorShaderProgram = AttributeColorShaderProgram()

    private val varray: FloatArray = floatArrayOf(
            // x,y,z 前面, r,g,b
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
            -0.5f, 0.8f, 0.5f, 1.0f, 0.0f, 0.0f,
            0.5f, 0.8f, 0.5f, 1.0f, 0.0f, 0.0f,

            // x,y,z 右面, r,g,b
            0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.5f, 0.8f, 0.5f, 0.0f, 1.0f, 0.0f,
            0.5f, 0.8f, -0.5f, 0.0f, 1.0f, 0.0f,

            // x,y,z 后面, r,g,b
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f,
            0.5f, 0.8f, -0.5f, 0.0f, 0.0f, 1.0f,
            -0.5f, 0.8f, -0.5f, 0.0f, 0.0f, 1.0f,

            // x,y,z 左面, r,g,b
            0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
            -0.5f, 0.8f, -0.5f, 1.0f, 1.0f, 0.0f,
            -0.5f, 0.8f, 0.5f, 1.0f, 1.0f, 0.0f,

            // x,y,z 顶部, r,g,b
            -0.5f, 0.8f, -0.5f, 1.0f, 1.0f, 1.0f,
            -0.5f, 0.8f, 0.5f, 1.0f, 1.0f, 1.0f,
            0.5f, 0.8f, 0.5f, 1.0f, 1.0f, 1.0f,

            0.5f, 0.8f, 0.5f, 1.0f, 1.0f, 1.0f,
            0.5f, 0.8f, -0.5f, 1.0f, 1.0f, 1.0f,
            -0.5f, 0.8f, -0.5f, 1.0f, 1.0f, 1.0f

    )

    override fun onInited() {
        putVertexArray(varray)
    }

    override fun onPreDraw(mtx: FloatArray) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        vertex?.setVertexAttribPointer(0, program.aPositionLocation, 3, 6 * 4)
        vertex?.setVertexAttribPointer(3, program.aColorLocation, 3, 6 * 4)
        val projection = FloatArray(16)
        Matrix.perspectiveM(projection, 0, 45f, mSurfaceWidth / mSurfaceHeight.toFloat(), 0.1f, 100f)
        val viewMtx = FloatArray(16)
        Matrix.setIdentityM(viewMtx, 0)
        Matrix.translateM(viewMtx, 0, 0f, 0f, -3f)
        Matrix.rotateM(viewMtx, 0, 45f, 0.5f, 1f, 0f)
        Matrix.scaleM(viewMtx, 0, 0.5f, 0.5f, 0.5f)
        Matrix.multiplyMM(mtx, 0, projection, 0, viewMtx, 0)
        program.setUniFormMat4(program.uMatrixLocation, mtx)
    }

    override fun drawCommand() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, varray.size / 6)
        GLES20.glDisableVertexAttribArray(program.aColorLocation)
        GLES20.glDisableVertexAttribArray(program.aPositionLocation)
        GLES20.glDisable(GLES20.GL_DEPTH_TEST)
    }

    override fun onReleased() {
        program.release()
    }
}