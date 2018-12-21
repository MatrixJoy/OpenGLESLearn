package catnemo.top.opengleslearn

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import catnemo.top.opengleslearn.entity.Item

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private val SCHEME = "catnemo.top.opengleslearn"

    private val list = listOf(Item("三角形", "$SCHEME://triangle"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.list_view)
        val adapter = Adapter()
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val uri = Uri.parse(list[position].scheme.trim())
            val i = Intent(Intent.ACTION_VIEW, uri)
            startActivity(i)
        }
    }

    inner class Adapter : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            var holde: ViewHolder = ViewHolder()
            if (view == null) {
                view = LayoutInflater.from(this@MainActivity).inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
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
