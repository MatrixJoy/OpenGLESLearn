package catnemo.top.opengleslearn.javagl.graphics

import android.opengl.GLES20
import catnemo.top.opengleslearn.javagl.gles.program.AttributeColorShaderProgram

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
class ColorfulTriangle : Graphics<AttributeColorShaderProgram>() {
    override val program: AttributeColorShaderProgram = AttributeColorShaderProgram()



    private val POSITION_COMMENT_COUNT = 2
    private val COLOR_COMPONENT_COUNT = 3

    private val vertexArray: FloatArray = floatArrayOf(
            // x,y,r,g,b
            0.0f, 0.5f, 1f, 0.0f, 0.0f,
            -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
            0.5f, -0.5f, 0.0f, 1.0f, 1.0f)

    private val STRIDE = (POSITION_COMMENT_COUNT + COLOR_COMPONENT_COUNT) * PER_FLOAT_BYTE  // 需要跳过读取的步长

    override fun onInited() {
        putVertexArray(vertexArray)
    }

    override fun onPreDraw(mtx: FloatArray) {
        program.setUniFormMat4(program.uMatrixLocation, mtx)
        vertex?.setVertexAttribPointer(0, program.aPositionLocation, POSITION_COMMENT_COUNT, STRIDE)
        vertex?.setVertexAttribPointer(POSITION_COMMENT_COUNT, program.aColorLocation, COLOR_COMPONENT_COUNT, STRIDE)
    }

    override fun drawCommand() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3)
    }


    override fun onReleased() {
        program.release()
    }
}