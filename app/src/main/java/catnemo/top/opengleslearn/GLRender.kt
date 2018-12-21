package catnemo.top.opengleslearn

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import catnemo.top.airhockey.util.ShaderHelper
import catnemo.top.airhockey.util.TextResourceReader
import catnemo.top.opengleslearn.util.LogUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2018/12/19
 *
 */
class GLRender(val context: Context) : GLSurfaceView.Renderer {

    private val POSITION_COMMENT_COUNT = 2

    private val BYTE_PER_FLOAT = 4 // 每一个float的子节大小
    // 二维数组 用来存储坐标信息
    private val vertex = floatArrayOf(
            // x,y
            0.0f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f
    )

    private var program = 0

    private val floatBuffer = ByteBuffer.allocateDirect(vertex.size * BYTE_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()

    private val U_COLOR = "u_Color" // shader 中的 uniform 变量名

    private var uColorLocation = 0 // 持有  shader 中的 uniform u_Color 的 location,用于传递数据给shader

    private val A_POSITION = "a_Position"
    private var aPositionLocation = 0

    init {
        floatBuffer.put(vertex) // 从java虚拟机 传递到 native memory
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 0f)

        /**
         * 读取 shader 原代码
         */
        val vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader)
        val fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader)

        /**
         * 编译shader 并获取已经编译好的shader对象
         */
        val vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource)
        val fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource)

        /**
         * 绑定片源和顶点着色器
         */
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader)

        if (LogUtil.ON) {
            ShaderHelper.validateProgram(program)
        }
        /**
         * 使得opengl 能使用这个program来进行绘制
         */
        GLES20.glUseProgram(program)

        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR) // 获取到 U_COLOR 的 program 的location对象
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION)

        floatBuffer.position(0) // 移动指针到起始位置
        /**
         * 从 floatBuffer 中找到数据 赋值给 aPositionLocation
         * @param index attribute location
         * @param size 顶点包含的做标数，例如 [x,y ] 那么就是 2 [x, y, z] 就是3 以此类推
         * @param type 数据的类型 这里 定义的是 floatBuffer 所以传递 GL_FLOAT
         * @param normalized 只有在整形数据适用
         * @param sride 步长 当在一个数组里存放了多种 attribute location 例如 x,y,z ,r,g,b stride is 3
         * @param buuferptr  前面定义的 floatbuffer
         */
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMMENT_COUNT, GLES20.GL_FLOAT, false
                , 0, floatBuffer)

        GLES20.glEnableVertexAttribArray(aPositionLocation)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT) // 清屏 并赋值上 glClearColor的颜色
        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3)
    }
}