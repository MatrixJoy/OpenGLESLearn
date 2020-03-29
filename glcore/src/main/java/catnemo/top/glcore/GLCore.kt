package catnemo.top.glcore

import android.graphics.SurfaceTexture
import android.view.Surface
import javax.microedition.khronos.egl.EGLContext

object GLCore {
    init {
        System.loadLibrary("glcore")
    }

    external fun init(shareContext: EGLContext? = null, flag: Int = 0, surface: Surface): Boolean
    external fun resize(width: Int, height: Int)
    external fun performDraw();
    external fun release()
    external fun createSurfaceTexture(): SurfaceTexture
}
