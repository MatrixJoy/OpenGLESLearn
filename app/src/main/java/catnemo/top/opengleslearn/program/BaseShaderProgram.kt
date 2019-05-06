package catnemo.top.opengleslearn.program

import android.opengl.GLES20
import catnemo.top.airhockey.util.ShaderHelper
import catnemo.top.airhockey.util.TextResourceReader
import catnemo.top.opengleslearn.App

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
abstract open class BaseShaderProgram(vertexShaderId: Int, fragmentShaderId: Int) {


    companion object {
        const val A_POSITION = "a_Position"
        const val U_MATRIX = "u_Matrix"
    }

    var mProgram: Int = 0

    init {
        mProgram = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(App.mContext, vertexShaderId),
                TextResourceReader.readTextFileFromResource(App.mContext, fragmentShaderId))
    }

    fun useProgram() {
        if (mProgram != 0) {
            GLES20.glUseProgram(mProgram)
        }
    }

    fun setUniFormMat4(location: Int, matrix: FloatArray) {
        GLES20.glUniformMatrix4fv(location, 1, false, matrix, 0)
    }

    fun setUniFormFloat(location: Int, value: Float) {
        GLES20.glUniform1f(location, value)
    }

    fun release() {
        if (mProgram != 0) {
            GLES20.glDeleteProgram(mProgram)
        }
    }

}