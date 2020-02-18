package jp.making.felix.readrecordermvparch.BookDataView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.Logs
import jp.making.felix.readrecordermvparch.data.Page

class thoughtListAdapter(context: Context, val logs: List<Logs>, val page: List<Page>): BaseAdapter() {
    private val inflater = LayoutInflater.from(context)


    override fun getItem(position: Int) = logs[position].logData

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = logs.size


    private fun createView(parent: ViewGroup?) : View {
        val view = inflater.inflate(R.layout.thought_item, parent, false)
        view.tag = ViewHolder(view)
        return view
    }

    private class ViewHolder(view: View) {
        val thoughtPage = view.findViewById<TextView>(R.id.thoughtPage)
        val thoughtText = view.findViewById<TextView>(R.id.thoughtText)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: createView(parent)

        val viewHolder = view.tag as ViewHolder

        viewHolder.thoughtPage.text = page[position].pageData.toString()
        viewHolder.thoughtText.text = getItem(position)
        return view
    }
}