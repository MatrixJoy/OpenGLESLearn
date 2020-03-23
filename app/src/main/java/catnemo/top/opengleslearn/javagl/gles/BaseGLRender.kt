package catnemo.top.opengleslearn.javagl.gles

import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import catnemo.top.opengleslearn.App
import catnemo.top.opengleslearn.R
import catnemo.top.opengleslearn.javagl.graphics.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2018/12/19
 *
 */
class BaseGLRender : GLSurfaceView.Renderer {


    var mGraphics: Graphics<*>? = null
        set(value) {
            field?.release()
            field = value
        }

    private var mSurfaceWidth: Int = 0
    private var mSurfaceHeight: Int = 0

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        mSurfaceWidth = width
        mSurfaceHeight = height
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 0f)
    }

    private var mAngle = 0f
    fun setAngle(angle: Float) {
        mAngle = angle
        mGraphics?.mAngle = angle
    }

    private var mEyeX = 0f
    private var mEyeZ = 3f
    private var mEyeY = 0f

    fun setEyeXYZ(eyeX: Float, eyeY: Float, eyeZ: Float) {
        mEyeX = eyeX
        mEyeZ = eyeZ
        mEyeY = eyeY
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT) // 清屏 并赋值上 glClearColor的颜色
        val mtx = FloatArray(16)
        Matrix.setIdentityM(mtx, 0)
        mGraphics?.apply {
            if (!isInit) {
                init(this@BaseGLRender.mSurfaceWidth, this@BaseGLRender.mSurfaceHeight)
            }
        }
        if (mGraphics is Cube) {
            mGraphics?.mEyeX = mEyeX
            mGraphics?.mEyeY = mEyeY
            mGraphics?.mEyeZ = mEyeZ
        }
        mGraphics?.draw(mtx)
    }
}