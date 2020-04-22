package jp.making.felix.readrecordermvparch.data

import android.graphics.Color
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
        binding.comment.text = book.updateDate.last()?.pageData
        val nowpage = book.pages.last()?.pageData?.toFloat()
        val maxpage = book.maxPage.toFloat()
        // 現在の読破率 else 0%(登録したままの状態)
        val persentage =
            if (nowpage != 0f && nowpage != null) (nowpage / maxpage * 100).toInt() else 0
        binding.movement.text = makeMovementString(persentage)
        if (persentage == 100) binding.movement.setTextColor(Color.RED)
        binding.pagechart.setup(binding, book)
        binding.name.setOnClickListener(clickListener)
        viewHolder.itemView.setOnClickListener {
            binding.name.callOnClick()
        }
    }

    private fun LineChart.setup(binding: BookItemBinding, book: Book) {
        val adapter = ChartItemAdapter(binding.pagechart, book)
        adapter.setup()
    }

    private fun makeMovementString(now: Int): String {
        return "読破率$now％"
    }
}