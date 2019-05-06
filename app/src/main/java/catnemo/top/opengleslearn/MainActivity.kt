package catnemo.top.opengleslearn

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import catnemo.top.opengleslearn.entity.Item
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val list = listOf(
            Item("三角形", 0),
            Item("颜色混合三角形", 1),
            Item("纹理", 2),
            Item("纹理+三角形", 3),
            Item("五角星", 4),
            Item("圆形", 5),
            Item("矩形", 6),
            Item("立方体", 7),
            Item("锥体", 8),
            Item("圆锥", 9),
            Item("圆柱", 10))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val render = BaseGLRender()
        gl_surface_view.setEGLContextClientVersion(2)
        gl_surface_view.setRenderer(render)
        gl_surface_view.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        gl_surface_view.queueEvent { render.mShape = 0 }
        grid_view.adapter = Adapter()
        grid_view.setOnItemClickListener { parent, view, position, id ->
            gl_surface_view.queueEvent {
                render.mShape = list[position].shape
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
                        val x = ((event.x / v.width) * 2 - 1)*5
                        val y = -((event.y / v.height) * 2 - 1)*5
                        val z = 3f
                        render.setEyeXYZ(x,y, z)
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
                view = LayoutInflater.from(this@MainActivity).inflate(android.R.layout.simple_list_item_1, parent, false)
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
