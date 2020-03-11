package jp.making.felix.readrecorder

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.squareup.picasso.Picasso
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import kotlinx.android.synthetic.main.book_item.view.*

class ListAdapter(context: Context, val users: List<Book>): BaseAdapter() {

    val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return users.count()
    }

    override fun getItem(position: Int): Book {
        return users[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = layoutInflater.inflate(R.layout.book_item, parent, false)
        if(users[position].imageUrl.isNotEmpty()) {
            Picasso.get().load(users[position].imageUrl).into(view.thumbnail)
        }
        val updatePosition = users[position].updateDate.size - 1
        view.name.text = users[position].name
        view.comment.text = users[position].updateDate[updatePosition]?.pageData
        return view
    }
}