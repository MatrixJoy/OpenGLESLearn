package catnemo.top.opengleslearn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleAdapter
import catnemo.top.opengleslearn.javagl.JavaGLImplementationActivity
import catnemo.top.opengleslearn.nativegl.NativEGLActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val funMap = mapOf<String, Class<out AppCompatActivity>>(
            "JavaGL" to JavaGLImplementationActivity::class.java,
            "nativeGL" to NativEGLActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.home)
        function_list.divider = resources.getDrawable(R.drawable.divider)
        function_list.adapter = SimpleAdapter(this, crateFuncationList(), android.R.layout.two_line_list_item,
                arrayOf("title", "pageclass"), intArrayOf(android.R.id.text2))
        function_list.setOnItemClickListener { parent, view, position, id ->
            val clazz = (parent.getItemAtPosition(position) as Map<String, Class<out AppCompatActivity>>)["pageclass"]
            val intent = Intent(this, clazz)
            startActivity(intent)

        }
    }

    private fun crateFuncationList(): List<Map<String, Class<out AppCompatActivity>>> {
        val list = ArrayList<Map<String, Class<out AppCompatActivity>>>()
        for (m in funMap) {
            list.add(mapOf("title" to m.key, "pageclass" to m.value) as Map<String, Class<out AppCompatActivity>>)
        }
        return list
    }
}
