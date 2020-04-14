package jp.making.felix.readrecordermvparch.data

import android.view.View
import coil.api.load
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.OnItemLongClickListener
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.databinding.BookItemBinding

data class BookItem(
    val book: Book
) : Item<GroupieViewHolder>() {

    override fun getLayout(): Int = R.layout.book_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val binding = BookItemBinding.bind(viewHolder.root)
        binding.thumbnail.load(book.imageUrl)
        binding.name.text = book.name
        binding.comment.text = book.updateDate[book.updateDate.size - 1]?.pageData
    }
}