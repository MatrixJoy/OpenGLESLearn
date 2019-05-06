package catnemo.top.opengleslearn.graphics

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/24
 *
 */
class VertexArray(vertexArray: FloatArray) {
    var vertexBuffer: FloatBuffer = ByteBuffer.allocateDirect(vertexArray.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(vertexArray)




    fun setVertexAttribPointer(dataOffset: Int, localPosition: Int, componentCount: Int, stride: Int = 0) {

        vertexBuffer.position(dataOffset)
        GLES20.glVertexAttribPointer(localPosition, componentCount, GLES20.GL_FLOAT, false, stride,
                vertexBuffer)
        GLES20.glEnableVertexAttribArray(localPosition)
        vertexBuffer.position(0)
    }
}