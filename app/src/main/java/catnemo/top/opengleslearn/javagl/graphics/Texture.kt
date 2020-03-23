package catnemo.top.opengleslearn.javagl.graphics

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils
import android.opengl.Matrix
import catnemo.top.opengleslearn.App
import catnemo.top.opengleslearn.javagl.gles.program.TextureShaderProgram
import catnemo.top.opengleslearn.javagl.gles.utils.TextureHelper

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/24
 *
 */
class Texture(val resId: Int) : Graphics<TextureShaderProgram>() {
    override val program: TextureShaderProgram = TextureShaderProgram()

    private val vertexArray: FloatArray = floatArrayOf(
            -1f, -1f, 0f, 0f,
            1f, -1f, 1f, 0f,
            1f, 1f, 1f, 1f,
            -1f, 1f, 0f, 1f)

    private lateinit var mBitmap: Bitmap

    var textureId = -1

    var bWidth = 0
    var bHeight = 0
    override fun onInited() {

        putVertexArray(vertexArray)
        val options = BitmapFactory.Options()
        options.inScaled = false
        mBitmap = BitmapFactory.decodeResource(App.mContext.resources, resId, options)
        bWidth = mBitmap.width
        bHeight = mBitmap.height
    }

    override fun onPreDraw(mtx: FloatArray) {
        val tempMtx = FloatArray(16)
        System.arraycopy(mtx, 0, tempMtx, 0, tempMtx.size)

        val projectionMtx = FloatArray(16)
        Matrix.orthoM(projectionMtx, 0, 0f, mSurfaceWidth.toFloat(), 0f, mSurfaceHeight.toFloat(), -1f, 1f)

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
        program.setUniFormMat4(program.uMatrixLocation, tempMtx)

        if (textureId == -1) {
            bindTexture()
        }

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        vertex?.setVertexAttribPointer(0, program.aPositionLocation, 2, 4 * 4)
        vertex?.setVertexAttribPointer(2, program.aTextureUVLocation, 2, 4 * 4)

    }

    private fun bindTexture() {
        textureId = TextureHelper.createTexture2D()
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0)
        GLES20.glUniform1i(program.uTextureLocation, 0)

        if (!mBitmap.isRecycled) {
            mBitmap.recycle()
        }
    }

    override fun drawCommand() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
        GLES20.glDisableVertexAttribArray(program.aPositionLocation)
        GLES20.glDisableVertexAttribArray(program.aTextureUVLocation)
    }


    override fun onReleased() {
        program.release()
        if (textureId != -1) {
            GLES20.glDeleteTextures(1, IntArray(textureId), 0)
            textureId = -1
        }
    }
}