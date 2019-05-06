package catnemo.top.opengleslearn.graphics

import android.opengl.GLES20
import android.opengl.Matrix
import catnemo.top.opengleslearn.graphics.builder.ObjectBuilder
import catnemo.top.opengleslearn.program.UniformColorShaderProgram
import catnemo.top.opengleslearn.util.Geometry

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
class Circle : Graphics {
    var program: UniformColorShaderProgram

    init {
        COMPONENT_COUNT = 2
        program = UniformColorShaderProgram()
    }

    var generatedData: ObjectBuilder.GeneratedData? = null

    constructor(radius: Float, numPoints: Int) {
        generatedData = ObjectBuilder.createCircle(Geometry.Circle(Geometry.Point(0f, 0f, 0f), radius), numPoints, "z")
        generatedData?.apply {
            putVertexArray(vertexData)
        }
    }

    override fun draw(mtx: FloatArray) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        program.useProgram()
        val projection = FloatArray(16)

        val ratio = if (mSurfaceWidth > mSurfaceHeight) {
            mSurfaceWidth.toFloat() / mSurfaceHeight
        } else {
            mSurfaceHeight.toFloat() / mSurfaceWidth;
        }

        if (mSurfaceWidth > mSurfaceHeight) {
            Matrix.orthoM(projection, 0, -ratio, ratio, -1f, 1f, -1f, 1f)
        } else {
            Matrix.orthoM(projection, 0, -1f, 1f, -ratio, ratio, -1f, 1f)
        }
        Matrix.multiplyMM(mtx, 0, projection, 0, mtx, 0)
        program.setUniFormMat4(program.uMatrixLocation, mtx)
        vertex?.setVertexAttribPointer(0, program.aPositionLocation, 3, 3 * 4)
        GLES20.glUniform4f(program.uColorLocation, 0.9f, 0.9f, 0.9f, 0.9f)
        generatedData?.apply {
            for (draw in drawList) {
                draw.draw()
            }
        }
    }

    override fun release() {
        program.release()
    }
}