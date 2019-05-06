package catnemo.top.opengleslearn.graphics

import android.opengl.GLES20
import android.opengl.Matrix
import android.os.SystemClock
import catnemo.top.opengleslearn.graphics.builder.ObjectBuilder
import catnemo.top.opengleslearn.program.AttributeColorShaderProgram
import catnemo.top.opengleslearn.program.UniformColorShaderProgram
import catnemo.top.opengleslearn.util.Geometry
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/26
 *
 */
class Cylinder : Graphics {

    private var colorVb: FloatArray? = null
    var mProgram = AttributeColorShaderProgram()

    private var generatedData: ObjectBuilder.GeneratedData? = null
    private var colorFb: FloatBuffer? = null

    constructor(radius: Float, height: Float, numPoints: Int) {
        var point = Geometry.Point(0f, 0f, 0f)
        generatedData = ObjectBuilder.createCylinder(Geometry.Cylinder(point, radius, height), numPoints)
        generatedData?.apply {
            putVertexArray(vertexData)
            colorVb = FloatArray(vertexData.size)
            colorVb?.apply {
                for (i in 0 until size) {
                    set(i, i * 1.0f / 255)
                }
                colorFb = ByteBuffer.allocateDirect(size * PER_FLOAT_BYTE)
                        .order(ByteOrder.nativeOrder())
                        .asFloatBuffer()
                        .put(this)
            }
        }
    }

    override fun draw(mtx: FloatArray) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)

        mProgram.useProgram()

        vertex?.setVertexAttribPointer(0, mProgram.aPositionLocation, 3, 3 * 4)
        colorFb?.position(0)
        GLES20.glVertexAttribPointer(mProgram.aColorLocation, 3, GLES20.GL_FLOAT, false, 3 * 4, colorFb)
        GLES20.glEnableVertexAttribArray(mProgram.aColorLocation)
        val projection = FloatArray(16)
        Matrix.perspectiveM(projection, 0, 45f, mSurfaceWidth / mSurfaceHeight.toFloat(), 0.1f, 100f)
        val viewMtx = FloatArray(16)
        Matrix.setLookAtM(viewMtx, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f)
        Matrix.scaleM(viewMtx, 0, 0.5f, 0.5f, 0.5f)
        Matrix.multiplyMM(mtx, 0, projection, 0, viewMtx, 0)
        mProgram.setUniFormMat4(mProgram.uMatrixLocation, mtx)
//        GLES20.glUniform4f(mProgram.uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        generatedData?.apply {
            for (draw in drawList) {
                draw.draw()
            }
        }
        GLES20.glDisableVertexAttribArray(mProgram.aColorLocation)
        GLES20.glDisableVertexAttribArray(mProgram.aPositionLocation)
    }

    override fun release() {
        mProgram.release()
    }
}