package catnemo.top.opengleslearn.program

import android.opengl.GLES20
import catnemo.top.opengleslearn.R

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
class AttributeColorShaderProgram : BaseShaderProgram(R.raw.color_vertex_shader, R.raw.color_fragment_shader) {

    var aPositionLocation: Int = 0
    var aColorLocation = -1
    var uMatrixLocation = -1

    init {
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION)
        aColorLocation = GLES20.glGetAttribLocation(mProgram, "a_Color")
        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX)

    }
}