package catnemo.top.opengleslearn

import androidx.appcompat.app.AppCompatActivity

import java.util.HashMap

class t {

    private val ha = HashMap<String, Class<out AppCompatActivity>>()

    init {
        ha["ko"] = MainActivity::class.java

    }
}
