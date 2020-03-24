package catnemo.top.opengleslearn.javagl.graphics

import android.opengl.GLES20
import android.opengl.Matrix
import catnemo.top.opengleslearn.javagl.graphics.builder.ObjectBuilder
import catnemo.top.opengleslearn.javagl.gles.program.BaseShaderProgram
import catnemo.top.opengleslearn.javagl.graphics.builder.Geometry

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
class Circle(private var radius: Float, private var numPoints: Int) : Graphics<BaseShaderProgram>() {

    private var generatedData: ObjectBuilder.GeneratedData? = null
    override val program: BaseShaderProgram = BaseShaderProgram()

    override fun onInited() {
        COMPONENT_COUNT = 2
        generatedData = ObjectBuilder.createCircle(Geometry.Circle(Geometry.Point(0f, 0f, 0f), radius), numPoints, "z")
        generatedData?.apply {
            putVertexArray(vertexData)
        }
    }

    override fun onPreDraw(mtx: FloatArray) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
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
        program.setUnifrom4f(program.uColorLocation, 0.9f, 0.9f, 0.9f, 0.9f)
    }

    override fun drawCommand() {
        generatedData?.apply {
            for (draw in drawList) {
                draw.draw()
            }
        }
        GLES20.glDisable(GLES20.GL_DEPTH_TEST)
    }

    override fun onReleased() {
        program.release()
    }
}