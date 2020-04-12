package catnemo.top.opengleslearn.javagl.particle

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import catnemo.top.opengleslearn.R
import kotlinx.android.synthetic.main.activity_particle.*

class ParticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_particle)
        gl_surface.setEGLContextClientVersion(2)
        gl_surface.setRenderer(ParticleRender())
    }

    override fun onResume() {
        super.onResume()
        gl_surface.onResume()
    }

    override fun onPause() {
        super.onPause()
        gl_surface.onPause()
    }
}
