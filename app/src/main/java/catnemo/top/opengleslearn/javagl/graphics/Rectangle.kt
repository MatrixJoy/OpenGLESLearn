package catnemo.top.opengleslearn.javagl.graphics

import android.opengl.GLES20
import android.opengl.Matrix
import catnemo.top.opengleslearn.javagl.gles.program.BaseShaderProgram

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/25
 *
 */
class Rectangle : Graphics<BaseShaderProgram>() {

    private var vArray: FloatArray = floatArrayOf(
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f
    )

    override val program: BaseShaderProgram = BaseShaderProgram()

    override fun onInited() {
        COMPONENT_COUNT = 3
        putVertexArray(vArray)
    }

    override fun onPreDraw(mtx: FloatArray) {
        vertex?.setVertexAttribPointer(0, program.aPositionLocation, COMPONENT_COUNT, 0)
        val projection = FloatArray(16)
//        Matrix.perspectiveM(projection, 0, 45f, mSurfaceHeight / mSurfaceHeight.toFloat(), 1f, 10f)
//        val viewMtx = FloatArray(16)
//        Matrix.setIdentityM(viewMtx, 0)
//        Matrix.translateM(viewMtx, 0, 0f, 0f, -2f)
//        Matrix.rotateM(viewMtx, 0, -65f, 1f, 0f, 0f)
//        Matrix.multiplyMM(mtx, 0, projection, 0, viewMtx, 0)
        program.setUniFormMat4(program.uMatrixLocation, mtx)
        GLES20.glUniform4f(program.uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
    }

    override fun drawCommand() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vArray.size / COMPONENT_COUNT)

        GLES20.glDisableVertexAttribArray(program.aPositionLocation)
    }


    override fun onReleased() {
        program.release()
    }
}