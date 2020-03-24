package catnemo.top.opengleslearn.javagl.graphics

import android.opengl.GLES20
import android.opengl.Matrix
import catnemo.top.opengleslearn.javagl.graphics.builder.ObjectBuilder
import catnemo.top.opengleslearn.javagl.gles.program.AttributeColorShaderProgram
import catnemo.top.opengleslearn.javagl.graphics.builder.Geometry
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
class Cone(private val radius: Float, private val height: Float, private val numPoints: Int) : Graphics<AttributeColorShaderProgram>() {
    override val program: AttributeColorShaderProgram = AttributeColorShaderProgram()

    private var colorVb: FloatArray? = null

    private var generatedData: ObjectBuilder.GeneratedData? = null
    private var colorFb: FloatBuffer? = null

    override fun onInited() {
        val point = Geometry.Point(0f, 0f, 0f)
        generatedData = ObjectBuilder.createCone(Geometry.Conde(point, radius, height), numPoints)
        generatedData?.apply {
            putVertexArray(vertexData)
            colorVb = FloatArray(vertexData.size)
            colorVb?.apply {
                for (i in 0 until size) {
                    val color = if (i * 1.0f / size < 0.6) {
                        1.0f
                    } else {
                        (i * 1.0f / 255)
                    }
                    set(i, color)

                }
                colorFb = ByteBuffer.allocateDirect(size * PER_FLOAT_BYTE)
                        .order(ByteOrder.nativeOrder())
                        .asFloatBuffer()
                        .put(this)
            }
        }
    }

    override fun onPreDraw(mtx: FloatArray) {

        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        vertex?.setVertexAttribPointer(0, program.aPositionLocation, 3, 3 * 4)
        colorFb?.position(0)
        GLES20.glVertexAttribPointer(program.aColorLocation, 3, GLES20.GL_FLOAT, false, 3 * 4, colorFb)
        GLES20.glEnableVertexAttribArray(program.aColorLocation)
        val projection = FloatArray(16)
        Matrix.perspectiveM(projection, 0, 45f, mSurfaceWidth / mSurfaceHeight.toFloat(), 0.1f, 100f)
        val viewMtx = FloatArray(16)
        Matrix.setLookAtM(viewMtx, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f)
        Matrix.scaleM(viewMtx, 0, 0.5f, 0.5f, 0.5f)
        Matrix.multiplyMM(mtx, 0, projection, 0, viewMtx, 0)
        program.setUniFormMat4(program.uMatrixLocation, mtx)

    }

    override fun drawCommand() {
        generatedData?.apply {
            for (draw in drawList) {
                draw.draw()
            }
        }
        GLES20.glDisableVertexAttribArray(program.aColorLocation)
        GLES20.glDisableVertexAttribArray(program.aPositionLocation)
        GLES20.glDisable(GLES20.GL_DEPTH_TEST)

    }


    override fun onReleased() {
        program.release()
    }
}