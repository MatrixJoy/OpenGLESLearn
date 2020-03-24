package catnemo.top.opengleslearn.nativegl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import catnemo.top.glcore.GLCore
import catnemo.top.opengleslearn.R
import kotlinx.android.synthetic.main.activity_nativ_egl.*

class NativEGLActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nativ_egl)
        title = "原生实现"
        gl_surface.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                GLCore.release()
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                Thread(Runnable {
                    holder?.let {
                        GLCore.init(null, 0, holder.surface)
                    }
                }).start()
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
