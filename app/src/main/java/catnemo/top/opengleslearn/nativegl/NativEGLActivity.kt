package catnemo.top.opengleslearn.nativegl

import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Surface
import android.view.SurfaceHolder
import catnemo.top.glcore.GLCore
import catnemo.top.opengleslearn.R
import kotlinx.android.synthetic.main.activity_nativ_egl.*

class NativEGLActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var surfaceTexture: SurfaceTexture? = null

    private val type = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nativ_egl)
        title = "原生实现"
        gl_surface.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                GLCore.resize(width, height)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                release()
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                GLCore.setType(type)
                holder?.let {
                    GLCore.init(null, 0, holder.surface)
                }
                if (type == 1) {
                    GLCore.performDraw()
                    return
                }
                surfaceTexture = GLCore.createSurfaceTexture()
                val surface = Surface(surfaceTexture)
                mediaPlayer = MediaPlayer()
                mediaPlayer?.let {
                    it.setDataSource("/sdcard/DCIM/Camera/1566530575149.mp4")
                    it.setSurface(surface);
                    it.prepareAsync()
                    it.setOnPreparedListener { mp -> mp?.start() }
                }
                surfaceTexture?.setOnFrameAvailableListener {
                    GLCore.performDraw()
                }
                surface.release()
            }
        })

    }

    override fun onResume() {
        super.onResume()

    }


    override fun onPause() {
        super.onPause()
        mediaPlayer?.let {
            it.pause()
        }
    }

    private fun release() {
        GLCore.release()
        mediaPlayer?.let {
            it.stop()
            it.release()
        }
        surfaceTexture?.let {
            it.release()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
