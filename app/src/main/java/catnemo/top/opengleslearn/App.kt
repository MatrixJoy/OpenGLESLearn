package catnemo.top.opengleslearn

import android.app.Application
import android.content.Context

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/23
 *
 */
class App : Application() {

    companion object {
        lateinit var mContext: Context
        lateinit var mInst: App
    }

    override fun onCreate() {
        super.onCreate()
        mInst = this
        mContext = applicationContext
    }
}