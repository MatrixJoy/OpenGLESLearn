package catnemo.top.opengleslearn.javagl.gles.program

import android.opengl.GLES20
import catnemo.top.airhockey.util.TextResourceReader
import catnemo.top.opengleslearn.App
import catnemo.top.opengleslearn.R
import catnemo.top.opengleslearn.javagl.gles.utils.buildProgram
import java.util.*

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
open class BaseShaderProgram {

    constructor() : this(R.raw.simple_vertex_shader, R.raw.simple_fragment_shader)


    constructor(vertexShaderId: Int, fragmentShaderId: Int) {
        this.vertexShaderId = vertexShaderId
        this.fragmentShaderId = fragmentShaderId
    }

    companion object {
        const val A_POSITION = "a_Position"
        const val U_MATRIX = "u_Matrix"
        const val U_COLOR = "u_Color"
    }

    var mProgram: Int = 0
    private var vertexShaderId = 0
    private var fragmentShaderId = 0

    var isInit = false


    var aPositionLocation: Int = 0
    var uMatrixLocation = 0
    var uColorLocation = 0

    private val runCommands = LinkedList<Runnable>()

    fun init() {
        mProgram = buildProgram(
                TextResourceReader.readTextFileFromResource(App.mContext, vertexShaderId),
                TextResourceReader.readTextFileFromResource(App.mContext, fragmentShaderId))
        onInit()
        isInit = true
    }

    open fun onInit() {
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION)
        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX)
        uColorLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR)
    }

    fun useProgram() {
        if (mProgram != 0) {
            GLES20.glUseProgram(mProgram)
        }
    }

    fun runDrawCommand() {
        synchronized(runCommands) {
            while (!runCommands.isEmpty()) {
                runCommands.pollFirst().run()
            }
        }
    }

    fun setUniFormMat4(location: Int, matrix: FloatArray) {
        runCommands.add(Runnable {
            GLES20.glUniformMatrix4fv(location, 1, false, matrix, 0)
        })

    }

    fun setUniForm1f(location: Int, value: Float) {
        runCommands.add(Runnable {
            GLES20.glUniform1f(location, value)
        })
    }

    fun setUnifrom4f(location: Int, x: Float, y: Float, z: Float, w: Float) {
        runCommands.add(Runnable {
            GLES20.glUniform4f(location, x, y, z, w)
        })
    }

    fun release() {
        if (mProgram != 0) {
            GLES20.glDeleteProgram(mProgram)
        }
        isInit = false
    }

}