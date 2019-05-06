package catnemo.top.opengleslearn.util

import android.opengl.GLES20
import android.util.Log

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
object TextureHelper {
    fun createTexture2D(): Int {
        val textures = IntArray(1)
        GLES20.glGenTextures(1, textures, 0)
        if (textures[0] == 0) {
            Log.e("ZJJ", "创建纹理texture id失败")
            return 0
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)

        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
        return textures[0]
    }
}