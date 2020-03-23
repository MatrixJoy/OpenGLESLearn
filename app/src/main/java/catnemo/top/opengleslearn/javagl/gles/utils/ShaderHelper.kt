package catnemo.top.opengleslearn.javagl.gles.utils

import android.opengl.GLES20
import android.opengl.GLES20.glGetProgramInfoLog
import android.util.Log


/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2018/12/04
 *
 */
private val TAG = "ShaderHelper"

/**
 * 编译顶点着色器，并返回编译好的id
 */
fun compileVertexShader(shaderCode: String): Int {
    return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode)
}

/**
 * 编译片源着色器，并返回编译好的id
 */
fun compileFragmentShader(shaderCode: String): Int {
    return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode)
}

/**
 * 编译shader，跟进类型不同编译不同的shader
 */
private fun compileShader(type: Int, shaderCode: String): Int {
    val shaderObjectId = GLES20.glCreateShader(type) // 根据shader类型 创建shaderid 也就是当前shader的引用
    /**
     * shaderObjectId == 0 表示编译失败，类似于java 的 null
     * shaderObjectId 表示 OpenGL 对象的一个引用，无论后面如何引用这个对象，传入这个id即可
     */
    if (shaderObjectId == 0) {
        Log.e(TAG, " crate shader fail")
        return 0
    }
    /**
     * 构建完 shaderObjectId ，然后上传写好的 shaderCode 到 OpenGL
     * 建立起 shaderObjectId 和 shaderCode 之间的关联
     */
    GLES20.glShaderSource(shaderObjectId, shaderCode) // 关联 shaderObjectId 和 shaderCode
    /**
     * 因为 shaderCode 已经和 shaderObjectId 建立了关联 所以这里传入 shaderObjectId 就可以完成编译
     */
    GLES20.glCompileShader(shaderObjectId) // 编译shader
    val compileStatus = IntArray(1) // 接收编译状态的数组
    /**
     * 接收编译状态 并将编译状态存入 compileStatus[0] 返回
     */
    GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0) // 获取编译状态

    /**
     * GLES20.glGetShaderInfoLog(shaderObjectId) 可以获取到当前shader的详细信息
     * 这里打印出编译时的信息，已告知我们可能出错的原因
     */
    Log.d(TAG, "Results of compiling source: \n $shaderCode \n ${GLES20.glGetShaderInfoLog(shaderObjectId)}")
    /**
     * 同理 如果 状态是0 则编译失败 这里就删除申请的 shader 对象
     */
    if (compileStatus[0] == 0) {
        GLES20.glDeleteShader(shaderObjectId) // 删除shader
        Log.w(TAG, "Compilation of shader failed.")
        return 0
    }
    return shaderObjectId
}

/**
 * program 绑定 顶点着色器 和 片源着色器 因为二者总是紧密结合的
 */
fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
    /**
     * 创建 program 对象并将其引用返回给 programObjectId
     */
    val programObjectId = GLES20.glCreateProgram()  // 创建 program 对象
    if (programObjectId == 0) {
        Log.w(TAG, "could not crate new program.")
        return 0
    }
    /**
     * glAttachShader 给 program 对象 吸附上 着色器
     */
    GLES20.glAttachShader(programObjectId, vertexShaderId) // 绑定 program 和 shader
    GLES20.glAttachShader(programObjectId, fragmentShaderId)
    GLES20.glLinkProgram(programObjectId) // 链接 两着色器到一块儿

    val linStatus = IntArray(1)
    GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linStatus, 0)
    Log.v(TAG, "Results of linking program:\n ${GLES20.glGetProgramInfoLog(programObjectId)}")

    if (linStatus[0] == 0) {
        GLES20.glDeleteProgram(programObjectId)
        Log.w(TAG, "Linking of program failed.");
        return 0
    }
    return programObjectId
}

fun validateProgram(programObjectId: Int): Boolean {
    GLES20.glValidateProgram(programObjectId)
    val validateStatus = IntArray(1)
    GLES20.glGetProgramiv(programObjectId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0)
    Log.v(TAG, "Results of validating program: ${validateStatus[0]} " +
            "\n Log:  ${glGetProgramInfoLog(programObjectId)}")
    return validateStatus[0] != 0
}

fun buildProgram(vertexShaderSource: String, fragmentShaderSource: String): Int {
    var program = 0
    val vertexShader = compileVertexShader(vertexShaderSource)
    val fragmentShader = compileFragmentShader(fragmentShaderSource)

    program = linkProgram(vertexShader, fragmentShader)
    validateProgram(program)
    return program
}