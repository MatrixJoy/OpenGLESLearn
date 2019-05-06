package catnemo.top.opengleslearn.program

import android.opengl.GLES20
import catnemo.top.opengleslearn.R

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/24
 *
 */
class TextureShaderProgram : BaseShaderProgram(R.raw.texture_vertex_shader, R.raw.texture_fragment_shader) {

    var aPositionLocation: Int = 0
    var uMatrixLocation: Int = 0
    var aTextureUVLocation = 0
    var uTextureLocation = 0

    init {
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, "a_Position")
        aTextureUVLocation = GLES20.glGetAttribLocation(mProgram, "a_uv")
        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
        uTextureLocation = GLES20.glGetUniformLocation(mProgram, "texture")
    }
}