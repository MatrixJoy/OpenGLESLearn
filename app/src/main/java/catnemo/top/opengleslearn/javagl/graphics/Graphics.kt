package catnemo.top.opengleslearn.javagl.graphics

import android.opengl.GLES20
import catnemo.top.opengleslearn.javagl.gles.program.BaseShaderProgram
import java.lang.RuntimeException

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
abstract class Graphics<T : BaseShaderProgram> {
    companion object {
        const val TAG = "Graphics"
        const val PER_FLOAT_BYTE = 4
    }

    open var COMPONENT_COUNT = 0
    var vertex: VertexArray? = null

    var mSurfaceWidth: Int = 0
    var mSurfaceHeight: Int = 0

    var mAngle: Float = 0f

    var mEyeX = 0f
    var mEyeY = 0f
    var mEyeZ = 0f

    var isInit = false
        private set
        get() {
            if (program == null) {
                return false
            }
            return program.isInit
        }

    abstract val program: T


    fun init(width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)

        mSurfaceWidth = width
        mSurfaceHeight = height
        program.init()
        onInited()
    }

    abstract fun onInited()

    fun putVertexArray(vertexArray: FloatArray) {
        vertex = VertexArray(vertexArray)
    }

    fun onSizeChanged(width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        mSurfaceHeight = height
        mSurfaceWidth = width
    }

    fun draw(mtx: FloatArray) {
        if (!isInit) {
            throw RuntimeException("Graphics must  call init() first")
        }
        program.useProgram()
        onPreDraw(mtx)
        program.runDrawCommand()
        drawCommand()
    }


    protected abstract fun onPreDraw(mtx: FloatArray)

    protected abstract fun drawCommand()

    fun release() {
        mAngle = 0f

        mEyeX = 0f
        mEyeY = 0f
        mEyeZ = 0f

        mSurfaceWidth = 0
        mSurfaceHeight = 0

        COMPONENT_COUNT = 0

        vertex = null

        onReleased()
    }

    protected abstract fun onReleased()
}