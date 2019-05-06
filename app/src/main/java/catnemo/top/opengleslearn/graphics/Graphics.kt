package catnemo.top.opengleslearn.graphics

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
abstract class Graphics {
    companion object {
        const val PER_FLOAT_BYTE = 4
    }

    open var COMPONENT_COUNT = 0
    var vertex: VertexArray? = null

    var mSurfaceWidth: Int = 0
    var mSurfaceHeight: Int = 0
    var height = 0f

    var mAngle: Float = 0f

    var mEyeX = 0f
    var mEyeY = 0f
    var mEyeZ = 0f


    fun putVertexArray(vertexArray: FloatArray) {
        vertex = VertexArray(vertexArray)
    }

    abstract fun draw(mtx: FloatArray)
    abstract fun release()
}