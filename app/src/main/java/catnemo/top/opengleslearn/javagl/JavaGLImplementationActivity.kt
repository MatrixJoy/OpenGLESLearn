package catnemo.top.opengleslearn.javagl

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import catnemo.top.opengleslearn.javagl.gles.BaseGLRender
import catnemo.top.opengleslearn.R
import catnemo.top.opengleslearn.entity.Item
import catnemo.top.opengleslearn.javagl.graphics.*
import kotlinx.android.synthetic.main.activity_java_impl.*

class JavaGLImplementationActivity : AppCompatActivity() {

    private val list = listOf(
            Item("三角形", Triangle()),
            Item("颜色混合三角形", ColorfulTriangle()),
            Item("矩形", Rectangle()),
            Item("圆形", Circle(0.5f, 32)),
            Item("纹理", Texture(R.drawable.test)),
            Item("立方体", Cube()),
            Item("锥体", Pyramid()),
            Item("圆锥", Cone(0.5f, -0.8f, 32)),
            Item("圆柱", Cylinder(0.5f, -0.8f, 32))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_java_impl)
        title = getString(R.string.java_implemention)
        val render = BaseGLRender()
        gl_surface_view.setZOrderOnTop(false)
        gl_surface_view.setZOrderMediaOverlay(true)

        gl_surface_view.setEGLContextClientVersion(2)
        gl_surface_view.setRenderer(render)
        gl_surface_view.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        gl_surface_view.queueEvent { render.mGraphics = Triangle() }
        grid_view.adapter = Adapter()
        grid_view.setOnItemClickListener { _, _, position, id ->
            gl_surface_view.queueEvent {
                render.mGraphics = list[position].shape
                gl_surface_view.requestRender()
                hideGridView()
            }
        }
        change_shape_tv.setOnClickListener {
            if (grid_view.visibility == View.INVISIBLE) {
                showGridView()
            } else {
                hideGridView()
            }
        }

        change_angle.setOnClickListener {
            gl_surface_view.queueEvent {
                render.setAngle(SystemClock.uptimeMillis() * 50F)
                gl_surface_view.requestRender()
            }
        }
        gl_surface_view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                }
                MotionEvent.ACTION_MOVE -> {
                    gl_surface_view.queueEvent {
                        val x = ((event.x / v.width) * 2 - 1) * 5
                        val y = -((event.y / v.height) * 2 - 1) * 5
                        val z = 3f
                        render.setEyeXYZ(x, y, z)
                        gl_surface_view.requestRender()
                    }
                }
            }
            return@setOnTouchListener true
        }

    }

    private fun showGridView() {
        grid_view.post {
            grid_view.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(grid_view, "translationY",
                    grid_view.height * 1.0f, 0f)
                    .setDuration(300)
                    .start()
        }
    }

    private fun hideGridView() {
        grid_view.post {
            val hide = ObjectAnimator.ofFloat(grid_view, "translationY",
                    0f, grid_view.height * 1.0f)
                    .setDuration(300)
            hide.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    grid_view.visibility = View.INVISIBLE
                }
            })
            hide.start()
        }
    }

    inner class Adapter : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            var holde: ViewHolder = ViewHolder()
            if (view == null) {
                view = LayoutInflater.from(this@JavaGLImplementationActivity).inflate(android.R.layout.simple_list_item_1, parent, false)
                holde.tv = view.findViewById(android.R.id.text1)
                view.tag = holde
            } else {
                holde = (view.tag as ViewHolder)
            }
            holde.tv?.text = list[position].name

            return view!!
        }

        override fun getItem(position: Int): Any {
            return list[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return list.size
        }

        inner class ViewHolder {
            var tv: TextView? = null
        }

    }
}
