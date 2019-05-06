package catnemo.top.opengleslearn.graphics

import android.opengl.GLES20
import catnemo.top.opengleslearn.program.AttributeColorShaderProgram

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
class ColorfulTriangle : Graphics() {

    private val POSITION_COMMENT_COUNT = 2
    private val COLOR_COMPONENT_COUNT = 3

    private val vertexArray: FloatArray = floatArrayOf(
            // x,y,r,g,b
            0.0f, 0.5f, 1f, 0.0f, 0.0f,
            -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
            0.5f, -0.5f, 0.0f, 1.0f, 1.0f)

    private val STRIDE = (POSITION_COMMENT_COUNT + COLOR_COMPONENT_COUNT) * PER_FLOAT_BYTE  // 需要跳过读取的步长


    private var mProgram = AttributeColorShaderProgram()

    override fun draw(mtx: FloatArray) {
        mProgram.useProgram()
        mProgram.setUniFormMat4(mProgram.uMatrixLocation, mtx)
        putVertexArray(vertexArray)
        vertex?.setVertexAttribPointer(0, mProgram.aPositionLocation, POSITION_COMMENT_COUNT, STRIDE)
        vertex?.setVertexAttribPointer(POSITION_COMMENT_COUNT, mProgram.aColorLocation, COLOR_COMPONENT_COUNT, STRIDE)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3)
    }

    override fun release() {
        mProgram.release()
    }
}