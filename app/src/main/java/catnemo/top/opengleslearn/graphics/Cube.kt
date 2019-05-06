package catnemo.top.opengleslearn.graphics

import android.opengl.GLES20
import android.opengl.Matrix
import catnemo.top.opengleslearn.program.AttributeColorShaderProgram

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/26
 *
 */
class Cube : Graphics() {

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

    val trans= arrayOf(
            floatArrayOf(0.0f,0.0f,0.0f),
            floatArrayOf(0.0f,0.9f,0.6f),
            floatArrayOf(0.4f,-0.7f,0.8f),
            floatArrayOf(-0.5f,-0.9f,1.0f),
            floatArrayOf(-0.2f,1.0f,0.2f)
    )

    var mProgram = AttributeColorShaderProgram()
    override fun draw(mtx: FloatArray) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)

        mProgram.useProgram()
        putVertexArray(vertexArray)
        vertex?.setVertexAttribPointer(0, mProgram.aPositionLocation, 3, 6 * 4)
        vertex?.setVertexAttribPointer(3, mProgram.aColorLocation, 3, 6 * 4)
        val projection = FloatArray(16)
        Matrix.perspectiveM(projection, 0, 45f, mSurfaceWidth / mSurfaceHeight.toFloat(), 0.1f, 100f)
        val viewMtx = FloatArray(16)
        // 前面三个坐标表示相机位置
        Matrix.setLookAtM(viewMtx, 0, mEyeX, mEyeY, mEyeZ, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f)
        for(i in 0 until trans.size){
            Matrix.translateM(viewMtx,0,trans[i][0],trans[i][1],trans[i][2])
            Matrix.scaleM(viewMtx, 0, 0.5f, 0.5f, 0.5f)
            Matrix.multiplyMM(mtx, 0, projection, 0, viewMtx, 0)
            mProgram.setUniFormMat4(mProgram.uMatrixLocation, mtx)
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36)
        }

    }

    override fun release() {
        mProgram.release()
    }
}