package catnemo.top.opengleslearn.program

import android.opengl.GLES20
import catnemo.top.opengleslearn.R
import java.nio.FloatBuffer

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
class UniformColorShaderProgram : BaseShaderProgram(R.raw.simple_vertex_shader, R.raw.simple_fragment_shader) {

    var aPositionLocation: Int = 0
    var uMatrixLocation = 0
    var uColorLocation = 0

    init {
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION)
        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX)
        uColorLocation = GLES20.glGetUniformLocation(mProgram, "u_Color")
    }
}