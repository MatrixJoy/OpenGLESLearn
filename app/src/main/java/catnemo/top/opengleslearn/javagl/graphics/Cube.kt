package catnemo.top.opengleslearn.javagl.graphics

import android.opengl.GLES20
import android.opengl.Matrix
import catnemo.top.opengleslearn.javagl.gles.program.AttributeColorShaderProgram

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/26
 *
 */
class Cube : Graphics<AttributeColorShaderProgram>() {
    override val program: AttributeColorShaderProgram = AttributeColorShaderProgram()


    private val vertexArray: FloatArray = floatArrayOf(
            // x,y,z 前面1, r,g,b
            -0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,

            // x,y,z 前面2
            -0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,

            // x,y,z 右面1
            0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,

            // x,y,z 右面2
            0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
            0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,

            // x,y,z 后面1
            0.5f, 0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
            // x,y,z 后面2
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
            0.5f, 0.5f, -0.5f, 0.0f, 0.0f, 1.0f,

            // x,y,z 左面1
            -0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, 1.0f, 1.0f, 0.0f,

            // x,y,z 左面2
            -0.5f, -0.5f, 0.5f, 1.0f, 1.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.0f,

            // x,y,z 上面1
            -0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 1.0f,
            -0.5f, 0.5f, 0.5f, 1.0f, .0f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f, .0f, 1.0f,

            // x,y,z 上面2
            0.5f, 0.5f, 0.5f, 1.0f, .0f, 1.0f,
            0.5f, 0.5f, -0.5f, 1.0f, .0f, 1.0f,
            -0.5f, 0.5f, -0.5f, 1.0f, .0f, 1.0f,

            // x,y,z 下面1
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 1.0f, 1.0f,
            0.5f, -0.5f, 0.5f, 0.0f, 1.0f, 1.0f,

            // x,y,z 下面2
            0.5f, -0.5f, 0.5f, 0.0f, 1.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 1.0f

    )

    private val trans = arrayOf(
            floatArrayOf(0.0f, 0.0f, 0.0f),
            floatArrayOf(0.0f, 0.9f, 0.6f),
            floatArrayOf(0.4f, -0.7f, 0.8f),
            floatArrayOf(-0.5f, -0.9f, 1.0f),
            floatArrayOf(-0.2f, 1.0f, 0.2f)
    )

    override fun onInited() {
        putVertexArray(vertexArray)
    }

    override fun onPreDraw(mtx: FloatArray) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)

        vertex?.setVertexAttribPointer(0, program.aPositionLocation, 3, 6 * 4)
        vertex?.setVertexAttribPointer(3, program.aColorLocation, 3, 6 * 4)
        val projection = FloatArray(16)
        Matrix.perspectiveM(projection, 0, 45f, mSurfaceWidth / mSurfaceHeight.toFloat(), 0.1f, 100f)
        val viewMtx = FloatArray(16)
        // 前面三个坐标表示相机位置
        Matrix.setLookAtM(viewMtx, 0, mEyeX, mEyeY, mEyeZ, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f)
//        for (i in trans.indices) {
            Matrix.translateM(viewMtx, 0, trans[0][0], trans[0][1], trans[0][2])
            Matrix.scaleM(viewMtx, 0, 0.5f, 0.5f, 0.5f)
            Matrix.multiplyMM(mtx, 0, projection, 0, viewMtx, 0)
            program.setUniFormMat4(program.uMatrixLocation, mtx)
//        }
    }

    override fun drawCommand() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36)
        GLES20.glDisableVertexAttribArray(program.aColorLocation)
        GLES20.glDisableVertexAttribArray(program.aPositionLocation)
        GLES20.glDisable(GLES20.GL_DEPTH_TEST)
    }



    override fun onReleased() {
        program.release()
    }
}