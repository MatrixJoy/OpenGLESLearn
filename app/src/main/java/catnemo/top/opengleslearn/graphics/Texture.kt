package catnemo.top.opengleslearn.graphics

import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLUtils
import android.opengl.Matrix
import catnemo.top.opengleslearn.program.TextureShaderProgram
import catnemo.top.opengleslearn.util.TextureHelper

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/24
 *
 */
class Texture : Graphics() {
    private val vertexArray: FloatArray = floatArrayOf(
            -1f, -1f, 0f, 0f,
            1f, -1f, 1f, 0f,
            1f, 1f, 1f, 1f,
            -1f, 1f, 0f, 1f)
    init {
        putVertexArray(vertexArray)
    }

    var mProgram = TextureShaderProgram()

    var mBitmap: Bitmap? = null

    override fun draw(mtx: FloatArray) {
        val tempMtx = FloatArray(16)
        System.arraycopy(mtx, 0, tempMtx, 0, tempMtx.size)

        mProgram.useProgram()

        val projectionMtx = FloatArray(16)
        Matrix.orthoM(projectionMtx, 0, 0f, mSurfaceWidth.toFloat(), 0f, mSurfaceHeight.toFloat(), -1f, 1f)


        val bWidth = mBitmap?.width!!
        val bHeight = mBitmap?.height!!
        var newWidth = 0f
        var newHeight = 0f

        var ratioW = 0f
        var ratioH = 0f
        if (mSurfaceWidth > mSurfaceHeight) {
            if (bWidth > bHeight) {
                mSurfaceHeight * 1.0f / bWidth
            } else {
                mSurfaceHeight * 1.0f / bHeight
            }
        } else {
            if (bWidth > bHeight) {
                ratioW = mSurfaceWidth * 1.0f / bWidth
                ratioH = mSurfaceWidth * 1.0F / bWidth
            } else {
                ratioW = mSurfaceWidth * 1.0f / bWidth
                ratioH = mSurfaceHeight * 1.0f / bHeight
            }
        }
        newHeight = Math.round(bHeight * ratioH).toFloat() / 2
        newWidth = Math.round(bWidth * ratioW).toFloat() / 2

        val viewMtx = FloatArray(16)
        Matrix.setIdentityM(viewMtx, 0)
        Matrix.translateM(viewMtx, 0, mSurfaceWidth / 2f, mSurfaceHeight / 2f, 1f)
        Matrix.rotateM(viewMtx, 0, 180f, 0f, 0f, 1.0f)
        Matrix.scaleM(viewMtx, 0, -newWidth, newHeight, 1.0f)

        Matrix.multiplyMM(tempMtx, 0, projectionMtx, 0, viewMtx, 0)
        mProgram.setUniFormMat4(mProgram.uMatrixLocation, tempMtx)

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, TextureHelper.createTexture2D())
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0)
        GLES20.glUniform1i(mProgram.uTextureLocation, 0)

        vertex?.setVertexAttribPointer(0, mProgram.aPositionLocation, 2, 4 * 4)
        vertex?.setVertexAttribPointer(2, mProgram.aTextureUVLocation, 2, 4 * 4)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
        GLES20.glDisableVertexAttribArray(mProgram.aPositionLocation)
        GLES20.glDisableVertexAttribArray(mProgram.aTextureUVLocation)
    }

    override fun release() {
        mProgram.release()
    }
}