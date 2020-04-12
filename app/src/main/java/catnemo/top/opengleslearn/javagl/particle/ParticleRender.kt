package catnemo.top.opengleslearn.javagl.particle

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class ParticleRender : GLSurfaceView.Renderer {

    private val particleSystem = ParticleSystem(10000)

    private var width = 0
    private var height = 0
    private val mtx = FloatArray(16)

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        if (particleSystem.isInit) {
            particleSystem.onSizeChanged(width, height)
        }
        this.width = width
        this.height = height

        Matrix.setIdentityM(mtx, 0)
    }

    override fun onDrawFrame(gl: GL10?) {
        if (!particleSystem.isInit) {
            particleSystem.init(width, height)
        }
        particleSystem.draw(mtx)
    }
}