package catnemo.top.opengleslearn

import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import catnemo.top.opengleslearn.graphics.*
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


    var mGraphics: Graphics? = null
    var mShape: Int = -1

    private var mSurfaceWith: Int = 0
    private var mSurfaceHeight: Int = 0

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        mSurfaceWith = width
        mSurfaceHeight = height
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 0f)
    }

    private var mAngle = 0f
    fun setAngle(angle: Float) {
        mAngle = angle
    }

    private var mEyeX =0f
    private var mEyeZ = 3f
    private var mEyeY = 0f

    fun setEyeXYZ(eyeX: Float,eyeY:Float, eyeZ: Float) {
        mEyeX = eyeX
        mEyeZ = eyeZ
        mEyeY = eyeY;
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT) // 清屏 并赋值上 glClearColor的颜色
        val mtx = FloatArray(16)
        Matrix.setIdentityM(mtx, 0)
        mGraphics?.release()
        when (mShape) {
            0 -> {
                mGraphics = Triangle()
            }
            1 -> {
                mGraphics = ColorfulTriangle()
            }
            2 -> {
                mGraphics = Texture()
                val options = BitmapFactory.Options()
                options.inScaled = false
                val bitmap = BitmapFactory.decodeResource(App.mContext.resources, R.drawable.test, options)
                (mGraphics as Texture).mBitmap = bitmap
            }
            3 -> {
                mGraphics = Texture()
                mGraphics?.mSurfaceWidth = mSurfaceWith
                mGraphics?.mSurfaceHeight = mSurfaceHeight
                val options = BitmapFactory.Options()
                options.inScaled = false
                val bitmap = BitmapFactory.decodeResource(App.mContext.resources, R.drawable.s, options)
                (mGraphics as Texture).mBitmap = bitmap
                mGraphics?.draw(mtx)
                mGraphics = Triangle()
                mGraphics?.mSurfaceWidth = mSurfaceWith
                mGraphics?.mSurfaceHeight = mSurfaceHeight
                mGraphics?.draw(mtx)
            }
            4 -> {
                mGraphics = Pentagram()
            }
            5 -> {
                mGraphics = Circle(0.5f, 32)
            }
            6 -> {
                mGraphics = Rectangle()
            }
            7 -> {
                mGraphics = Cube()
                mGraphics?.mEyeX = mEyeX
                mGraphics?.mEyeZ = mEyeZ
                mGraphics?.mEyeY= mEyeY
            }
            8 -> {
                mGraphics = Pyramid()
            }
            9 -> {
                mGraphics = Cone(0.5f, -0.8f, 32)
            }
            10 -> {
                mGraphics = Cylinder(0.5f, -0.8f, 32)
            }
        }
        if (mShape != 3) {
            mGraphics?.mSurfaceWidth = mSurfaceWith
            mGraphics?.mSurfaceHeight = mSurfaceHeight
            mGraphics?.draw(mtx)
        }
    }
}