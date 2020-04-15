package jp.making.felix.readrecordermvparch.data

import android.view.View
import coil.api.load
import com.github.mikephil.charting.charts.LineChart
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.databinding.BookItemBinding
import jp.making.felix.readrecordermvparch.util.ChartItemAdapter

data class BookItem(
    val book: Book,
    val clickListener: (View) -> Unit
) : Item<GroupieViewHolder>() {

    override fun getLayout(): Int = R.layout.book_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val binding = BookItemBinding.bind(viewHolder.root)
        binding.thumbnail.load(book.imageUrl)
        binding.name.text = book.name
        binding.comment.text = book.updateDate[book.updateDate.size - 1]?.pageData
        binding.pagechart.setup(binding, book)
        viewHolder.itemView.setOnClickListener(clickListener)
    }

    private fun LineChart.setup(binding: BookItemBinding, book: Book) {
        val adapter = ChartItemAdapter(binding.pagechart, book)
        adapter.setup()
    }
}